/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.ao4fma.core.ProductsReader;
import at.tugraz.ist.ase.ao4fma.core.AllRecommendationLists;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class Accessibility extends AnalysisOperation {

    File fmFile;
    File filterFile;
    File productsFile;

    AllRecommendationLists all = null;

    public Accessibility(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
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

        // calculate accessibility for each product
        HashMap<Product, Double> accessibility = new HashMap<>();
        for (Product product : products) {
            val accessibilityValue = calculate(product);
            accessibility.put(product, accessibilityValue);
        }

        return accessibility;
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

        // calculate accessibility
        int occurrences = all.countOccurrence(product);
        double accessibility = (double) occurrences / all.size();

        // print results
        if (printResults) {
            LoggerUtils.indent();
            String message = String.format("%sOccurrences: %s", LoggerUtils.tab(), occurrences);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sTotal recommendations: %s", LoggerUtils.tab(), all.size());
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sAccessibility: %s", LoggerUtils.tab(), accessibility);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            LoggerUtils.outdent();
        }

        return accessibility;
    }
}
