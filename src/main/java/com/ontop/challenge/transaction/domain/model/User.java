package com.ontop.challenge.transaction.domain.model;

import lombok.Data;
import java.util.List;

@Data
public class User {

    private Integer idUser;

    private String firstName;

    private String lastName;

    private String documentNumber;

    private String accountNumber;

    private String nameBank;

    private String currency;
    private String routingNumber;
    private Double availableWallet;

    private List<Transaction> transactionEntities;

}
