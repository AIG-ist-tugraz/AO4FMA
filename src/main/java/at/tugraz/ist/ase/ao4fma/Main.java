/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma;

import at.tugraz.ist.ase.ao4fma.ao.*;
import at.tugraz.ist.ase.ao4fma.cli.CmdLineOptions;
import at.tugraz.ist.ase.ao4fma.cli.ConfigManager;
import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.ao4fma.core.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static at.tugraz.ist.ase.ao4fma.cli.ConfigManager.defaultConfigFile;
import static at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapterFactory.createConfigurator;

@Slf4j
public class Main {

    private static final List<String> QUERY_RESTRICTIVENESS_FILES = Arrays.asList("q1_1.csv", "q1_2.csv", "q1_3.csv");

    public static void main(String[] args) throws IOException, FeatureModelParserException {
        String programTitle = "Analysis Operations On The Run";
        String usage = "Usage: java -jar ao4fma.jar [options]]";

        // Parse command line arguments
        CmdLineOptions cmdLineOptions = new CmdLineOptions(null, programTitle, null, usage);
        cmdLineOptions.parseArgument(args);

        if (cmdLineOptions.isHelp()) {
            cmdLineOptions.printUsage();
            System.exit(0);
        }

        cmdLineOptions.printWelcome();

        String confFile = cmdLineOptions.getConfFile() == null ? defaultConfigFile : cmdLineOptions.getConfFile();
        ConfigManager cfg = ConfigManager.getInstance(confFile);

        val fmFile = cfg.getFmFile();
        val filterFile = cfg.getFilterFile();
        val productsFile = cfg.getProductsFile();
        val transactionsFile = cfg.getTransactionsFile();
        val queries_folder = cfg.getQueries_folder();
        val resultFile = cfg.getResultFile();

        @Cleanup val writer = new BufferedWriter(new FileWriter(resultFile));
        LoggerUtils.setUseThreadInfo(false);

        // Restrictiveness
        restrictiveness(fmFile, filterFile, productsFile, writer, queries_folder);

        // Restrictiveness for all features
        restrictivenessAllLeafFeatures(fmFile, filterFile, productsFile, writer);

        // Accessibility
        accessibility(fmFile, filterFile, productsFile, writer);

        // Product Catalog Coverage
        productCatalogCoverage(fmFile, filterFile, productsFile, writer);

        // Visibility of Products
        visibilityOfProducts(fmFile, filterFile, productsFile, writer);

        // Controversy of Features
        controversyOfFeatures(fmFile, filterFile, productsFile, writer);

        // Global controversy
        globalControversy(fmFile, filterFile, productsFile, writer);

        // Efficiency
        efficiencyOfProducts(fmFile, filterFile, productsFile, transactionsFile, writer);

        // Prominence
        prominenceOfFeatures(fmFile, filterFile, productsFile, transactionsFile, writer);

        // Popularity of Features
        popularityOfFeatures(fmFile, filterFile, productsFile, transactionsFile, writer);

        // Popularity of Products
        popularityOfProducts(fmFile, filterFile, productsFile, transactionsFile, writer);
    }

    private static void restrictiveness(File fmFile,
                                        File filterFile,
                                        File productsFile,
                                        BufferedWriter writer,
                                        String queries_folder) throws IOException, FeatureModelParserException {
        String message = String.format("%sI. RESTRICTIVENESS:", LoggerUtils.tab());
        log.info(message);
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
        String message = String.format("%sI.1. RESTRICTIVENESS - ALL LEAF FEATURES:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val restrictiveness = new Restrictiveness(fmFile, filterFile, productsFile);
        restrictiveness.setWriter(writer);

        LoggerUtils.indent();
        LinkedHashMap<String, Double> results = restrictiveness.calculate();
        LoggerUtils.outdent();
    }

    private static void accessibility(File fmFile,
                                      File filterFile,
                                      File productsFile,
                                      BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sII. ACCESSIBILITY:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val accessibility = new Accessibility(fmFile, filterFile, productsFile);
        accessibility.setWriter(writer);

        LoggerUtils.indent();
        HashMap<Product, Double> results = accessibility.calculate();
        LoggerUtils.outdent();
    }

    private static void productCatalogCoverage(File fmFile,
                                              File filterFile,
                                              File productsFile,
                                              BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sIII. PRODUCT CATALOG COVERAGE:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val coverage = new ProductCatalogCoverage(fmFile, filterFile, productsFile);
        coverage.setWriter(writer);

        LoggerUtils.indent();
        double results = coverage.calculate();
        LoggerUtils.outdent();
    }

    private static void visibilityOfProducts(File fmFile,
                                               File filterFile,
                                               File productsFile,
                                               BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sIV. VISIBILITY OF PRODUCTS:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val visibility = new Visibility(fmFile, filterFile, productsFile);
        visibility.setWriter(writer);

        LoggerUtils.indent();
        HashMap<Product, Double> results = visibility.calculate();
        LoggerUtils.outdent();
    }

    private static void controversyOfFeatures(File fmFile,
                                             File filterFile,
                                             File productsFile,
                                             BufferedWriter writer) throws IOException, FeatureModelParserException {

        String message = String.format("%sV. CONTROVERSY OF FEATURES:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val controversy = new Controversy(fmFile, filterFile, productsFile);
        controversy.setWriter(writer);

        LoggerUtils.indent();
        HashMap<String, Double> results = controversy.calculate();
        LoggerUtils.outdent();
    }

    private static void globalControversy(File fmFile,
                                          File filterFile,
                                          File productsFile,
                                          BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sVI. GLOBAL CONTROVERSY:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val controversy = new GlobalControversy(fmFile, filterFile, productsFile);
        controversy.setWriter(writer);

        LoggerUtils.indent();
        double results = controversy.calculate();
        LoggerUtils.outdent();
    }

    private static void efficiencyOfProducts(File fmFile,
                                          File filterFile,
                                          File productsFile,
                                          File transactionsFile,
                                          BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sVII. EFFICIENCY OF PRODUCTS:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val efficiency = new Efficiency(fmFile, filterFile, productsFile, transactionsFile);
        efficiency.setWriter(writer);

        LoggerUtils.indent();
        HashMap<Product, Double> results = efficiency.calculate();
        LoggerUtils.outdent();
    }

    private static void prominenceOfFeatures(File fmFile,
                                             File filterFile,
                                             File productsFile,
                                             File transactionsFile,
                                             BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sVIII. PROMINENCE OF FEATURES:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val prominence = new Prominence(fmFile, filterFile, productsFile, transactionsFile);
        prominence.setWriter(writer);

        LoggerUtils.indent();
        HashMap<String, Double> results = prominence.calculate();
        LoggerUtils.outdent();
    }

    private static void popularityOfFeatures(File fmFile,
                                             File filterFile,
                                             File productsFile,
                                             File transactionsFile,
                                             BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sIX. POPULARITY OF FEATURES:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val popularity = new FeaturesPopularity(fmFile, filterFile, productsFile, transactionsFile);
        popularity.setWriter(writer);

        LoggerUtils.indent();
        HashMap<String, Double> results = popularity.calculate();
        LoggerUtils.outdent();
    }

    private static void popularityOfProducts(File fmFile,
                                             File filterFile,
                                             File productsFile,
                                             File transactionsFile,
                                             BufferedWriter writer) throws IOException, FeatureModelParserException {
        String message = String.format("%sX. POPULARITY OF PRODUCTS:", LoggerUtils.tab());
        log.info(message);
        writer.write(message); writer.newLine();

        // create the operation
        val popularity = new ProductsPopularity(fmFile, filterFile, productsFile, transactionsFile);
        popularity.setWriter(writer);

        LoggerUtils.indent();
        HashMap<Product, Double> results = popularity.calculate();
        LoggerUtils.outdent();
    }

    public static void findProducts(File fmFile,
                                    BufferedWriter writer) throws IOException, FeatureModelParserException {
        // load the feature model
        val configurator = createConfigurator(fmFile);

        configurator.findAllSolutions(false,0);

        // map the solutions to products
        val products = configurator.getSolutions().stream().map(solution -> new Product("0",null, solution, 0, false)).collect(Collectors.toList());

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
        val recommendationList = recommendation.recommend(null);
    }
}
