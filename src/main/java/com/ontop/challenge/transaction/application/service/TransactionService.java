package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {

    Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort);
    Optional<ProcessTransaction> processTransaction(Transaction transaction);

}
