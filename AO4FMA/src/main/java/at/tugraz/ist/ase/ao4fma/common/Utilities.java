/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2022-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.common;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.builder.RequirementBuilder;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.fm.parser.FMParserFactory;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParser;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
@Slf4j
public class Utilities {

    public FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint>
        loadFeatureModel(@NonNull File fileFM) throws FeatureModelParserException {
        // create the factory for anomaly feature models
        FMParserFactory<Feature, AbstractRelationship<Feature>, CTConstraint>
                factory = FMParserFactory.getInstance();

        // create the parser
        @Cleanup("dispose")
        FeatureModelParser<Feature, AbstractRelationship<Feature>, CTConstraint>
                parser = factory.getParser(fileFM.getName());
        return parser.parse(fileFM);
    }

//    public List<Feature> getLeafFeatures(@NonNull FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm) {
//        return fm.getBfFeatures().stream().filter(Feature::isLeaf).collect(Collectors.toList());
//    }

    public List<String> getLeafFeatures(@NonNull FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm) {
        return fm.getBfFeatures().stream().filter(Feature::isLeaf).map(Feature::getName).collect(Collectors.toList());
    }

    /**
     * Replace special characters (e.g. space, -) by underscore characters.
     *
     * @param st - a string needed to replace
     * @return a new string in which the special characters are replaced
     */
    public String replaceSpecialCharactersByUnderscore(@NonNull String st) {
        return st.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public String getDatabaseName(@NonNull File fileFM) {
        return fileFM.getName().substring(0, fileFM.getName().indexOf("."));
    }

    public String getTableName(@NonNull File fileFM) {
        return replaceSpecialCharactersByUnderscore(getDatabaseName(fileFM));
    }

    public List<String> getColumnNames(@NonNull FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm) {
        return fm.getBfFeatures().stream().map(f -> replaceSpecialCharactersByUnderscore(f.getName())).toList();
    }

    public List<String> getColumnNames(@NonNull Solution firstProduct) {
        return firstProduct.getAssignments().stream().map(f -> replaceSpecialCharactersByUnderscore(f.getVariable())).toList();
    }

    public List<String> getColumnSpecs(Solution firstProduct) {
        List<String> columnSpecs = new ArrayList<>();
        for (Assignment assignment : firstProduct.getAssignments()) {
            if (assignment.getValue().equals("true") || assignment.getValue().equals("false"))
                columnSpecs.add(assignment.getVariable() + " BOOLEAN");
            else
                columnSpecs.add(assignment.getVariable() + " VARCHAR(255)");
        }
        return columnSpecs;
    }

    public Requirement readRequirement(@NonNull String filepath) throws IOException {
        File file = new File(filepath);

        Requirement req = null;
        if (file.exists()) { // exists
            log.trace("{}Reading file {}", LoggerUtils.tab(), file.getName());
            @Cleanup InputStream is = new FileInputStream(file);
            @Cleanup BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            RequirementBuilder urBuilder = new RequirementBuilder();
            req = urBuilder.build(br.readLine());
        } else {
            log.error("{}Folder {} does not exist!", LoggerUtils.tab(), filepath);
        }

        return req;
    }

    public Integer[] createIndexesArray(int numVar) {
        return IntStream.range(0, numVar).mapToObj(i -> i + 1).toArray(Integer[]::new);
    }

    public List<String> generateFMValueCombinations(int card,
                                                   Set<Integer> comb,
                                                   List<String> leafFeatures) {
        // variable-value assignment
        List<String> value_combs = new LinkedList<>();
        value_combs.add("");
        List<Integer> original_index = Lists.newArrayList(comb);

        for (int j = 0; j < card; j++) {
            String feature = leafFeatures.get(original_index.get(j) - 1);
            value_combs = generateValueCombinations(value_combs, feature);
        }
        return value_combs;
    }

    private static List<String> generateValueCombinations(List<String> combs,
                                                          String feature) {
        List<String> newCombs = new LinkedList<>();
        List<String> values = List.of("true"); // "false"

        for (String comb : combs) {
            for (String value : values) {
                String n_comb;

                if (comb.isEmpty()) {
                    n_comb = feature + "=" + value;
                } else {
                    n_comb = comb + "," + feature + "=" + value;
                }

                newCombs.add(n_comb);
            }
        }

        return newCombs;
    }

    public static Requirement convertToRequirement(String varValueComb, FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm) {
        RequirementBuilder urBuilder = new RequirementBuilder();
        return urBuilder.build(varValueComb);
    }

    public void printSolutions(List<Solution> solutions, BufferedWriter writer) throws IOException {
        int counter = 0;
        for (Solution s : solutions) {
            String message = String.format("%s%s %s", LoggerUtils.tab(), ++counter, s);
            log.info(message);
            if (writer != null) {
                writer.write(message); writer.newLine();
            }
        }
    }
}
