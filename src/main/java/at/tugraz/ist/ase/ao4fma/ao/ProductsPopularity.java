/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.core.*;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of Popularity of Products
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class ProductsPopularity extends AnalysisOperation {

    File transactionsFile;

    List<Requirement> userRequirements = null;
    TransactionList mappedTransactions = null;

    public ProductsPopularity(@NonNull File fmFile, @NonNull File filterFile,
                              @NonNull File productsFile, @NonNull File transactionsFile) {
        super(fmFile, filterFile, productsFile);
        this.transactionsFile = transactionsFile;
    }

    public HashMap<Product, Double> calculate() throws IOException, FeatureModelParserException {
        loadData();

        val products = ProductsReader.read(productsFile); // don't need to calculate rf

        // calculate visibility for each product
        HashMap<Product, Double> popularities = new HashMap<>();
        for (Product product : products) {
            val popularityValue = calculate(product);
            popularities.put(product, popularityValue);
        }

        return popularities;
    }

    private void loadData() throws FeatureModelParserException, IOException {
        UserRequirement urOperation = new UserRequirement(fmFile, filterFile, productsFile);
        // calculate all list of user requirements
        userRequirements = urOperation.getRequirements();

        val transactions = TransactionReader.read(transactionsFile);

        // update data for transactions
        mappedTransactions = new TransactionList();
        transactions.forEach(t -> {
            int ur_id = t.ur_id();
            val ur = userRequirements.get(ur_id);

            // copy transaction
            val newTransaction = new Transaction(t.id(), t.ur_id(), t.product_id(), ur);
            mappedTransactions.add(newTransaction);
        });
    }

    public double calculate(Product product) throws FeatureModelParserException, IOException {
        if (printResults) {
            String message = String.format("%sProduct: %s", LoggerUtils.tab(), product.id());
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }

        if (userRequirements == null) {
            loadData();
        }

        // NUMERATOR
        long selections = 0;
        for (Transaction t : mappedTransactions) {
            if (t.product_id().equals(product.id())) {
                selections++;
            }
        }

        // DENOMINATOR - included
        int product_selections = mappedTransactions.size();

        // calculate popularity
        double popularity = (double) selections / product_selections;

        // print results
        LoggerUtils.indent();
        if (printResults) {
            String message = String.format("%sSelections: %s", LoggerUtils.tab(), selections);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sProduct selections: %s", LoggerUtils.tab(), product_selections);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sProduct Popularity: %s", LoggerUtils.tab(), popularity);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return popularity;
    }

}
