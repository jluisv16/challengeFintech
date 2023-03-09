package com.ontop.challenge.transaction.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.challenge.transaction.application.service.TransactionService;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.adapter.db.TransactionRepositoryMySQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    TransactionController transactionController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        transactionController=
                new TransactionController(transactionService);

    }

    @Test
    void shouldReturnNotFoundTransactions() {

        when(transactionService.getTransactionsByUser(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/transaction/{id}", 99))
                .andExpect(status().isNotFound())
                .andDo(print());

    }*/


}