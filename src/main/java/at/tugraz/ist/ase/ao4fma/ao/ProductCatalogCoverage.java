/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ProductCatalogCoverage {

    File fmFile;
    File filterFile;
    File productsFile;

    @Setter
    BufferedWriter writer = null;
    @Setter
    boolean printResults = true;

    public ProductCatalogCoverage(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public double calculate() throws IOException, FeatureModelParserException {
        val accessibility = new Accessibility(fmFile, filterFile, productsFile);
        accessibility.setWriter(writer);
        accessibility.setPrintResults(false);

        HashMap<Product, Double> results = accessibility.calculate();

        // NUMERATOR
        int countAtLeastOne = 0;
        for (Product p : results.keySet()) {
            if (results.get(p) > 0) {
                countAtLeastOne++;
            }
        }

        return (double) countAtLeastOne / results.size();
    }

}
