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

import static at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapterFactory.createConfigurator;

@Slf4j
public class UserRequirement extends AnalysisOperation {

    public List<Requirement> getRequirements(File fmFile) throws FeatureModelParserException {
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

        return URs;
    }

    public List<Requirement> getGlobalConsistentUserRequirements(File fmFile) throws FeatureModelParserException, IOException {
        List<Requirement> URs = getRequirements(fmFile);

        // filter consistent user requirements
        val configurator = createConfigurator(fmFile);

        List<Requirement> list = new ArrayList<>();
        for (Requirement UR : URs) {
            if (configurator.isConsistent(UR)) {
                list.add(UR);
            }
        }
        return list;
    }

    public List<Requirement> getConsistentUserRequirements(File fmFile, File filterFile, File productsFile) throws FeatureModelParserException, IOException {
        List<Requirement> URs = getRequirements(fmFile);

        // filter consistent user requirements
        val configurator = createConfigurator(fmFile, filterFile, productsFile);

        List<Requirement> list = new ArrayList<>();
        for (Requirement UR : URs) {
            configurator.findAllSolutions(UR);

            if (configurator.getProducts().size() > 0) {
                list.add(UR);
            }
        }
        return list;
    }

    public List<Requirement> getInconsistentUserRequirements(File fmFile, File filterFile, File productsFile) throws FeatureModelParserException, IOException {
        List<Requirement> URs = getRequirements(fmFile);

        // filter consistent user requirements
        val configurator = createConfigurator(fmFile, filterFile, productsFile);

        List<Requirement> list = new ArrayList<>();
        for (Requirement UR : URs) {
            configurator.findAllSolutions(UR);

            if (configurator.getProducts().size() == 0) {
                list.add(UR);
            }
        }
        return list;
    }
}
