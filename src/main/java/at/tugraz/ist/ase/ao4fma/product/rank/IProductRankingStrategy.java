/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.product.rank;

import at.tugraz.ist.ase.ao4fma.configurator.ConfiguratorAdapter;
import at.tugraz.ist.ase.ao4fma.product.Product;
import at.tugraz.ist.ase.hiconfit.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.hiconfit.fm.core.CTConstraint;
import at.tugraz.ist.ase.hiconfit.fm.core.Feature;
import at.tugraz.ist.ase.hiconfit.fm.core.FeatureModel;

import java.util.List;

public interface IProductRankingStrategy {
    List<Product> rank(List<Product> products,
                       FeatureModel<Feature, AbstractRelationship<Feature>, CTConstraint> fm,
                       ConfiguratorAdapter configurator);
}
