/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.common;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProductCollection  implements Iterable<Product> {
    List<Product> products = new LinkedList<>();

    public void add(Product product) {
        products.add(product);
    }

    public Iterator<Product> iterator() {
        return products.listIterator();
    }

    public boolean contains(Solution solution) {
        Product product = products.stream().filter(p -> p.solution().equals(solution)).findFirst().orElse(null);

        return product != null;
    }
}
