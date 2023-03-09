package com.ontop.challenge.transaction.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreatePaymentProviderResponse {

    private RequestInfo requestInfo;
    private PaymentInfo paymentInfo;

    @Data
    public static class RequestInfo{
        private String status;
    }
    @Data
    public static class PaymentInfo{
        private Double amount;
        private String id;
    }
}
