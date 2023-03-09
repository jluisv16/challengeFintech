package com.ontop.challenge.transaction.domain.dto;

import com.ontop.challenge.transaction.domain.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionByUser {
    private MessageResponse messageResponse;
    private List<Transaction> transactions;
}
