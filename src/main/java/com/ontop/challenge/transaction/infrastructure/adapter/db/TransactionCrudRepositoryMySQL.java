package com.ontop.challenge.transaction.infrastructure.adapter.db;

import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionCrudRepositoryMySQL extends CrudRepository<TransactionEntity, Integer> {
}
