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
public class Visibility {

    File fmFile;
    File filterFile;
    File productsFile;

    @Setter
    BufferedWriter writer = null;
    @Setter
    boolean printResults = true;

    AllRecommendationLists all = null;

    public Visibility(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public HashMap<Product, Double> calculate() throws IOException, FeatureModelParserException {
        Recommendation recommendation = new Recommendation(fmFile, filterFile, productsFile);
        recommendation.setWriter(writer);
        recommendation.setPrintResults(false);
        all = recommendation.calculateAllRecommendations();

        val products = ProductsReader.read(productsFile);

        // calculate visibility for each product
        HashMap<Product, Double> visibilities = new HashMap<>();
        for (Product product : products) {
            val visibilityValue = calculate(product);
            visibilities.put(product, visibilityValue);
        }

        return visibilities;
    }

    public double calculate(Product product) throws FeatureModelParserException, IOException {
        if (printResults) {
            String message = String.format("%sProduct: %s", LoggerUtils.tab(), product.id());
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }

        if (all == null) {
            Recommendation recommendation = new Recommendation(fmFile, filterFile, productsFile);
            recommendation.setWriter(writer);
            recommendation.setPrintResults(false);
            all = recommendation.calculateAllRecommendations();
        }

        // calculate visibility
        LoggerUtils.indent();
        double visibility = all.visibility(product);

        // print results
        if (printResults) {
            String message = String.format("%sVisibility: %s", LoggerUtils.tab(), visibility);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return visibility;
    }
}
