/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.ao4fma.product.ProductAssortment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;
import at.tugraz.ist.ase.hiconfit.kb.fm.FMKB;
import lombok.val;

import java.io.File;

public class Restrictiveness {

//    private static final String QUERY_TOTAL_PRODUCTS_STATEMENT = "SELECT COUNT(*) FROM %s;";
//    private static final String QUERY_SUPPORT_STATEMENT = "SELECT COUNT(*) AS SUPPORT FROM %s WHERE %s;";
//    private static final String QUERY_RESTRICTIVENESS_STATEMENT = "SELECT COUNT(*) * 1.000 / (SELECT COUNT(*) FROM %s) AS PERCENT " +
//                                                                "FROM %s WHERE %s;";

//    private final String FILTER_TABLE_NAME;

//    public Restrictiveness(String filterTableName) {
//    }

    ProductAssortment products;
    FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel;
    File filterFile;

    public Restrictiveness(FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> featureModel,
                           File filterFile, ProductAssortment products) {
        this.featureModel = featureModel;
        this.filterFile = filterFile;
        this.products = products;
    }

    public double calculate(Requirement req) {
        System.out.println("\tRequirement: " + req);
//        String where = GeneralJdbcUtilities.getConditionStatement(null, req, null, false);

        // count the total number of products
//        String sql = String.format(QUERY_TOTAL_PRODUCTS_STATEMENT, FILTER_TABLE_NAME);
//        int totalProducts = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Integer.class)).orElse(0);
        int totalProducts = products.size();

        // supports
//        sql = String.format(QUERY_SUPPORT_STATEMENT, FILTER_TABLE_NAME, where);
//        int support = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Integer.class)).orElse(0);

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

        configurator.findAllSolutions(req);
        int support = configurator.getSolutions().size();

        int counter = 0;
        for (Solution s : configurator.getSolutions()) {
            System.out.println(++counter + " " + s);
        }

        // restrictiveness
//        sql = String.format(QUERY_RESTRICTIVENESS_STATEMENT, FILTER_TABLE_NAME, FILTER_TABLE_NAME, where);
//        double restrictiveness = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Double.class)).orElse(0.0);
        double restrictiveness = (double) support / totalProducts;

        System.out.println("\t\tSupport: " + support);
        System.out.println("\t\tTotal products: " + totalProducts);
        System.out.println("\t\tRestrictiveness: " + restrictiveness);

        return restrictiveness;
    }
}
