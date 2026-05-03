package com.appvan.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AlunoResponse {

    private Integer id;
    private String nome;
    private String escola;
    private String turno;
    private BigDecimal mensalidade;
}