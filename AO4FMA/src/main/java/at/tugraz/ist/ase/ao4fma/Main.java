/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma;

import at.tugraz.ist.ase.ao4fma.ao.Restrictiveness;
import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.ao4fma.product.ProductAssortment;
import at.tugraz.ist.ase.ao4fma.product.ProductsReader;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.configurator.ConfigurationModel;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.Cleanup;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {

    private static final List<String> QUERY_RESTRICTIVENESS_FILES = Arrays.asList("q1_1.csv", "q1_2.csv", "q1_3.csv");

    public static void main(String[] args) throws FeatureModelParserException, IOException {
        val fileFM = new File(args[0]);
        val filterFile = new File(args[1]);
        val productsFile = new File(args[2]);
        val queries_folder = args[3];

        val fm = Utilities.loadFeatureModel(fileFM);

        // read products
        ProductAssortment products;
        try {
            products = ProductsReader.read(productsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        @Cleanup val writer = new BufferedWriter(new FileWriter("results.txt"));
        LoggerUtils.setUseThreadInfo(false);

        String message = String.format("%sAll user requirements:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();
        findProducts(fm, writer);

        message = String.format("%n%sProduct Assortment:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();
        findProducts(fm, filterFile, products, writer);

        // Restrictiveness
        restrictiveness(fm, filterFile, products, writer, queries_folder);

        // Restrictiveness for all features
        restrictivenessAllLeafFeatures(fm, filterFile, products, writer);
    }

    private static void restrictiveness(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm,
                                        File filterFile,
                                        ProductAssortment products,
                                        BufferedWriter writer,
                                        String queries_folder) throws IOException {
        String message = String.format("%n%sI. RESTRICTIVENESS:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();

        // create the operation
        val restrictiveness = new Restrictiveness(fm, filterFile, products);
        restrictiveness.setWriter(writer);

        LoggerUtils.indent();
        for (String queryFile : QUERY_RESTRICTIVENESS_FILES) {
            String query = queries_folder + queryFile;
            val req = Utilities.readRequirement(query);

            double restrict_value = restrictiveness.calculate(req);
        }
        LoggerUtils.outdent();
    }

    private static void restrictivenessAllLeafFeatures(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm,
                                                File filterFile,
                                                ProductAssortment products,
                                                BufferedWriter writer) throws IOException {
        String message = String.format("%n%sI.1. RESTRICTIVENESS - ALL LEAF FEATURES:", LoggerUtils.tab());
        System.out.println(message);
        writer.write(message); writer.newLine();

        // create the operation
        val restrictiveness = new Restrictiveness(fm, filterFile, products);
        restrictiveness.setWriter(writer);

        LoggerUtils.indent();
        LinkedHashMap<String, Double> results = restrictiveness.calculate4AllLeafFeatures();
        LoggerUtils.outdent();
    }

    public static void findProducts(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel,
                                    BufferedWriter writer) throws IOException {
        // convert the feature model into FMKB
        val kb = new FMKB<>(featureModel, true);

        val configurationModel = new ConfigurationModel(kb, true);
        configurationModel.initialize();
        val configurator = Configurator.builder()
                .kb(kb)
                .configurationModel(configurationModel)
                .translator(new FMSolutionTranslator())
                .build();

        configurator.findAllSolutions(false,0);

        Utilities.printSolutions(configurator.getSolutions(), writer);
    }

    public static void findProducts(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel,
                                    File filterFile, ProductAssortment products,
                                    BufferedWriter writer) throws IOException {
        // convert the feature model into FMKB
        val kb = new FMKB<>(featureModel, true);

        // findProducts using ProductAwareConfigurationModel
        val translator = new MZN2ChocoTranslator();
        val productAwareConfigurationModel = ProductAwareConfigurationModel.builder()
                .kb(kb)
                .rootConstraints(true)
                .filterFile(filterFile) // add the filter constraints
                .translator(translator)
                .build();
        productAwareConfigurationModel.initialize();
        val configurator = ConfiguratorAdapter.configuratorAdapterBuilder()
                .kb(kb)
                .model(productAwareConfigurationModel)
                .translator(new FMSolutionTranslator())
                .products(products)
                .build();

        configurator.findAllSolutions();

        Utilities.printSolutions(configurator.getSolutions(), writer);
    }
}
