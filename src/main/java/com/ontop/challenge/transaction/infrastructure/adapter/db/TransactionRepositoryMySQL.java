package com.ontop.challenge.transaction.infrastructure.adapter.db;

import com.ontop.challenge.transaction.domain.dto.*;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.adapter.ws.MockProvider;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import com.ontop.challenge.transaction.infrastructure.rest.mapper.TransactionMapper;
import com.ontop.challenge.transaction.infrastructure.util.Constants;
import com.ontop.challenge.transaction.infrastructure.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TransactionRepositoryMySQL implements TransactionRepository {

    private final TransactionCrudRepositoryMySQL transactionCrudRepository;

    @Autowired
    private MockProvider mockProvider;

    @Override
    public Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort) {

        //Inicializar con exito el mensaje
        TransactionByUser transactionByUser =
                TransactionByUser.builder()
                        .messageResponse(MessageResponse.builder()
                                .code(Constants.HTTP_CODE_OK)
                                .message(Constants.HTTP_MESSAGE_SUCCESSFUL)
                                .build()).build();

        //Cada vez que se invoque el servicio antes llamará todas las transacciones que se encuentran en estado "Processing"
        List<Transaction> transactions =
                TransactionMapper.MAPPER.toListTransaction(
                        transactionCrudRepository.findByStatus(Constants.VALUE_STATUS_PROCESSING_DEFAULT));

        if(transactions.size() > 0){
            //Invocar Servicio Proveedor Status
            for (Transaction p:transactions) {
                PaymentProviderStatusResponse response = mockProvider.getPaymentProviderStatus(p.getCodePaymentInfo());
                if (response != null) {
                    if (response.getStatus().equals("Failed")) {

                        //Updated status of TransactionEntity to Failed
                        p.setStatus("Failed");
                        p.setTransactionUpdated(LocalDateTime.now());
                        this.saveTransaction(p);

                        p.setId(null);
                        p.setAmountFee(0.0);
                        p.setStatus("Refunded");
                        p.setTransactionCreated(LocalDateTime.now());
                        p.setTransactionUpdated(LocalDateTime.now());
                        this.saveTransaction(p);
                    }

                    if (response.getStatus().equals("Completed")) {
                        p.setStatus("Completed");
                        p.setTransactionUpdated(LocalDateTime.now());
                        this.saveTransaction(p);

                    }
                } else {
                    return Optional.of(TransactionByUser.builder()
                            .messageResponse(MessageResponse.builder()
                                    .code(Constants.HTTP_CODE_TIMEOUT)
                                    .message(Constants.HTTP_MESSAGE_TIMEOUT)
                                    .build())
                            .build());
                }
            }


        }

        log.info(transactionByUser.toString());
        log.info("Parameters: {}, {}, {}", Util.convertStringToLocalDateTime(date), Double.parseDouble(amountSent), pagingSort);

        Page<TransactionEntity> transactionEntities = transactionCrudRepository
                .findByTransactionCreatedAndAmountSent(Util.convertStringToLocalDateTime(date), Double.parseDouble(amountSent), pagingSort);

        log.info(transactionEntities.toString());

        transactionByUser.setCurrentPage(transactionEntities.getNumber());
        transactionByUser.setTotalItems(transactionEntities.getTotalElements());
        transactionByUser.setTotalPages(transactionEntities.getTotalPages());
        transactionByUser.setTransactions(TransactionMapper.MAPPER.toListTransaction(transactionEntities.getContent()));

        return Optional.of(transactionByUser);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionMapper.MAPPER.toTransactionEntity(transaction);
        return TransactionMapper.MAPPER.toTransaction(this.transactionCrudRepository.save(transactionEntity));
    }

    @Override
    public Optional<ProcessTransactionResponse> processTransaction(ProcessTransactionRequest processTransactionRequest) {

        log.info("Iniciar processTransaction: {} ", processTransactionRequest);
        Transaction transaction = new Transaction();

        CreateWalletResponse createWalletResponse = mockProvider.createWallet(fillInformation(processTransactionRequest));

        if (createWalletResponse == null) {

            return Optional.of(ProcessTransactionResponse.builder()
                    .messageResponse(MessageResponse.builder()
                            .code(Constants.HTTP_CODE_TIMEOUT)
                            .message(Constants.HTTP_MESSAGE_TIMEOUT)
                            .build()).build());
        }


        //Get Balance from Provider
        BalanceResponse balanceResponse = mockProvider.getBalance(createWalletResponse.getUser_id());
        log.info("BalanceResponse: {} ", balanceResponse);

        if (balanceResponse == null) {
            return Optional.of(ProcessTransactionResponse.builder()
                    .messageResponse(MessageResponse.builder()
                            .code(Constants.HTTP_CODE_TIMEOUT)
                            .message(Constants.HTTP_MESSAGE_TIMEOUT)
                            .build()).build());
        }

        //Can do transactions
        if (balanceResponse.getBalance() > 0) {
            log.info("Fill Information Payments: " + fillInformationPayment(processTransactionRequest));
            CreatePaymentProviderResponse responsePayment = mockProvider.createPaymentProvider(fillInformationPayment(processTransactionRequest));

            if (responsePayment == null) {

                return Optional.of(ProcessTransactionResponse.builder()
                        .messageResponse(MessageResponse.builder()
                                .code(Constants.HTTP_CODE_TIMEOUT)
                                .message(Constants.HTTP_MESSAGE_TIMEOUT)
                                .build()).build());
            }

            log.info("Mock createPaymentProvider: {}", responsePayment);
            //Mapeo de Campos

            transaction.setCodePaymentInfo(responsePayment.getPaymentInfo().getId());
            transaction.setCurrency(processTransactionRequest.getDestination().getAccount().getCurrency());
            transaction.setTransactionCreated(LocalDateTime.now());
            transaction.setStatus(responsePayment.getRequestInfo().getStatus());
            transaction.setAmountSent(responsePayment.getPaymentInfo().getAmount());
            transaction.setAccountNumber(processTransactionRequest.getDestination().getAccount().getAccountNumber());
            transaction.setFirstName(processTransactionRequest.getDestination().getFirstName());
            transaction.setLastName(processTransactionRequest.getDestination().getLastName());
            transaction.setRoutingNumber(processTransactionRequest.getDestination().getAccount().getRoutingNumber());
            transaction.setNationalIdentificationNumber(processTransactionRequest.getSource().getSourceInformation().getNationalIdentificationNumber());
            //Se calcula el 10% de lo enviado.
            Double calcFee = transaction.getAmountSent() * 0.1;
            transaction.setAmountFee(calcFee);

            this.saveTransaction(transaction);
        }

        return Optional.of(ProcessTransactionResponse.builder()
                .messageResponse(MessageResponse.builder()
                        .code(Constants.HTTP_CODE_OK)
                        .message(Constants.HTTP_MESSAGE_SUCCESSFUL)
                        .build())
                .status(transaction.getStatus())
                .build());
    }

    private CreatePaymentProviderRequest fillInformationPayment(ProcessTransactionRequest request) {

        return new CreatePaymentProviderRequest(
                new CreatePaymentProviderRequest.Source(
                        request.getSource().getType(),
                        new CreatePaymentProviderRequest.SourceInformation(request.getSource().getSourceInformation().getName()),
                        new CreatePaymentProviderRequest.Account(
                                request.getSource().getAccount().getAccountNumber(),
                                request.getSource().getAccount().getCurrency(),
                                request.getSource().getAccount().getRoutingNumber()
                        )
                ),
                new CreatePaymentProviderRequest.Destination(
                        request.getDestination().getFirstName() + " " + request.getDestination()
                                .getLastName(),
                        new CreatePaymentProviderRequest.Account(
                                request.getDestination().getAccount().getAccountNumber(),
                                request.getDestination().getAccount().getCurrency(),
                                request.getDestination().getAccount().getRoutingNumber()
                        )
                ),
                request.getAmount()
        );

    }

    private CreateWalletRequest fillInformation(ProcessTransactionRequest processTransactionRequest) {
        return new CreateWalletRequest(processTransactionRequest.getAmount(),processTransactionRequest.getIdUser());
    }


}
