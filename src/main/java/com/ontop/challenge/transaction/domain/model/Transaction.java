package com.ontop.challenge.transaction.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private Integer id;
    private String codePaymentInfo;
    private Double amountSent;
    private Double amountFee;
    private String routingNumber;
    private String currency;
    private LocalDateTime transactionCreated;
    private LocalDateTime transactionUpdated;
    private String accountNumber;
    private String nationalIdentificationNumber;
    private String status;
    private String firstName;
    private String lastName;

}
