/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.common.Utilities;
import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.ao4fma.product.ProductAssortment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

@Slf4j
public class Restrictiveness {

    ProductAssortment products;
    FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel;
    File filterFile;

    @Setter
    BufferedWriter writer = null;

    public Restrictiveness(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel,
                           File filterFile, ProductAssortment products) {
        this.featureModel = featureModel;
        this.filterFile = filterFile;
        this.products = products;
    }

    public double calculate(Requirement req) throws IOException {
        String message = String.format("%sRequirement: %s", LoggerUtils.tab(), req);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }

        // DENOMINATOR - the total number of products
        int totalProducts = products.size();

        // NUMERATOR - supports
        val kb = new FMKB<>(featureModel, true);
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

        configurator.findAllSolutions(req); // identify all products that satisfy the Requirement
        int support = configurator.getSolutions().size();

        Utilities.printSolutions(configurator.getSolutions(), writer);

        // restrictiveness
        double restrictiveness = (double) support / totalProducts;

        LoggerUtils.indent();
        message = String.format("%sSupport: %s", LoggerUtils.tab(), support);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        message = String.format("%sTotal products: %s", LoggerUtils.tab(), totalProducts);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        message = String.format("%sRestrictiveness: %s", LoggerUtils.tab(), restrictiveness);
        log.info(message);
        if (writer != null) {
            writer.write(message); writer.newLine();
        }
        LoggerUtils.outdent();

        return restrictiveness;
    }
}
