/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.product;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@UtilityClass
public class ProductsReader {
    public ProductAssortment read(@NonNull File file) throws IOException {
        log.debug("{}Reading the product file - {} >>>", LoggerUtils.tab(), file.getName());

        @Cleanup BufferedReader reader = Files.newReader(file, Charsets.UTF_8);

        ProductAssortment products = new ProductAssortment();

        String line = reader.readLine();
        while (line != null) {
            String[] tokens = line.split(",");

            // first token is the product id
            String[] items = tokens[0].split("=");
            String id = items[1];

            List<Assignment> assignments = new LinkedList<>();
            // the rest of the tokens are the assignments
            for (int i = 1; i < tokens.length; i++) {
                String token = tokens[i];
                items = token.split("=");

                String variable = items[0];
                String value = items[1];

                Assignment ur = Assignment.builder().variable(variable).value(value).build();

                assignments.add(ur);
            }

            Product product = new Product(id, Solution.builder().assignments(assignments).build());
            products.add(product);

            line = reader.readLine();
        }

        return products;
    }
}
