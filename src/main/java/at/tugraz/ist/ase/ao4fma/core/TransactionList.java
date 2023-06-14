/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TransactionList implements Iterable<Transaction> {

    List<Transaction> transactions = new LinkedList<>();

    public int size() {
        return transactions.size();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public Iterator<Transaction> iterator() {
        return transactions.listIterator();
    }

    public long selections(Product p) {
        return transactions.stream().filter(t -> t.product_id().equals(p.id())).count();
    }
}
