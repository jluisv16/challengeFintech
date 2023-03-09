package com.ontop.challenge.transaction.domain.model;

import com.ontop.challenge.transaction.infrastructure.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {

    private Integer id;

    private UserEntity userEntity;

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

}
