/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import com.google.common.base.Objects;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RecommendationList implements Iterable<Product> {

    List<Product> products;

    public RecommendationList(List<Product> products) {
        this.products = products;
    }

    public int size() {
        return products.size();
    }

    public boolean empty() {
        return products.isEmpty();
    }

    public int rank(Product product) {
        return products.indexOf(product) + 1;
    }

    public boolean contains(Product product) {
        return products.contains(product);
    }

    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    @Override
    public void forEach(Consumer<? super Product> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Product> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecommendationList p)) return false;
        if (products.size() != p.products.size()) return false;
        for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).equals(p.products.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
