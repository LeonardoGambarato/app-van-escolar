package com.appvan.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardResponse {

    private Long alunos;
    private Long ativos;
    private BigDecimal receita;
    private Long pendentes;
}