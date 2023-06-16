/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import lombok.Getter;
import lombok.Synchronized;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A list of transactions
 */
public class TransactionList implements Iterable<Transaction> {

    @Getter
    List<Transaction> transactions = new LinkedList<>();

    public int size() {
        return transactions.size();
    }

    @Synchronized
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public Iterator<Transaction> iterator() {
        return transactions.listIterator();
    }

    /**
     * Returns the number of times a product was purchased
     * @param p a product
     * @return the number of times the product was purchased
     */
    public long selections(Product p) {
        return transactions.parallelStream().filter(t -> t.product_id().equals(p.id())).count();
    }
}
