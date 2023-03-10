package com.ontop.challenge.transaction.infrastructure.rest.controller;


import com.ontop.challenge.transaction.application.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp(){
    }

    @Test
    void shouldReturnNotFoundTransactions() throws Exception {
        when(transactionService.findTransactionByParametersAndSort(any(),any(),any())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/api/v1/transaction")
                        .param("date","2023-03-08 11:30:40")
                        .param("amountSent","2500.00"))
                .andExpect(status().isOk());
    }


}