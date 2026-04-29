package com.appvan.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alunos")
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String responsavel;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    private String endereco;

    private String escola;

    private String turno;

    private BigDecimal mensalidade;

    @Column(name = "status_pagamento")
    private String statusPagamento;

    private String observacoes;

    private Boolean ativo;

    private Integer diaVencimento;

    private LocalDate dataUltimoPagamento;

    @Column(name = "usuario_id")
    private Integer usuarioId;

}
