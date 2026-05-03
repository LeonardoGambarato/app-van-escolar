package com.appvan.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FinanceiroResponse {

    private BigDecimal receita;
    private BigDecimal recebido;
    private BigDecimal pendente;
    private Long pagos;
    private Long devendo;
    private BigDecimal ticketMedio;
}
