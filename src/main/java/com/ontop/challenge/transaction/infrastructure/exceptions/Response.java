package com.ontop.challenge.transaction.infrastructure.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response {

    private List<Error> errors = new ArrayList<>();

    public void addError (Error error){
        this.errors.add(error);
    }
}

