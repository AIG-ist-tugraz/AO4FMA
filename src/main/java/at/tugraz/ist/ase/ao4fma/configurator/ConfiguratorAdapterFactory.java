/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.configurator;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.core.ProductsReader;
import at.tugraz.ist.ase.ao4fma.core.mapper.ProductSolutionMapperImpl;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.configurator.ConfigurationModel;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class ConfiguratorAdapterFactory {

    public static ConfiguratorAdapter createConfigurator(File fmFile, File filterFile, File productsFile) throws FeatureModelParserException, IOException {
        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);
        // read products
        val products = ProductsReader.read(productsFile);

        // create knowledge base
        val kb = new FMKB<>(fm, false);
        val translator = new MZN2ChocoTranslator(); // a translator for filter constraints
        // model for the configuration task
        val productAwareConfigurationModel = ProductAwareConfigurationModel.builder()
                .kb(kb)
                .rootConstraints(true)
                .filterFile(filterFile)
                .translator(translator)
                .build();
        productAwareConfigurationModel.initialize();

        // create and return configurator
        return ConfiguratorAdapter.configuratorAdapterBuilder()
                .kb(kb)
                .model(productAwareConfigurationModel)
                .translator(new FMSolutionTranslator())
                .productAssortment(products)
                .productSolutionMapper(new ProductSolutionMapperImpl(productAwareConfigurationModel))
                .build();
    }

    public static Configurator createConfigurator(File fmFile) throws FeatureModelParserException {
        // load the feature model
        val fm = Utilities.loadFeatureModel(fmFile);

        // convert the feature model into FMKB
        val kb = new FMKB<>(fm, false);

        val configurationModel = new ConfigurationModel(kb, true);
        configurationModel.initialize();
        return Configurator.builder()
                .kb(kb)
                .configurationModel(configurationModel)
                .translator(new FMSolutionTranslator())
                .build();
    }
}
