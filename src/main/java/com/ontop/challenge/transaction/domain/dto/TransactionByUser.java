package com.ontop.challenge.transaction.domain.dto;

import com.ontop.challenge.transaction.domain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionByUser {
    private MessageResponse messageResponse;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
    private List<Transaction> transactions;
    
}
