/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.core.ProductsReader;
import at.tugraz.ist.ase.ao4fma.core.RecommendationList;
import at.tugraz.ist.ase.ao4fma.core.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implementation of Restrictiveness of Features
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class Restrictiveness extends AnalysisOperation {

    public Restrictiveness(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        super(fmFile, filterFile, productsFile);
    }

    public double calculate(Requirement req) throws IOException, FeatureModelParserException {
        Utilities.printInfo(printResults, writer, "Requirement", req.toString());

        // read products
        val productAssortment = ProductsReader.read(productsFile);
        // DENOMINATOR - the total number of products
        int totalProducts = productAssortment.size();

        // NUMERATOR - supports
        LoggerUtils.indent();
        Recommendation recommendation = Recommendation.builder()
                                                .fmFile(fmFile)
                                                .filterFile(filterFile)
                                                .productsFile(productsFile)
                                                .build();
        recommendation.setWriter(writer);
        recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy
        RecommendationList recommendationList = recommendation.recommend(req);
        int support = recommendationList.size();

        // restrictiveness
        double restrictiveness = 1 - (double) support / totalProducts;

        // print results
        if (printResults) {
            String message = String.format("%sSupport: %s", LoggerUtils.tab(), support);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sTotal products: %s", LoggerUtils.tab(), totalProducts);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sRestrictiveness: %s", LoggerUtils.tab(), restrictiveness);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return restrictiveness;
    }

    // calculate restrictiveness for all leaf features
    public LinkedHashMap<String, Double> calculate() throws IOException, FeatureModelParserException {
        LinkedHashMap<String, Double> results = new LinkedHashMap<>();

        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        for (String feature : Utilities.getLeafFeatures(fm)) {
            val req = Requirement.requirementBuilder()
                    .assignments(List.of(Assignment.builder()
                            .variable(feature)
                            .value("true")
                            .build()))
                    .build();

            val restrict_value = calculate(req);

            results.put(feature, restrict_value);
        }
        return results;
    }
}
