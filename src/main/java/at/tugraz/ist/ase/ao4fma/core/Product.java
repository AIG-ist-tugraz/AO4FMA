/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import com.google.common.base.Objects;

/**
 * A product
 * @param id the id of the product
 * @param properties technical properties of the product
 * @param fm_values user requirements/feature values based on that the product was identified
 * @param rf the result of ranking function
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equal(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
