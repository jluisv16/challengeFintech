package com.ontop.challenge.transaction.application.service;

import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.model.User;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DomainTransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new DomainTransactionService(transactionRepository);

    }

    @Test
    void getTransactionsByUser() {
        transactionService.getTransactionsByUser(1);
        verify(transactionRepository).getTransactionsByUser(1);
    }

    /*@Test
    void processTransaction() {
        transactionService.processTransaction(fillInformation());
        verify(transactionRepository).processTransaction(fillInformation());
    }*/

    private User fillInformation(){
        User user = new User();

        user.setIdUser(1);
        user.setFirstName("Natalia");
        user.setLastName("Andrade");
        user.setDocumentNumber("54781069");
        user.setAccountNumber("2218458745");
        user.setNameBank("Banking ABC1");
        user.setAvailableWallet(7500.00);
        user.setCurrency("USD");

        //user.setRoutingNumber("324555");

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setId(1);
        UserEntity us = new UserEntity();
        us.setIdUser(user.getIdUser());
        transaction.setUserEntity(us);
        transaction.setCodePaymentInfo("9d88fd12-b7b1-49d4-9c5e-c110827c093a");
        transaction.setAmountSent(1000.00);
        transaction.setAmountFee(100.00);
        transaction.setRoutingNumber("1467478");
        transaction.setCurrency("USD");
        transaction.setTransactionCreated(LocalDateTime.of(2023, Month.MARCH, 8, 8, 8, 52));
        transaction.setTransactionUpdated(LocalDateTime.of(2023, Month.MARCH, 8, 20, 47, 15));
        transaction.setAccountNumber("22234242");
        transaction.setNationalIdentificationNumber("543536");
        transaction.setStatus("Processing");
        transactions.add(transaction);

        user.setTransactionEntities(transactions);

        transaction = new Transaction();
        transaction.setId(2);
        transaction.setUserEntity(us);
        transaction.setCodePaymentInfo("9d88fd12-b7b1-49d4-9c5e-c1uj822293a");
        transaction.setAmountSent(1300.00);
        transaction.setAmountFee(130.00);
        transaction.setRoutingNumber("1467478");
        transaction.setCurrency("USD");
        transaction.setTransactionCreated(LocalDateTime.of(2023, Month.MARCH, 8, 8, 14, 46));
        transaction.setTransactionUpdated(LocalDateTime.of(2023, Month.MARCH, 8, 23, 37, 20));
        transaction.setAccountNumber("28634242");
        transaction.setNationalIdentificationNumber("543521");
        transaction.setStatus("Failed");
        transactions.add(transaction);

        user.setTransactionEntities(transactions);

        return user;
    }
}