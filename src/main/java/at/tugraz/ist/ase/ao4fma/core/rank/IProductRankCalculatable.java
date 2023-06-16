/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core.rank;

import at.tugraz.ist.ase.ao4fma.core.ProductAssortment;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;

import java.io.IOException;

public interface IProductRankCalculatable {
    ProductAssortment calculate(ProductAssortment productAssortment) throws FeatureModelParserException, IOException;
}
