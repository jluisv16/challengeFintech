package com.ontop.challenge.transaction.infrastructure.rest.controller;

import com.ontop.challenge.transaction.application.service.TransactionService;
import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaction")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Optional<TransactionByUser>> getTransactions(@RequestParam("idUser") Integer idUser){
        log.info("ID:" + idUser);
        Optional<TransactionByUser> transactionByUser = this.transactionService.getTransactionsByUser(idUser);
        String codeHttp = transactionByUser.map(TransactionByUser::getMessageResponse).get().getCode();

        if (codeHttp.equals("200"))
            return new ResponseEntity<>(transactionByUser, HttpStatus.OK);
        else
            return new ResponseEntity<>(transactionByUser, HttpStatus.REQUEST_TIMEOUT);
    }

    @PostMapping
    public ResponseEntity<Optional<ProcessTransaction>> getTransactions(@RequestBody User user){
        log.info("ID:" + user);
        Optional<ProcessTransaction> processTransaction = this.transactionService.processTransaction(user);

        return new ResponseEntity<>(processTransaction, HttpStatus.CREATED);
    }

}
