package com.ontop.challenge.transaction.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessTransactionResponse {

    private MessageResponse messageResponse;

    private String status;



}
