package com.ontop.challenge.transaction.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "User", schema = "digitalWallet")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    private Integer idUser;

    @Size(max = 25)
    @NotNull
    @Column(name = "firstName", nullable = false, length = 25)
    private String firstName;

    @Size(max = 25)
    @NotNull
    @Column(name = "lastName", nullable = false, length = 25)
    private String lastName;

    @Size(max = 15)
    @NotNull
    @Column(name = "documentNumber", nullable = false, length = 15)
    private String documentNumber;

    @Size(max = 3)
    @NotNull(message = "Por favor ingresar un tipo de Moneda.")
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Size(max = 9)
    @NotNull(message = "Debe ingresar el número de ruta.")
    @Column(name = "routingNumber", nullable = false, length = 9)
    private String routingNumber;

    @Size(max = 20)
    @NotNull
    @Column(name = "accountNumber", nullable = false, length = 20)
    private String accountNumber;

    @Size(max = 50)
    @NotNull
    @Column(name = "nameBank", nullable = false, length = 50)
    private String nameBank;

    @NotNull
    @Column(name = "availableWallet", nullable = false)
    private Double availableWallet;

    @OneToMany(mappedBy="userEntity", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    @JsonIgnoreProperties("userEntity")
    private List<TransactionEntity> transactionEntities;
}