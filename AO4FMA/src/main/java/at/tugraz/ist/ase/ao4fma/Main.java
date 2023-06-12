/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma;

import at.tugraz.ist.ase.ao4fma.common.FMSolutionReader;
import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.configurator.ConfigurationModel;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.fm.parser.FMParserFactory;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParser;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.val;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FeatureModelParserException {
        File fileFM = new File(args[0]);
        File filterFile = new File(args[1]);
//        File productsFile = new File(args[1]);
//        String queries_folder = args[2];

        // create the factory for anomaly feature models
        FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint>
                fm = Utilities.loadFeatureModel(fileFM);

//        JdbcTemplate jdbcTemplate = jdbcConfiguration.getJdbcTemplate("hsqldb").orElseThrow(IllegalArgumentException::new);

//        ConsistencyChecker checker = new ConsistencyChecker();
//        AODBService service = new AODBService(jdbcTemplate, checker);

        // prepare the table name and the column names
//        List<Solution> products = FMSolutionReader.read(productsFile);
//        service.createDB(fm, products);
//        service.printTable(AODBService.PRODUCT_TABLE_NAME);
//        service.printTable(AODBService.UR_TABLE_NAME);
//        service.printTable(AODBService.TRANS_TABLE_NAME);

        // Restrictiveness
//        System.out.println("I. RESTRICTIVENESS: ");
//        Restrictiveness restrictiveness = new Restrictiveness(AODBService.PRODUCT_TABLE_NAME);
//
//        for (String queryFile : QUERY_RESTRICTIVENESS_FILES) {
//            String query = queries_folder + queryFile;
//            Requirement req = Utilities.readRequirement(query);
//
//            restrictiveness.query(jdbcTemplate, req);
//        }

        System.out.println("ConfigurationModel");
        findProducts(fm);
        System.out.println("ProductAwareConfigurationModel");
        findProducts(fm, filterFile);
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

        int counter = 0;
        for (int i = 0; i < 100; i++) {
            configurator.findSolutions(false, 1);
            System.out.println(++counter + " " + configurator.getLastestSolution());
        }
    }

    public static void findProducts(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel, File filterFile) {
        // convert the feature model into FMKB
        val kb = new FMKB<>(featureModel, true);

//        Configurator configurator = new Configurator(kb, true, new FMSolutionTranslator());
//        val configurationModel = new ConfigurationModel(kb, true);
//        configurationModel.initialize();
//        val configurator = Configurator.builder()
//                .kb(kb)
//                .configurationModel(configurationModel)
//                .translator(new FMSolutionTranslator())
//                .build();

//        int counter = 0;
//        for (int i = 0; i < 100; i++) {
//            configurator.findSolutions(false, 1);
//            System.out.println(++counter + " " + configurator.getLastestSolution());
//        }

        // findProducts using ProductAwareConfigurationModel
        val translator = new MZN2ChocoTranslator();
        val productAwareConfigurationModel = new ProductAwareConfigurationModel(kb, true, filterFile, translator);
        productAwareConfigurationModel.initialize();
        val configurator = Configurator.builder()
                .kb(kb)
                .configurationModel(productAwareConfigurationModel)
                .translator(new FMSolutionTranslator())
                .build();

        int counter = 0;
        for (int i = 0; i < 100; i++) {
            configurator.findSolutions(false, 1);
            System.out.println(++counter + " " + configurator.getLastestSolution());
        }
    }
}
