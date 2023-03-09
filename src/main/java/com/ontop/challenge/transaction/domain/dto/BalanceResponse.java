package com.ontop.challenge.transaction.domain.dto;

import lombok.Data;

@Data
public class BalanceResponse {

    private Double balance;
    private Integer user_id;
}
