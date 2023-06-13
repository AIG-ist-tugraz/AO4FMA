/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma;

import at.tugraz.ist.ase.ao4fma.ao.Recommendation;
import at.tugraz.ist.ase.ao4fma.ao.Restrictiveness;
import at.tugraz.ist.ase.ao4fma.ao.UserRequirement;
import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.ao4fma.product.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.configurator.ConfigurationModel;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Main {

    private static final List<String> QUERY_RESTRICTIVENESS_FILES = Arrays.asList("q1_1.csv", "q1_2.csv", "q1_3.csv");

    public static void main(String[] args) throws IOException, FeatureModelParserException {
        val fmFile = new File(args[0]);
        val filterFile = new File(args[1]);
        val productsFile = new File(args[2]);
        val queries_folder = args[3];

        @Cleanup val writer = new BufferedWriter(new FileWriter("results.txt"));
        LoggerUtils.setUseThreadInfo(false);

        // All user requirements
        UserRequirement userRequirement = new UserRequirement();
        List<Requirement> requirements = userRequirement.getRequirements(fmFile);


        String message = String.format("%sAll consistent user requirements:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();
        findProducts(fmFile, writer);

//        message = String.format("%n%sProduct Assortment:", LoggerUtils.tab());
//        log.info(message);
//        writer.write(message); writer.newLine();
//        findProducts(fm, filterFile, products, writer);

        // Restrictiveness
        restrictiveness(fmFile, filterFile, productsFile, writer, queries_folder);

        // Restrictiveness for all features
        restrictivenessAllLeafFeatures(fmFile, filterFile, productsFile, writer);
    }

    private static void restrictiveness(File fmFile,
                                        File filterFile,
                                        File productsFile,
                                        BufferedWriter writer,
                                        String queries_folder) throws IOException, FeatureModelParserException {
        String message = String.format("%n%sI. RESTRICTIVENESS:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();

        // create the operation
        val restrictiveness = new Restrictiveness(fmFile, filterFile, productsFile);
        restrictiveness.setWriter(writer);

        LoggerUtils.indent();
        for (String queryFile : QUERY_RESTRICTIVENESS_FILES) {
            String query = queries_folder + queryFile;
            val req = Utilities.readRequirement(query);

            double restrict_value = restrictiveness.calculate(req);
        }
        LoggerUtils.outdent();
    }

    private static void restrictivenessAllLeafFeatures(File fmFile,
                                                       File filterFile,
                                                       File productsFile,
                                                BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%n%sI.1. RESTRICTIVENESS - ALL LEAF FEATURES:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();

        // create the operation
        val restrictiveness = new Restrictiveness(fmFile, filterFile, productsFile);
        restrictiveness.setWriter(writer);

        LoggerUtils.indent();
        LinkedHashMap<String, Double> results = restrictiveness.calculate4AllLeafFeatures();
        LoggerUtils.outdent();
    }

    public static void findProducts(File fmFile,
                                    BufferedWriter writer) throws IOException, FeatureModelParserException {
        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        // convert the feature model into FMKB
        val kb = new FMKB<>(fm, false);

        val configurationModel = new ConfigurationModel(kb, true);
        configurationModel.initialize();
        val configurator = Configurator.builder()
                .kb(kb)
                .configurationModel(configurationModel)
                .translator(new FMSolutionTranslator())
                .build();

        configurator.findAllSolutions(false,0);

        // map the solutions to products
        val products = configurator.getSolutions().stream().map(solution -> new Product("0",null, solution, 0)).collect(Collectors.toList());

        Utilities.printList(products, writer);
    }

    public static void findProducts(File fmFile,
                                    File filterFile,
                                    File productsFile,
                                    BufferedWriter writer) throws IOException, FeatureModelParserException {
        Recommendation recommendation = Recommendation.builder()
                .fmFile(fmFile)
                .filterFile(filterFile)
                .productsFile(productsFile)
                .build();
        recommendation.setWriter(writer);
        recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy
        val products = recommendation.recommend(null);
    }
}
