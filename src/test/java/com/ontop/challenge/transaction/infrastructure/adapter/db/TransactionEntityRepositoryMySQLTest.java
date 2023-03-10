package com.ontop.challenge.transaction.infrastructure.adapter.db;


import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import com.ontop.challenge.transaction.infrastructure.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TransactionEntityRepositoryMySQLTest {

    @Mock
    private TransactionCrudRepositoryMySQL transactionCrudRepository;
    @InjectMocks
    private TransactionRepositoryMySQL transactionRepositoryMySQL;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findTransactionByParametersAndSort() {
        String[] sort = {"id", "desc"};
        Pageable pagingSort = Util.SortingColumns(0, 3, sort);
        Pageable pageable = PageRequest.of(0, 3);
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1);
        Page<TransactionEntity> transactionEntityPage = new PageImpl<>(Arrays.asList(transactionEntity), pageable, 1);
        lenient().when(this.transactionCrudRepository.findByTransactionCreatedAndAmountSent(any(), any(), any())).thenReturn(transactionEntityPage);
        Optional<TransactionByUser> transactionByParametersAndSort = this.transactionRepositoryMySQL.findTransactionByParametersAndSort("2500.00", "2023-03-09 18:30:00", pagingSort);
        assertAll(() -> Assertions.assertNotNull(transactionByParametersAndSort.get()));
    }

}