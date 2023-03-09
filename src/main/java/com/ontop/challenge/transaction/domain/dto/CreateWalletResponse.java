package com.ontop.challenge.transaction.domain.dto;

import lombok.Data;

@Data
public class CreateWalletResponse {

    private Integer wallet_transaction_id;
    private Double amount;
    private Integer user_id;
}
