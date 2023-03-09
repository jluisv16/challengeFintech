package com.ontop.challenge.transaction.infrastructure.exceptions;

import lombok.Data;

@Data
public class Error {

    private String code;
    private String message;
}
