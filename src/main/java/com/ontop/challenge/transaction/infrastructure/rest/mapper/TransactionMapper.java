package com.ontop.challenge.transaction.infrastructure.rest.mapper;

import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    TransactionEntity toTransactionEntity(Transaction transaction);

    Transaction toTransaction(TransactionEntity transactionEntity);

    List<Transaction> toListTransaction(List<TransactionEntity> transactionEntities);
}
