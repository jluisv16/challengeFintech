package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.dto.*;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.adapter.db.TransactionRepositoryMySQL;
import com.ontop.challenge.transaction.infrastructure.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class DomainTransactionServiceTest {

    @InjectMocks
    private DomainTransactionService domainTransactionService;

    @Mock
    private TransactionRepository TransactionRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findTransactionByParametersAndSort() {
        String[] sort = {"id", "desc"};
        Pageable pagingSort = Util.SortingColumns(0, 3, sort);
        Pageable pageable = PageRequest.of(0, 3);
        TransactionByUser transactionByUser = new TransactionByUser(new MessageResponse("200", "Se ha ejecutado con éxito"),
                null,null,null,null);
        lenient().when(domainTransactionService.findTransactionByParametersAndSort(any(),any(),any())).thenReturn(Optional.of(transactionByUser)) ;

    }

    @Test
    void processTransaction() {
        ProcessTransactionRequest request = fillRequest();
        ProcessTransactionResponse response = new ProcessTransactionResponse(new MessageResponse("200", "Se ha ejecutado con éxito"), null);
        lenient().when(domainTransactionService.processTransaction(any())).thenReturn(Optional.of(response));

    }

    private ProcessTransactionRequest fillRequest() {

        return new ProcessTransactionRequest(
                120,
                new ProcessTransactionRequest.Source("COMPANY",
                        new ProcessTransactionRequest.SourceInformation("ONTOP INC", "447845"),
                        new ProcessTransactionRequest.Account(
                                "0245253419",
                                "USD",
                                "028444018"
                        )
                ),
                new ProcessTransactionRequest.Destination(
                        "Natalia", "Andrade",
                        new ProcessTransactionRequest.Account(
                                "1885226711",
                                "USD",
                                "211927207"
                        )
                ),2000.00);

    }

}