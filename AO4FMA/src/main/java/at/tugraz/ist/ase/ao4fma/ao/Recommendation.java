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
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.ao4fma.product.ProductsReader;
import at.tugraz.ist.ase.ao4fma.product.rank.IProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Recommendation {

    File fmFile;
    File filterFile;
    File productsFile;

    @Setter
    BufferedWriter writer = null;

    @Setter
    IProductRankingStrategy rankingStrategy = null;

    @Builder
    public Recommendation(File fmFile, File filterFile, File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public List<Product> recommend(Requirement req) throws FeatureModelParserException, IOException {
        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);
        // read products
        val products = ProductsReader.read(productsFile);

        // create knowledge base
        val kb = new FMKB<>(fm, false);
        val translator = new MZN2ChocoTranslator(); // a translator for filter constraints
        // model for the configuration task
        val productAwareConfigurationModel = ProductAwareConfigurationModel.builder()
                .kb(kb)
                .rootConstraints(true)
                .filterFile(filterFile)
                .translator(translator)
                .build();
        productAwareConfigurationModel.initialize();
        // create configurator
        val configurator = ConfiguratorAdapter.configuratorAdapterBuilder()
                .kb(kb)
                .model(productAwareConfigurationModel)
                .translator(new FMSolutionTranslator())
                .productAssortment(products)
                .build();

        configurator.findAllSolutions(req); // identify all products that satisfy the Requirement

        // reorder the products according to the recommendation strategy
        List<Product> recommendedProducts;
        if (rankingStrategy != null) {
            recommendedProducts = rankingStrategy.rank(configurator.getProducts(), fm, configurator);
        } else {
            recommendedProducts = configurator.getProducts();
        }

        Utilities.printProducts(recommendedProducts, writer);

        return recommendedProducts;
    }
}
