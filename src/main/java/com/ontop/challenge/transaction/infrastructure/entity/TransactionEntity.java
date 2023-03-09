package com.ontop.challenge.transaction.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Transaction", schema = "digitalWallet")
public class TransactionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransaction", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("transactionEntities")
    private UserEntity userEntity;

    @Size(max = 40)
    @NotNull
    @Column(name = "codePaymentInfo", nullable = false, length = 40)
    private String codePaymentInfo;

    @NotNull(message = "Por favor ingrese el monto a enviar.")
    @Column(name = "amountSent", nullable = false)
    private Double amountSent;

    @NotNull(message = "El impuesto debe ser ingresado.")
    @Column(name = "amountFee", nullable = false)
    private Double amountFee;

    @Size(max = 9)
    @NotNull(message = "Debe ingresar el número de ruta.")
    @Column(name = "routingNumber", nullable = false, length = 9)
    private String routingNumber;

    @Size(max = 3)
    @NotNull(message = "Por favor ingresar un tipo de Moneda.")
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @NotNull
    @Column(name = "transactionCreated", nullable = false)
    private LocalDateTime transactionCreated;

    @NotNull
    @Column(name = "transactionUpdated", nullable = false)
    private LocalDateTime transactionUpdated;

    @Size(max = 15, message = "No debe contener mas de 15 caracteres")
    @NotNull(message = "Debe ingresar el número de cuenta del destinario")
    @Column(name = "accountNumber", nullable = false, length = 15)
    private String accountNumber;

    @Size(max = 8)
    @NotNull
    @Column(name = "nationalIdentificationNumber", nullable = false, length = 8)
    private String nationalIdentificationNumber;

    @Size(max = 15)
    @NotNull
    @Column(name = "status", nullable = false, length = 15)
    private String status;

}