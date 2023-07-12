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
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkElementIndex;

/**
 * A list of recommended products for a user requirement
 */
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

    public Product get(int index) {
        checkElementIndex(index, products.size(), "Product index out of bound!");
        return products.get(index);
    }

    /**
     * Get the rank of the product in the list
     * @param product the product
     * @return the rank of the product in the list
     */
    public int rank(Product product) {
        return products.indexOf(product) + 1;
    }

    /**
     * Check if the recommendation list contains the product
     * @param product the product
     * @return true if the recommendation list contains the product
     */
    public boolean contains(Product product) {
        return products.parallelStream().anyMatch(p -> Objects.equal(p, product));
    }

    /**
     * Check if the feature is part of the user requirements that led to the recommendation list
     * @param feature the feature
     * @return true if the feature is part of the user requirements that led to the recommendation list
     */
    public boolean contains(String feature) {
        return products.stream().anyMatch(p -> p.fm_values().getAssignments().stream().anyMatch(a -> a.getVariable().equals(feature) && a.getValue().equals("true")));
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
        return IntStream.range(0, products.size()).allMatch(i -> products.get(i).equals(p.products.get(i)));
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
