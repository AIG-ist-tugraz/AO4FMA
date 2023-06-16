/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core.rank;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.ao4fma.core.ProductAssortment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapterFactory.createConfigurator;

public class SimpleProductRankCalculator implements IProductRankCalculatable {

    File fmFile;
    File filterFile;
    File productsFile;

    FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm;
    ConfiguratorAdapter configurator;

    public SimpleProductRankCalculator(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public ProductAssortment calculate(ProductAssortment products) throws FeatureModelParserException, IOException {
        fm = Utilities.loadFeatureModel(fmFile);
        configurator = createConfigurator(fmFile, filterFile, productsFile, null);

        // loop over all products
        val new_products = new ProductAssortment();
        for (Product p: products) {
            val rf = calculate(p);
            val product = Product.builder()
                    .id(p.id())
                    .properties(p.properties())
                    .fm_values(p.fm_values())
                    .rf(rf)
                    .rf_calculated(true)
                    .build();
            new_products.add(product);
        }
        return new_products;
    }

    private int calculate(Product product) {
        // loop over all features
        AtomicInteger rf = new AtomicInteger();
        for (Feature f: fm.getBfFeatures()) {
            val req = Requirement.requirementBuilder()
                    .assignments(List.of(Assignment.builder()
                            .variable(f.getName())
                            .value("true")
                            .build()))
                    .build();

            configurator.findAllSolutions(req);

            if (configurator.getProducts().parallelStream().anyMatch(p -> p.equals(product))) {
                rf.getAndIncrement();
            }
        };
        return rf.get();
    }

    public void dispose() {
        fm = null;
        configurator = null;
    }
}
