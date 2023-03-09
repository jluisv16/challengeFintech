package com.ontop.challenge.transaction.domain.dto;

import lombok.Data;

@Data
public class CreateWalletRequest {

    private Double amount;
    private Integer user_id;
}
