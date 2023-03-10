package com.ontop.challenge.transaction.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ProcessTransactionRequest {

    private Integer idUser;
    private Source source;
    private Destination destination;

    private Double amount;

    @Data
    @Builder
    public static class Source{
        private String type;
        private SourceInformation sourceInformation;
        private Account account;
    }
    @Data
    @Builder
    public static class Destination{
        private String firstName;
        private String lastName;
        private Account account;

    }
    @Data
    @Builder
    public static class Account{
        private String accountNumber;
        private String currency;
        private String routingNumber;
    }

    @Data
    @Builder
    public static class SourceInformation{
        private String name;
        private String nationalIdentificationNumber;
    }
}
