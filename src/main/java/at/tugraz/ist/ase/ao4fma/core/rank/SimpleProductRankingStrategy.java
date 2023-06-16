/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core.rank;

import at.tugraz.ist.ase.ao4fma.core.Product;
import lombok.NonNull;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class SimpleProductRankingStrategy implements IProductRankingStrategy {

//    FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm;
//    ConfiguratorAdapter configurator;

    @Override
    public List<Product> rank(List<Product> products) {
//        this.configurator = configurator;
//        this.fm = fm;

//        var newProducts = products.stream().map(p -> {
//            if (!p.rf_calculated()) {
//                return Product.builder()
//                        .id(p.id())
//                        .properties(p.properties())
//                        .fm_values(p.fm_values())
//                        .rf(calculateRF(p))
//                        .rf_calculated(true)
//                        .build();
//            } else {
//                return Product.builder()
//                        .id(p.id())
//                        .properties(p.properties())
//                        .fm_values(p.fm_values())
//                        .rf(p.rf())
//                        .rf_calculated(true)
//                        .build(); // this is a copy
//            }
//        }).toList();

        return products.stream()
                .sorted(Comparator.comparing(Product::rf).reversed()
                        .thenComparing(Product::id)).toList();
    }

    @Override
    public IProductRankCalculatable getCalculator(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        return new SimpleProductRankCalculator(fmFile, filterFile, productsFile);
    }

//    private int calculateRF(Product product) {
//        // loop over all features
//        AtomicInteger rf = new AtomicInteger();
//        for (Feature f: fm.getBfFeatures()) {
//            val req = Requirement.requirementBuilder()
//                    .assignments(List.of(Assignment.builder()
//                            .variable(f.getName())
//                            .value("true")
//                            .build()))
//                    .build();
//
//            configurator.findAllSolutions(req);
//
//            if (configurator.getProducts().parallelStream().anyMatch(p -> p.equals(product))) {
//                rf.getAndIncrement();
//            }
//        };
//        return rf.get();
//    }
}
