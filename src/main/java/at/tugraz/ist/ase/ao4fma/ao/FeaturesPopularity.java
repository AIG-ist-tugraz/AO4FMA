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
import at.tugraz.ist.ase.ao4fma.core.TransactionsReader;
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

@Slf4j
public class FeaturesPopularity extends AnalysisOperation {

    File fmFile;
    File filterFile;
    File productsFile;
    File transactionsFile;

    List<Requirement> userRequirements = null;
    TransactionList mappedTransactions = null;

    public FeaturesPopularity(@NonNull File fmFile, @NonNull File filterFile,
                              @NonNull File productsFile, @NonNull File transactionsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
        this.transactionsFile = transactionsFile;
    }

    public HashMap<String, Double> calculate() throws IOException, FeatureModelParserException {
        loadData();

        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        LinkedHashMap<String, Double> results = new LinkedHashMap<>();
        for (String feature : Utilities.getLeafFeatures(fm)) {
            val popularity = calculate(feature);

            results.put(feature, popularity);
        }
        return results;
    }

    private void loadData() throws FeatureModelParserException, IOException {
        UserRequirement urOperation = new UserRequirement();
        // calculate all list of user requirements
        userRequirements = urOperation.getRequirements(fmFile);

        val transactions = TransactionsReader.read(transactionsFile);

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

    public double calculate(String feature) throws IOException, FeatureModelParserException {
        if (printResults) {
            String message = String.format("%sFeature: %s", LoggerUtils.tab(), feature);
            log.info(message);
            if (writer != null) {
                writer.write(message); writer.newLine();
            }
        }

        if (userRequirements == null) {
            loadData();
        }

        // NUMERATOR
        long selections = 0;
        for (Transaction t : mappedTransactions) {
            if (t.req().getAssignments().stream().anyMatch(a -> a.getVariable().equals(feature) && a.getValue().equals("true"))) {
                selections++;
            }
        }

        // DENOMINATOR - included
        int feature_selections = 0;
        for (Transaction t : mappedTransactions) {
            feature_selections += t.req().getAssignments().size();
        }

        // popularity
        double popularity = (double) selections / feature_selections;

        // print results
        LoggerUtils.indent();
        if (printResults) {
            String message = String.format("%sSelections: %s", LoggerUtils.tab(), selections);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sFeature selections: %s", LoggerUtils.tab(), feature_selections);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
            message = String.format("%sFeature Popularity: %s", LoggerUtils.tab(), popularity);
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
