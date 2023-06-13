/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.ao4fma.product.ProductsReader;
import at.tugraz.ist.ase.ao4fma.product.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.ao4fma.recommendation.AllRecommendationLists;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class Accessibility {

    File fmFile;
    File filterFile;
    File productsFile;

    @Setter
    BufferedWriter writer = null;

    AllRecommendationLists all = null;

    public Accessibility(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public HashMap<Product, Double> calculate() throws IOException, FeatureModelParserException {
        calculateAllRecommendations();

        val products = ProductsReader.read(productsFile);

        // calculate accessibility for each product
        HashMap<Product, Double> accessibility = new HashMap<>();
        for (Product product : products) {
            val accessibilityValue = calculate(product);
            accessibility.put(product, accessibilityValue);
        }

        return accessibility;
    }

    public Double calculate(Product product) throws FeatureModelParserException, IOException {
        String message = String.format("%sProduct: %s", LoggerUtils.tab(), product.id());
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }

        calculateAllRecommendations();

        // calculate accessibility
        int occurrences = all.countOccurrence(product);
        double accessibility = (double) occurrences / all.size();

        // print results
        LoggerUtils.indent();
        message = String.format("%sOccurrences: %s", LoggerUtils.tab(), occurrences);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        message = String.format("%sTotal recommendations: %s", LoggerUtils.tab(), all.size());
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        message = String.format("%sAccessibility: %s", LoggerUtils.tab(), accessibility);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        LoggerUtils.outdent();

        return accessibility;
    }

    private void calculateAllRecommendations() throws FeatureModelParserException, IOException {
        if (all != null) return;

        all = new AllRecommendationLists();
        UserRequirement urOperation = new UserRequirement();

        // calculate all list of user requirements
        List<Requirement> userRequirements = urOperation.getConsistentUserRequirements(fmFile, filterFile, productsFile);

        Recommendation recommendation = Recommendation.builder()
                                                    .fmFile(fmFile)
                                                    .filterFile(filterFile)
                                                    .productsFile(productsFile)
                                                    .build();
        recommendation.setWriter(writer);
        recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy

        // calculate all list of recommendations
        for (Requirement userRequirement : userRequirements) {
//            System.out.println("User requirement: " + userRequirement);
            val recommendationList = recommendation.recommend(userRequirement);
//            System.out.println();

            if (recommendationList.empty()) {
//                System.out.println("No recommendation found!");
                continue;
            }
            all.add(recommendationList);

//            System.out.println();
        }

//        System.out.println("User requirements: " + userRequirements.size());
//        System.out.println("All recommendations: " + all.size());
    }
}
