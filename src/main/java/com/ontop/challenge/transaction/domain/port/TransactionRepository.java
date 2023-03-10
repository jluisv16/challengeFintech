package com.ontop.challenge.transaction.domain.port;

import com.ontop.challenge.transaction.domain.dto.ProcessTransactionRequest;
import com.ontop.challenge.transaction.domain.dto.ProcessTransactionResponse;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionRepository {


    Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort);

    Transaction saveTransaction (Transaction transaction);

    Optional<ProcessTransactionResponse> processTransaction(ProcessTransactionRequest processTransactionRequest);

}
