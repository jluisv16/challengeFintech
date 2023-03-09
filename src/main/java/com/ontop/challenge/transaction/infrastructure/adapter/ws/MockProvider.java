package com.ontop.challenge.transaction.infrastructure.adapter.ws;

import com.ontop.challenge.transaction.domain.dto.*;

public interface MockProvider {

    CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest);

    CreatePaymentProviderResponse createPaymentProvider(CreatePaymentProviderRequest createPaymentProviderRequest);

    BalanceResponse getBalance (Integer userId);

    PaymentProviderStatusResponse getPaymentProviderStatus(String paymentId);
}
