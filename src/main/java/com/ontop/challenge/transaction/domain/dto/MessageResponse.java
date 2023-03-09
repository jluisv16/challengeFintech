package com.ontop.challenge.transaction.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

    private String code;
    private String message;

}
