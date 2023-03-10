package com.ontop.challenge.transaction.infrastructure.rest.controller;

import com.ontop.challenge.transaction.application.service.TransactionService;
import com.ontop.challenge.transaction.domain.dto.ProcessTransaction;
import com.ontop.challenge.transaction.domain.dto.TransactionByUser;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.infrastructure.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    //Creación de un servicio REST - GET que capture por param(sort,
    @GetMapping
    public ResponseEntity<Optional<TransactionByUser>> getTransactions(
            @RequestParam String date,
            @RequestParam String amountSent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        log.info("ID:" + date);
        log.info("ID:" + amountSent);
        Pageable pagingSort = Util.SortingColumns(page, size, sort);

        Optional<TransactionByUser> transactionByUser =
                this.transactionService.findTransactionByParametersAndSort(amountSent, date, pagingSort);

        return new ResponseEntity<>(transactionByUser, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Optional<ProcessTransaction>> getTransactions(@RequestBody Transaction transaction){
        log.info("ID:" + transaction);
        Optional<ProcessTransaction> processTransaction = this.transactionService.processTransaction(transaction);

        return new ResponseEntity<>(processTransaction, HttpStatus.CREATED);
    }

}
