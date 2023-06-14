/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
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
public class Controversy extends AnalysisOperation {

    File fmFile;
    File filterFile;
    File productsFile;

    List<Requirement> userRequirements = null;

    public Controversy(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }

    public HashMap<String, Double> calculate() throws IOException, FeatureModelParserException {
        LinkedHashMap<String, Double> results = new LinkedHashMap<>();

        UserRequirement urOperation = new UserRequirement();
        // calculate all list of user requirements
        userRequirements = urOperation.getInconsistentUserRequirements(fmFile, filterFile, productsFile);

        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        for (String feature : Utilities.getLeafFeatures(fm)) {
            val controversyValue = calculate(feature);

            results.put(feature, controversyValue);
        }

        return results;
    }

    public double calculate(String feature) throws FeatureModelParserException, IOException {
        if (printResults) {
            String message = String.format("%sFeature: %s", LoggerUtils.tab(), feature);
            log.info(message);
            if (writer != null) {
                writer.write(message);
                writer.newLine();
            }
        }

        if (userRequirements == null) {
            UserRequirement urOperation = new UserRequirement();
            // calculate all list of user requirements
            userRequirements = urOperation.getInconsistentUserRequirements(fmFile, filterFile, productsFile);
        }

        // calculate controversy
        int count = 0;
        for (Requirement requirement : userRequirements) {
            if (requirement.getAssignments().stream().anyMatch(a -> a.getVariable().equals(feature))) {
                count++;
            }
        }

        // calculate controversy
        LoggerUtils.indent();
        double controversy = count / (double) userRequirements.size();

        // print results
        if (printResults) {
            String message = String.format("%sTotal URs including f: %s", LoggerUtils.tab(), count);
            log.info(message);
            if (writer != null) {
                writer.write(message); writer.newLine();
            }
            message = String.format("%sTotal inconsistent URs: %s", LoggerUtils.tab(), userRequirements.size());
            log.info(message);
            if (writer != null) {
                writer.write(message); writer.newLine();
            }
            message = String.format("%sControversy: %s", LoggerUtils.tab(), controversy);
            log.info(message);
            if (writer != null) {
                writer.write(message); writer.newLine();
            }
        }
        LoggerUtils.outdent();

        return controversy;
    }

}
