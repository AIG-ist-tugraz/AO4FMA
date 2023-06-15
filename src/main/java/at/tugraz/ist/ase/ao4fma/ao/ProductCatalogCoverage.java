/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Implementation of Product Catalog Coverage
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class ProductCatalogCoverage extends AnalysisOperation {

    File fmFile;
    File filterFile;
    File productsFile;

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

        double coverage = (double) countAtLeastOne / results.size();

        // print results
        LoggerUtils.indent();
        if (printResults) {
            String message = String.format("%sNumber of products recommended at least one: %s", LoggerUtils.tab(), countAtLeastOne);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sNumber of products: %s", LoggerUtils.tab(), results.size());
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sProduct Catalog Coverage: %s", LoggerUtils.tab(), coverage);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return coverage;
    }

}
