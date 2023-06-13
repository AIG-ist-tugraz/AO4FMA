/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.product;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;

public record Product (String id, Solution properties, Solution fm_values, int rf) {
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", properties=[" + properties + "]" +
                ", fm_values=[" + fm_values + "]" +
                ", rf=" + rf +
                '}';
    }
}
