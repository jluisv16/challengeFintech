package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.User;

import java.util.Optional;

public interface TransactionService {


    Optional<TransactionByUser> getTransactionsByUser(Integer idUser);

    Optional<ProcessTransaction> processTransaction(User transaction);

}
