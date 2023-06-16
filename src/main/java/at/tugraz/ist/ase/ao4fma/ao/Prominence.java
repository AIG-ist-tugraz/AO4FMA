/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.core.RecommendationList;
import at.tugraz.ist.ase.ao4fma.core.Transaction;
import at.tugraz.ist.ase.ao4fma.core.TransactionList;
import at.tugraz.ist.ase.ao4fma.core.TransactionReader;
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
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implementation of Prominence of Features
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Slf4j
public class Prominence extends AnalysisOperation {

    File transactionsFile;

    List<Requirement> userRequirements = null;
    TransactionList mappedTransactions = null;

    public Prominence(@NonNull File fmFile, @NonNull File filterFile,
                      @NonNull File productsFile, @NonNull File transactionsFile) {
        super(fmFile, filterFile, productsFile);
        this.transactionsFile = transactionsFile;
    }

    public HashMap<String, Double> calculate() throws IOException, FeatureModelParserException {
        loadData();

        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        LinkedHashMap<String, Double> results = new LinkedHashMap<>();
        for (String feature : Utilities.getLeafFeatures(fm)) {
            val prominence = calculate(feature);

            results.put(feature, prominence);
        }
        return results;
    }

    public double calculate(String feature) throws IOException, FeatureModelParserException {
        Utilities.printInfo(printResults, writer, "Feature", feature);

        if (userRequirements == null) {
            loadData();
        }

        // NUMERATOR
        long explicitly_selected = 0;
        for (Transaction t : mappedTransactions) {
            if (t.req().getAssignments().stream().anyMatch(a -> a.getVariable().equals(feature) && a.getValue().equals("true"))) {
                explicitly_selected++;
            }
        }

        // DENOMINATOR - included
        long included= 0;
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

            if (recommendationList.contains(feature)) {
                included++;
            }
        }

        // prominence
        double prominence = (double) explicitly_selected / included;

        // print results
        LoggerUtils.indent();
        if (printResults) {
            String message = String.format("%sNumber of time when f selected explicitly: %s", LoggerUtils.tab(), explicitly_selected);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sNumber of recommendation where f included in: %s", LoggerUtils.tab(), included);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sProminence: %s", LoggerUtils.tab(), prominence);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return prominence;
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
}
