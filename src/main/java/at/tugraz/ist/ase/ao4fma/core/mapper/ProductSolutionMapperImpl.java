/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core.mapper;

import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;

import java.util.LinkedList;
import java.util.List;

public class ProductSolutionMapperImpl implements IProductSolutionMapper {

    @Override
    public Product toProduct(Solution solution, int num_properties) {
        List<Assignment> newAssProperties = new LinkedList<>();
        List<Assignment> newAssFeatures = new LinkedList<>();

        for (int i = 0; i < solution.getAssignments().size(); i++) {
            Assignment assignment = solution.getAssignments().get(i);

            if (i < num_properties) {
                newAssProperties.add(assignment);
            } else {
                newAssFeatures.add(assignment);
            }
        }

        return new Product("0", new Solution(newAssProperties), new Solution(newAssFeatures), 0, false);
    }
}
