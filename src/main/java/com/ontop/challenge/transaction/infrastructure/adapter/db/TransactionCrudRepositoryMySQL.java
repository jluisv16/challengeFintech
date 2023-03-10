package com.ontop.challenge.transaction.infrastructure.adapter.db;

import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface TransactionCrudRepositoryMySQL extends JpaRepository<TransactionEntity, Integer> {

    Page<TransactionEntity> findByTransactionCreatedAndAmountSent(LocalDateTime transactionCreated, Double amountSent, Pageable pageable);
    List<TransactionEntity> findByStatus(String status);
}
