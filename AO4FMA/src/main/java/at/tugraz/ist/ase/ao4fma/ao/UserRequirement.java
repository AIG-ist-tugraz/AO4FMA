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
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class UserRequirement {

    @Setter
    BufferedWriter writer = null;

    public List<Requirement> getRequirements(File fmFile) throws FeatureModelParserException, IOException {
        String message = String.format("%sAll consistent user requirements:", LoggerUtils.tab());
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }

        val fm = Utilities.loadFeatureModel(fmFile);
        List<String> leafFeatures = Utilities.getLeafFeatures(fm);

        Integer[] indexesArr = Utilities.createIndexesArray(leafFeatures.size());
        Set<Integer> targetSet = Sets.newHashSet(indexesArr);

        List<Requirement> URs = new ArrayList<>();
        for (int card = 1; card <= leafFeatures.size(); card++) {
            Set<Set<Integer>> combinations = Sets.combinations(targetSet, card);

            for (Set<Integer> combination : combinations) {
                // generate variable_value_combinations
                List<String> var_value_combs = Utilities.generateFMValueCombinations(card, combination, leafFeatures);

                var_value_combs.forEach(var_value_comb -> {
                    Requirement requirement = Utilities.convertToRequirement(var_value_comb, fm);
                    URs.add(requirement);
                });
            }
        }

        // print all user requirements
        LoggerUtils.indent();
        Utilities.printList(URs, writer);
        LoggerUtils.outdent();

        return URs;
    }
}
