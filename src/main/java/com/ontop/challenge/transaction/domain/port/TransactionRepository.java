package com.ontop.challenge.transaction.domain.port;

import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.model.User;
import java.util.Optional;

public interface TransactionRepository {


    Optional<TransactionByUser> getTransactionsByUser(Integer idUser);

    User saveUser(User user);

    Transaction saveTransaction (Transaction transaction);

    Optional<ProcessTransaction> processTransaction(User transaction);

}
