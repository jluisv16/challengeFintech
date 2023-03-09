package com.ontop.challenge.transaction.infrastructure.adapter.ws;

import com.ontop.challenge.transaction.domain.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MockProviderConsumer implements MockProvider {

    @Value("${mock-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest) {
        String url = baseUrl + "/wallets/transactions";
        log.info("URL: {}", url);
        log.info("DTO: {}", createWalletRequest);
        ResponseEntity<CreateWalletResponse> response;
        try {
            response = restTemplate.postForEntity(url, createWalletRequest, CreateWalletResponse.class);
            if (response.getStatusCode().is2xxSuccessful())
                log.info("Body Response: {}", response.getBody());

        } catch (Exception e) {
            log.error("No se encontró el recurso con URL: " + url);
            log.error("Exception: " + e.getMessage());
            return null;
        }

        return response.getBody();

    }

    @Override
    public CreatePaymentProviderResponse createPaymentProvider(CreatePaymentProviderRequest createPaymentProviderRequest) {
        String url = baseUrl + "/api/v1/payments";
        log.info("URL: {}", url);
        log.info("DTO: {}", createPaymentProviderRequest);
        ResponseEntity<CreatePaymentProviderResponse> response;
        try {
            response = restTemplate.postForEntity(url, createPaymentProviderRequest, CreatePaymentProviderResponse.class);
            if (response.getStatusCode().is2xxSuccessful())
                log.info("Body Response: {}", response.getBody());

        } catch (Exception e) {
            log.error("No se encontró el recurso con URL: " + url);
            log.error("Exception: " + e.getMessage());
            return null;
        }

        return response.getBody();
    }

    @Override
    public BalanceResponse getBalance(Integer userId) {
        String url = baseUrl + "/wallets/balance?user_id=" + userId;
        log.info("URL: {}", url);
        log.info("DTO: {}", userId);

        ResponseEntity<BalanceResponse> response;

        try {
            response = restTemplate.getForEntity(url, BalanceResponse.class);

            if (response.getStatusCode().is2xxSuccessful())
                log.info("Body Response: {}", response.getBody());


        } catch (Exception e) {
            log.error("No se encontró el recurso con URL: " + url);
            log.error("Exception: " + e.getMessage());
            return null;
        }

        return response.getBody();
    }

    @Override
    public PaymentProviderStatusResponse getPaymentProviderStatus(String paymentId) {
        String url = baseUrl + "/api/v1/payments/" + paymentId + "/status";
        log.info("URL: {}", url);
        log.info("DTO: {}", paymentId);
        ResponseEntity<PaymentProviderStatusResponse> response;
        try {
            response = restTemplate.getForEntity(url, PaymentProviderStatusResponse.class);
            if (response.getStatusCode().is2xxSuccessful())
                log.info("Body Response: {}", response.getBody());

        }catch (Exception e){
            log.error("No se encontró el recurso con URL: " + url);
            log.error("Exception: " + e.getMessage());
            return null;
        }

        return response.getBody();
    }
}
