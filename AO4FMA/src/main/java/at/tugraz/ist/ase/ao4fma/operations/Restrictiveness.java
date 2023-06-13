///*
// * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
// *
// * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
// *
// * Contact: http://ase.ist.tugraz.at/ASE/
// */
//
//package at.tugraz.ist.ase.ao4fma.operations;
//
//import java.io.IOException;
//import java.util.Optional;
//
//public class Restrictiveness {
//
////    private static final String QUERY_TOTAL_PRODUCTS_STATEMENT = "SELECT COUNT(*) FROM %s;";
////    private static final String QUERY_SUPPORT_STATEMENT = "SELECT COUNT(*) AS SUPPORT FROM %s WHERE %s;";
////    private static final String QUERY_RESTRICTIVENESS_STATEMENT = "SELECT COUNT(*) * 1.000 / (SELECT COUNT(*) FROM %s) AS PERCENT " +
////                                                                "FROM %s WHERE %s;";
//
//    private final String FILTER_TABLE_NAME;
//
//    public Restrictiveness(String filterTableName) {
//        this.FILTER_TABLE_NAME = filterTableName;
//    }
//
//    public void query(JdbcTemplate jdbcTemplate, Requirement req) throws IOException {
//        System.out.println("\tRequirement: " + req);
//        String where = GeneralJdbcUtilities.getConditionStatement(null, req, null, false);
//
//        // count the total number of products
//        String sql = String.format(QUERY_TOTAL_PRODUCTS_STATEMENT, FILTER_TABLE_NAME);
//        int totalProducts = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Integer.class)).orElse(0);
//
//        // supports
//        sql = String.format(QUERY_SUPPORT_STATEMENT, FILTER_TABLE_NAME, where);
//        int support = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Integer.class)).orElse(0);
//
//        // restrictiveness
//        sql = String.format(QUERY_RESTRICTIVENESS_STATEMENT, FILTER_TABLE_NAME, FILTER_TABLE_NAME, where);
//        double restrictiveness = Optional.ofNullable(GeneralJdbcUtilities.calculate(jdbcTemplate, sql, Double.class)).orElse(0.0);
//
//        System.out.println("\t\tQuery statement: " + sql);
//
//        System.out.println("\t\tSupport: " + support);
//        System.out.println("\t\tTotal products: " + totalProducts);
//        System.out.println("\t\tRestrictiveness: " + restrictiveness);
//    }
//}
