package com.appvan.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "motoristas")
@Data

public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String telefone;
    private String email;
    private String cpf;

    @Column(name = "placa_van")
    private String placaVan;

    private Integer capacidade;
    private Boolean ativo;
}
