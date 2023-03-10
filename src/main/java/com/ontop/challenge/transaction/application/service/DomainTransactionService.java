package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.ProcessTransactionResponse;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class DomainTransactionService implements TransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort){

        return transactionRepository.findTransactionByParametersAndSort(amountSent, date, pagingSort);
    }

    @Override
    public Optional<ProcessTransactionResponse> processTransaction(Transaction transaction) {
        return transactionRepository.processTransaction(transaction);
    }
}
