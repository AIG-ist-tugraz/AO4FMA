/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.recommendation;

import at.tugraz.ist.ase.ao4fma.product.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class AllRecommendationLists implements Iterable<RecommendationList> {

    List<RecommendationList> all = new ArrayList<>();

    public void add(RecommendationList list) {
        all.add(list);
//        if (!all.contains(list)) {
//            all.add(list);
//        }
    }

    public int countOccurrence(Product product) {
        int count = 0;
        for (RecommendationList list : all) {
            if (list.contains(product)) {
                count++;
            }
        }
        return count;
    }

    public int size() {
        return all.size();
    }

    @Override
    public Iterator<RecommendationList> iterator() {
        return all.iterator();
    }

    @Override
    public void forEach(Consumer<? super RecommendationList> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<RecommendationList> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (RecommendationList list : all) {
            sb.append(list.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
