package com.ontop.challenge.transaction.infrastructure.adapter.db;

import com.ontop.challenge.transaction.domain.dto.*;
import com.ontop.challenge.transaction.domain.model.Transaction;
import com.ontop.challenge.transaction.domain.model.User;
import com.ontop.challenge.transaction.domain.port.TransactionRepository;
import com.ontop.challenge.transaction.infrastructure.adapter.ws.MockProvider;
import com.ontop.challenge.transaction.infrastructure.entity.TransactionEntity;
import com.ontop.challenge.transaction.infrastructure.entity.UserEntity;
import com.ontop.challenge.transaction.infrastructure.exceptions.ResourceNotFoundException;
import com.ontop.challenge.transaction.infrastructure.rest.mapper.TransactionMapper;
import com.ontop.challenge.transaction.infrastructure.util.SortByDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Slf4j
public class TransactionRepositoryMySQL implements TransactionRepository {


    private final UserCrudRepositoryMySQL userCrudRepository;

    private final TransactionCrudRepositoryMySQL transactionCrudRepository;

    @Autowired
    private MockProvider mockProvider;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionRepositoryMySQL(UserCrudRepositoryMySQL userCrudRepository,
                                      TransactionCrudRepositoryMySQL transactionCrudRepository) {
        this.userCrudRepository = userCrudRepository;
        this.transactionCrudRepository = transactionCrudRepository;
    }


    @Override
    public Optional<TransactionByUser> getTransactionsByUser(Integer idUser) {

        //Inicializar con exito el mensaje
        TransactionByUser transactionByUser = TransactionByUser.builder()
                .messageResponse(MessageResponse.builder()
                        .code("200")
                        .message("Se ha ejecutado con éxito")
                        .build())
                .build();

        //Actualizamos si encontramos un estado diferente
        UserEntity userEntity = this.userCrudRepository.findById(idUser).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso no encontrado")
        );

        for (TransactionEntity p:userEntity.getTransactionEntities()) {
            PaymentProviderStatusResponse response = mockProvider.getPaymentProviderStatus(p.getCodePaymentInfo());
            if (response != null){
                if (response.getStatus().equals("Failed") && p.getStatus().equals("Processing")){

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
            }else{
                transactionByUser = TransactionByUser.builder()
                        .messageResponse(MessageResponse.builder()
                                .code("408")
                                .message("Se ha excedido el limite de tiempo para la ejecución del servicio externo")
                                .build())
                        .build();
                break;
            }
        }

        userEntity.getTransactionEntities().forEach(p-> {
            p.setUserEntity(null);
        });

        if (transactionByUser.getMessageResponse().getCode().equals("200")){

            transactionByUser.setTransactions(this.transactionMapper.toUser(userEntity).getTransactionEntities());
            transactionByUser.getTransactions().sort(new SortByDateTime().reversed());
        }

        return Optional.of(transactionByUser);
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = this.transactionMapper.toUserEntity(user);
        return this.transactionMapper.toUser(this.userCrudRepository.save(userEntity));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = this.transactionMapper.toTransactionEntity(transaction);
        return this.transactionMapper.toTransaction(this.transactionCrudRepository.save(transactionEntity));
    }

    @Override
    public Optional<ProcessTransaction> processTransaction(User user) {

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

        if(createWalletResponse == null){
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

        if(balanceResponse == null){
            processTransaction = ProcessTransaction.builder()
                    .messageResponse(MessageResponse.builder()
                            .code("408")
                            .message("No se encuentra habilitado el recurso de consulta de Balance")
                            .build())
                    .build();

            return Optional.of(processTransaction);
        }

        //Can do transactions
        if (balanceResponse.getBalance() > 0){
            log.info("Fill Information Patymensts: "+ fillInformationPayment(user));
            CreatePaymentProviderResponse createPaymentProviderResponse;
            createPaymentProviderResponse = mockProvider.createPaymentProvider(fillInformationPayment(user));

            if (createPaymentProviderResponse == null){
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

        }else {
            processTransaction = ProcessTransaction.builder()
                    .messageResponse(MessageResponse.builder()
                            .code("5555")
                            .message("No cuenta con fondos suficientes")
                            .build())
                    .build();
        }

        return Optional.of(processTransaction);
    }

    private CreateWalletRequest fillInformation(User user){
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setAmount(user.getTransactionEntities().get(0).getAmountSent());
        createWalletRequest.setUser_id(user.getIdUser());
        return createWalletRequest;
    }

    private CreatePaymentProviderRequest fillInformationPayment (User user){
        return CreatePaymentProviderRequest.builder()
        .source(CreatePaymentProviderRequest.Source.builder()
                .type("COMPANY NAME")
                .sourceInformation(CreatePaymentProviderRequest.SourceInformation.builder()
                        .name("COMPANY NAME")
                        .build())
                .account(CreatePaymentProviderRequest.Account.builder()
                        .accountNumber(user.getAccountNumber())
                        .currency(user.getCurrency())
                        .routingNumber(user.getRoutingNumber())
                        .build()).build())
        .destination(CreatePaymentProviderRequest.Destination.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .account(CreatePaymentProviderRequest.Account.builder()
                        .accountNumber(user.getTransactionEntities().get(0).getAccountNumber())
                        .currency(user.getTransactionEntities().get(0).getCurrency())
                        .routingNumber(user.getTransactionEntities().get(0).getRoutingNumber())
                        .build())
                .build())
        .amount(user.getTransactionEntities().get(0).getAmountSent()).build();
    }


}
