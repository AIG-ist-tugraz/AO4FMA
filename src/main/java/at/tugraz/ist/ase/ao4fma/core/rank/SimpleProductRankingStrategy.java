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

    @Override
    public List<Product> rank(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(Product::rf).reversed()
                        .thenComparing(Product::id)).toList();
    }

    @Override
    public IProductRankCalculatable getCalculator(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        return new SimpleProductRankCalculator(fmFile, filterFile, productsFile);
    }
}
