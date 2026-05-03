package com.appvan.backend.controller;

import com.appvan.backend.dto.FinanceiroResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/financeiro")
@CrossOrigin("*")
public class FinanceiroController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public FinanceiroResponse resumo(
            HttpServletRequest request) {

        FinanceiroResponse dto = new FinanceiroResponse();

        String email =
                (String) request.getAttribute("emailLogado");

        Integer motoristaId = (Integer) entityManager
                .createNativeQuery("""
            SELECT id
            FROM motoristas
            WHERE email = ?1
        """)
                .setParameter(1, email)
                .getSingleResult();

        BigDecimal recebido = (BigDecimal) entityManager
                .createNativeQuery("""
                    SELECT COALESCE(SUM(valor),0)
                    FROM mensalidades_alunos
                    WHERE motorista_id = ?1
                    AND status = 'PAGO'
                """)
                .setParameter(1, motoristaId)
                .getSingleResult();

        BigDecimal pendente = (BigDecimal) entityManager
                .createNativeQuery("""
                    SELECT COALESCE(SUM(valor),0)
                    FROM mensalidades_alunos
                    WHERE motorista_id = ?1
                    AND status <> 'PAGO'
                """)
                .setParameter(1, motoristaId)
                .getSingleResult();
        BigDecimal receita = recebido.add(pendente);

        Number pagos = (Number) entityManager
                .createNativeQuery("""
                    SELECT COUNT(*)
                    FROM mensalidades_alunos
                    WHERE motorista_id = ?1
                    AND status = 'PAGO'
                """)
                .setParameter(1, motoristaId)
                .getSingleResult();

        Number devendo = (Number) entityManager
                .createNativeQuery("""
                    SELECT COUNT(*)
                    FROM mensalidades_alunos
                    WHERE motorista_id = ?1
                    AND status <> 'PAGO'
                """)
                .setParameter(1, motoristaId)
                .getSingleResult();

        dto.setReceita(receita);
        dto.setRecebido(recebido);
        dto.setPendente(pendente);
        dto.setPagos(pagos.longValue());
        dto.setDevendo(devendo.longValue());

        if (pagos.longValue() > 0) {
            dto.setTicketMedio(
                    recebido.divide(
                            new java.math.BigDecimal(pagos.longValue()),
                            2,
                            java.math.RoundingMode.HALF_UP
                    )
            );
        } else {
            dto.setTicketMedio(java.math.BigDecimal.ZERO);
        }

        return dto;
    }
}
