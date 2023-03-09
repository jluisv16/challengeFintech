package com.ontop.challenge.transaction.infrastructure.util;

import com.ontop.challenge.transaction.domain.model.Transaction;

import java.util.Comparator;

public class SortByDateTime implements Comparator<Transaction> {

    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getTransactionCreated().compareTo(o2.getTransactionCreated());
    }
}
