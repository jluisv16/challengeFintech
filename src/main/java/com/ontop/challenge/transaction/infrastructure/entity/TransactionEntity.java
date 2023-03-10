package com.ontop.challenge.transaction.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Transaction", schema = "digitalWallet")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "codePaymentInfo", nullable = false, length = 40)
    private String codePaymentInfo;

    @NotNull
    @Column(name = "amountSent", nullable = false, precision = 8, scale = 2)
    private Double amountSent;

    @Column(name = "amountFee", precision = 8, scale = 2)
    private Double amountFee;

    @Size(max = 9)
    @NotNull
    @Column(name = "routingNumber", nullable = false, length = 9)
    private String routingNumber;

    @Size(max = 3)
    @NotNull
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "transactionCreated")
    private LocalDateTime transactionCreated;

    @Column(name = "transactionUpdated")
    private LocalDateTime transactionUpdated;

    @Size(max = 15)
    @NotNull
    @Column(name = "accountNumber", nullable = false, length = 15)
    private String accountNumber;

    @Size(max = 8)
    @NotNull
    @Column(name = "nationalIdentificationNumber", nullable = false, length = 8)
    private String nationalIdentificationNumber;

    @Size(max = 15)
    @NotNull
    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Size(max = 45)
    @Column(name = "firstName", length = 45)
    private String firstName;

    @Size(max = 45)
    @Column(name = "lastName", length = 45)
    private String lastName;

}