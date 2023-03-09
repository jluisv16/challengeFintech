package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.User;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DomainTransactionService implements TransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionByUser> getTransactionsByUser(Integer idUser) {

        return transactionRepository.getTransactionsByUser(idUser);
    }

    @Override
    public Optional<ProcessTransaction> processTransaction(User user) {
        return transactionRepository.processTransaction(user);
    }
}
