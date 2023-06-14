/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core.rank;

import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import lombok.val;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleProductRankingStrategy implements IProductRankingStrategy {

    FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm;
    ConfiguratorAdapter configurator;

    @Override
    public List<Product> rank(List<Product> products,
                              FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm,
                              ConfiguratorAdapter configurator) {
        this.configurator = configurator;
        this.fm = fm;

        return products.stream().map(p -> new Product(p.id(), p.properties(), p.fm_values(), calculateRF(p)))
                        .sorted(Comparator.comparingInt(Product::rf).reversed()).toList();
    }

    private int calculateRF(Product product) {
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

            if (configurator.getProducts().stream().anyMatch(p -> p.equals(product))) {
                rf.getAndIncrement();
            }
        };
        return rf.get();
    }
}
