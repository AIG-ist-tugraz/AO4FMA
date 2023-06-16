/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.core.AllRecommendationLists;
import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.ao4fma.core.RecommendationList;
import at.tugraz.ist.ase.ao4fma.core.rank.IProductRankingStrategy;
import at.tugraz.ist.ase.ao4fma.core.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapterFactory.createConfigurator;

/**
 * Implementation of a Recommendation task
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class Recommendation extends AnalysisOperation {

    @Setter
    IProductRankingStrategy rankingStrategy = null;
    ConfiguratorAdapter configurator = null;

    @Builder
    public Recommendation(File fmFile, File filterFile, File productsFile) {
        super(fmFile, filterFile, productsFile);
    }

    public RecommendationList recommend(Requirement req) throws FeatureModelParserException, IOException {
        // load the feature model
//        val fm = Utilities.loadFeatureModel(fmFile);

//        val configurator = createConfigurator(fmFile, filterFile, productsFile);
        if (configurator == null) {
            configurator = createConfigurator(fmFile, filterFile, productsFile, rankingStrategy.getCalculator(fmFile, filterFile, productsFile));
        }

        configurator.findAllSolutions(req); // identify all products that satisfy the Requirement

        // reorder the products according to the recommendation strategy
        List<Product> recommendedProducts;
        if (rankingStrategy != null) {
            recommendedProducts = rankingStrategy.rank(configurator.getProducts());
        } else {
            recommendedProducts = configurator.getProducts();
        }

        if (printResults) {
            Utilities.printList(recommendedProducts, writer);
        }

        return new RecommendationList(recommendedProducts);
    }

    public AllRecommendationLists calculateAllRecommendations() throws FeatureModelParserException, IOException {
        AllRecommendationLists all = new AllRecommendationLists();
        UserRequirement urOperation = new UserRequirement(fmFile, filterFile, productsFile);

        // calculate all list of user requirements
        List<Requirement> userRequirements = urOperation.getConsistentUserRequirements();

        Recommendation recommendation = Recommendation.builder()
                .fmFile(fmFile)
                .filterFile(filterFile)
                .productsFile(productsFile)
                .build();
        recommendation.setWriter(writer);
        recommendation.setPrintResults(this.printResults);
        recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy

        // calculate all list of recommendations
        for (Requirement userRequirement : userRequirements) {
            val recommendationList = recommendation.recommend(userRequirement);

            if (!recommendationList.empty()) {
                all.add(recommendationList);
            }
        }

        return all;
    }
}
