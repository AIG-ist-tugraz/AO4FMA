/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.product.mapper;

import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;

public interface IProductSolutionMapper {
    Product toProduct(Solution solution);
}
