package com.ontop.challenge.transaction.infrastructure.rest.mapper;

import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.model.User;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import com.ontop.challenge.transaction.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    User toUser(UserEntity transactionEntities);

    UserEntity toUserEntity(User user);

    TransactionEntity toTransactionEntity(Transaction transaction);

    Transaction toTransaction(TransactionEntity transactionEntity);
}
