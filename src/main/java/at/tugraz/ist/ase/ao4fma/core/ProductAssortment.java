/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Product Assortment
 */
public class ProductAssortment implements Iterable<Product> {
    List<Product> products = new LinkedList<>();

    /**
     * @return the number of products in the assortment
     */
    public int size() {
        return products.size();
    }

    public void add(Product product) {
        products.add(product);
    }

    public Iterator<Product> iterator() {
        return products.listIterator();
    }

    /**
     * Get the product with the given properties
     * @param properties the properties of the product
     * @return  the product with the given properties
     */
    public Optional<Product> get(Solution properties) {
        return products.stream().filter(p -> p.properties().equals(properties)).findFirst();
    }
}
