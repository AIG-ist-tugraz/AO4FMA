/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.core.*;
import at.tugraz.ist.ase.ao4fma.core.rank.SimpleProductRankingStrategy;
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
 * Implementation of Efficiency of Products
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class Efficiency extends AnalysisOperation {

    File transactionsFile;

    List<Requirement> userRequirements = null;
    TransactionList mappedTransactions = null;

    public Efficiency(@NonNull File fmFile, @NonNull File filterFile,
                      @NonNull File productsFile, @NonNull File transactionsFile) {
        super(fmFile, filterFile, productsFile);
        this.transactionsFile = transactionsFile;
    }

    public HashMap<Product, Double> calculate() throws IOException, FeatureModelParserException {
        loadData();

        // load products
        val products = ProductsReader.read(productsFile);

        // calculate efficiency for each product
        HashMap<Product, Double> efficiencies = new HashMap<>();
        for (Product product : products) {
            efficiencies.put(product, calculate(product));
        }

        return efficiencies;
    }

    public double calculate(Product product) throws FeatureModelParserException, IOException {
        Utilities.printInfo(printResults, writer, "Product", product.id());

        if (userRequirements == null) {
            loadData();
        }

        // NUMERATOR
        long selections = mappedTransactions.selections(product);

        // DENOMINATOR
        long displaycounts = 0;
        // identify recommendation
        Recommendation recommendation = Recommendation.builder()
                .fmFile(fmFile)
                .filterFile(filterFile)
                .productsFile(productsFile)
                .build();
        recommendation.setWriter(writer);
        recommendation.setPrintResults(false);
        recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy

        for (Transaction t : mappedTransactions) {
            RecommendationList recommendationList = recommendation.recommend(t.req());

            if (recommendationList.contains(product)) {
                displaycounts++;
            }
        }

        // calculate efficiency
        double efficiency = (double) selections / displaycounts;

        // print results
        LoggerUtils.indent();
        if (printResults) {
            String message = String.format("%sSelections: %s", LoggerUtils.tab(), selections);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sDisplayCounts: %s", LoggerUtils.tab(), displaycounts);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sEfficiency: %s", LoggerUtils.tab(), efficiency);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return efficiency;
    }

    private void loadData() throws FeatureModelParserException, IOException {
        UserRequirement urOperation = new UserRequirement(fmFile, filterFile, productsFile);
        // calculate all list of user requirements
        userRequirements = urOperation.getRequirements();

        val transactions = TransactionReader.read(transactionsFile);

        // update data for transactions
        mappedTransactions = new TransactionList();
        transactions.getTransactions().parallelStream().forEach(t -> {
            int ur_id = t.ur_id();
            val ur = userRequirements.get(ur_id);

            // copy transaction
            val newTransaction = new Transaction(t.id(), t.ur_id(), t.product_id(), ur);
            mappedTransactions.add(newTransaction);
        });
    }
}
