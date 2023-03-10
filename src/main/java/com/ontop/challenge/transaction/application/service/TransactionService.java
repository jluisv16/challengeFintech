package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.ProcessTransactionRequest;
import com.ontop.challenge.transaction.domain.dto.ProcessTransactionResponse;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {

    Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort);
    Optional<ProcessTransactionResponse> processTransaction(ProcessTransactionRequest processTransactionRequest);

}
