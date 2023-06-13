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
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
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
import java.util.List;

public class Main {

    private static final List<String> QUERY_RESTRICTIVENESS_FILES = Arrays.asList("q1_1.csv", "q1_2.csv", "q1_3.csv");

    public static void main(String[] args) throws FeatureModelParserException, IOException {
        File fileFM = new File(args[0]);
        File filterFile = new File(args[1]);
        File productsFile = new File(args[2]);
        String queries_folder = args[3];

        // create the factory for anomaly feature models
        FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint>
                fm = Utilities.loadFeatureModel(fileFM);

        // read products
        ProductAssortment products;
        try {
            products = ProductsReader.read(productsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ConfigurationModel");
        findProducts(fm);
        System.out.println("ProductAwareConfigurationModel");
        findProducts(fm, filterFile, products);

        @Cleanup BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));

        // Restrictiveness
        System.out.println("I. RESTRICTIVENESS: ");
        writer.write("I. RESTRICTIVENESS: \n");
        Restrictiveness restrictiveness = new Restrictiveness(fm, filterFile, products);

        for (String queryFile : QUERY_RESTRICTIVENESS_FILES) {
            String query = queries_folder + queryFile;
            Requirement req = Utilities.readRequirement(query);

            System.out.println(restrictiveness.calculate(req));
        }
    }

    public static void findProducts(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel) {
        // convert the feature model into FMKB
        val kb = new FMKB<>(featureModel, true);

//        Configurator configurator = new Configurator(kb, true, new FMSolutionTranslator());
        val configurationModel = new ConfigurationModel(kb, true);
        configurationModel.initialize();
        val configurator = Configurator.builder()
                .kb(kb)
                .configurationModel(configurationModel)
                .translator(new FMSolutionTranslator())
                .build();

//        int counter = 0;
//        for (int i = 0; i < 100; i++) {
//            configurator.findSolutions(false, 1);
//            System.out.println(++counter + " " + configurator.getLastestSolution());
//        }

        configurator.findAllSolutions(false,0);
//        assert configurator.getNumberSolutions() == 2560;

        int counter = 0;
        for (Solution s : configurator.getSolutions()) {
            System.out.println(++counter + " " + s);
        }
    }

    public static void findProducts(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel,
                                    File filterFile, ProductAssortment products) {
        // convert the feature model into FMKB
        val kb = new FMKB<>(featureModel, true);

        // findProducts using ProductAwareConfigurationModel
        val translator = new MZN2ChocoTranslator();
        val productAwareConfigurationModel = ProductAwareConfigurationModel.builder()
                .kb(kb)
                .rootConstraints(true)
                .filterFile(filterFile)
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

        int counter = 0;
        for (Solution s : configurator.getSolutions()) {
            System.out.println(++counter + " " + s);
        }
    }
}
