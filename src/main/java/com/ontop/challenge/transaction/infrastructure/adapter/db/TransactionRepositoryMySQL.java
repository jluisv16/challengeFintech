package com.ontop.challenge.transaction.infrastructure.adapter.db;

import com.ontop.challenge.transaction.domain.dto.*;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.adapter.ws.MockProvider;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import com.ontop.challenge.transaction.infrastructure.rest.mapper.TransactionMapper;
import com.ontop.challenge.transaction.infrastructure.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
@Slf4j
public class TransactionRepositoryMySQL implements TransactionRepository {


    private final TransactionCrudRepositoryMySQL transactionCrudRepository;

    @Autowired
    private MockProvider mockProvider;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionRepositoryMySQL(TransactionCrudRepositoryMySQL transactionCrudRepository) {
        this.transactionCrudRepository = transactionCrudRepository;
    }

    @Override
    public Optional<TransactionByUser> findTransactionByParametersAndSort(String amountSent, String date, Pageable pagingSort) {

        //Inicializar con exito el mensaje
        TransactionByUser transactionByUser = TransactionByUser.builder()
                .messageResponse(MessageResponse.builder()
                        .code("200")
                        .message("Se ha ejecutado con éxito")
                        .build())
                .build();

        log.info(transactionByUser.toString());
        log.info("Parameters: {}, {}, {}",
                Util.convertStringToLocalDateTime(date),
                Double.parseDouble(amountSent),
                pagingSort);

        Pageable sortedByPriceDescNameAsc =
                PageRequest.of(0, 5, Sort.by("transactionCreated").descending());

        //findByTransactionCreatedAndAmountSentOrderByTransactionCreatedDesc
        Page<TransactionEntity> transactionEntities = transactionCrudRepository
                //.findByTransactionCreatedAndAmountSent(Util.convertStringToLocalDateTime(date) , Double.parseDouble(amountSent), pagingSort);
                        .findAll(sortedByPriceDescNameAsc);
        log.info(transactionEntities.toString());

        transactionByUser.setCurrentPage(transactionEntities.getNumber());
        transactionByUser.setTotalItems(transactionEntities.getTotalElements());
        transactionByUser.setTotalPages(transactionEntities.getTotalPages());
        transactionByUser.setTransactions(transactionMapper.toListTransaction(transactionEntities.getContent()));

        return Optional.of(transactionByUser);
    }


    public Optional<TransactionByUser> getTransactionsByUser(Integer idUser) {

/*
        for (TransactionEntity p : userEntity.getTransactionEntities()) {
            PaymentProviderStatusResponse response = mockProvider.getPaymentProviderStatus(p.getCodePaymentInfo());
            if (response != null) {
                if (response.getStatus().equals("Failed") && p.getStatus().equals("Processing")) {

                    //Updated status of TransactionEntity to Failed
                    p.setStatus("Failed");
                    p.setTransactionUpdated(LocalDateTime.now());
                    this.saveTransaction(transactionMapper.toTransaction(p));

                    p.setId(null);
                    p.setAmountFee(0.0);
                    p.setStatus("Refunded");
                    p.setTransactionCreated(LocalDateTime.now());
                    p.setTransactionUpdated(LocalDateTime.now());
                    this.saveTransaction(transactionMapper.toTransaction(p));
                }

                if (response.getStatus().equals("Completed") && p.getStatus().equals("Processing")) {
                    p.setStatus("Completed");
                    p.setTransactionUpdated(LocalDateTime.now());
                    this.saveTransaction(transactionMapper.toTransaction(p));

                }
            } else {
                transactionByUser = TransactionByUser.builder()
                        .messageResponse(MessageResponse.builder()
                                .code("408")
                                .message("Se ha excedido el limite de tiempo para la ejecución del servicio externo")
                                .build())
                        .build();
                break;
            }
        }

        userEntity.getTransactionEntities().forEach(p -> {
            p.setUserEntity(null);
        });

        if (transactionByUser.getMessageResponse().getCode().equals("200")) {

            transactionByUser.setTransactions(this.transactionMapper.toUser(userEntity).getTransactionEntities());
            transactionByUser.getTransactions().sort(new SortByDateTime().reversed());
        }
*/
        return null;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = this.transactionMapper.toTransactionEntity(transaction);
        return this.transactionMapper.toTransaction(this.transactionCrudRepository.save(transactionEntity));
    }


    public Optional<ProcessTransaction> processTransaction(Transaction user) {
/*
        log.info("Iniciar processTransaction: {} ", user);
        ProcessTransaction processTransaction = ProcessTransaction.builder()
                .messageResponse(MessageResponse.builder()
                        .code("200")
                        .message("Se ha ejecutado con éxito")
                        .build())
                .build();

        //Se crea la transaccion (Provider)
        CreateWalletResponse createWalletResponse = mockProvider.createWallet(fillInformation(user));
        log.info("CreateWalletResponse: {} ", createWalletResponse);

        if (createWalletResponse == null) {
            processTransaction = ProcessTransaction.builder()
                    .messageResponse(MessageResponse.builder()
                            .code("408")
                            .message("No se encuentra habilitado el recurso de creación de Wallet")
                            .build())
                    .build();

            return Optional.of(processTransaction);
        }

        //Get Balance from Provider
        BalanceResponse balanceResponse = mockProvider.getBalance(createWalletResponse.getUser_id());
        log.info("BalanceResponse: {} ", balanceResponse);

        if (balanceResponse == null) {
            processTransaction = ProcessTransaction.builder()
                    .messageResponse(MessageResponse.builder()
                            .code("408")
                            .message("No se encuentra habilitado el recurso de consulta de Balance")
                            .build())
                    .build();

            return Optional.of(processTransaction);
        }

        //Can do transactions
        if (balanceResponse.getBalance() > 0) {
            log.info("Fill Information Patymensts: " + fillInformationPayment(user));
            CreatePaymentProviderResponse createPaymentProviderResponse;
            createPaymentProviderResponse = mockProvider.createPaymentProvider(fillInformationPayment(user));

            if (createPaymentProviderResponse == null) {
                processTransaction = ProcessTransaction.builder()
                        .messageResponse(MessageResponse.builder()
                                .code("408")
                                .message("No se encuentra habilitado el de pagos del proveedor")
                                .build())
                        .build();
                return Optional.of(processTransaction);

            }

            log.info("Mock createPaymentProvider: {}", createPaymentProviderResponse);
            user.getTransactionEntities().get(0).setCodePaymentInfo(createPaymentProviderResponse.getPaymentInfo().getId());
            user.getTransactionEntities().get(0).setStatus(createPaymentProviderResponse.getRequestInfo().getStatus());
            user.getTransactionEntities().get(0).setTransactionCreated(LocalDateTime.now());
            //Se calcula el 10% de lo enviado.
            Double calcFee = user.getTransactionEntities().get(0).getAmountSent() * 0.1;

            user.getTransactionEntities().get(0).setAmountFee(calcFee);

            this.saveUser(user);

        } else {
            processTransaction = ProcessTransaction.builder()
                    .messageResponse(MessageResponse.builder()
                            .code("5555")
                            .message("No cuenta con fondos suficientes")
                            .build())
                    .build();
        }
*/
        return null;
    }



}
