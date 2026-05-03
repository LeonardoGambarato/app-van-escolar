package com.appvan.backend.controller;

import com.appvan.backend.dto.DashboardResponse;
import com.appvan.backend.repository.AlunoRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class ApiDashboardController {

    private final AlunoRepository repository;

    public ApiDashboardController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public DashboardResponse resumo(
            @RequestParam Integer motoristaId) {

        DashboardResponse dto = new DashboardResponse();

        var alunos = repository.findByMotoristaId(motoristaId);

        dto.setAlunos((long) alunos.size());

        dto.setAtivos(
                alunos.stream()
                        .filter(a -> Boolean.TRUE.equals(a.getAtivo()))
                        .count()
        );

        dto.setPendentes(
                alunos.stream()
                        .filter(a ->
                                !"PAGO".equalsIgnoreCase(
                                        String.valueOf(a.getStatusPagamento())
                                ))
                        .count()
        );

        BigDecimal receita = alunos.stream()
                .map(a -> a.getMensalidade() == null
                        ? BigDecimal.ZERO
                        : a.getMensalidade())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setReceita(receita);

        return dto;
    }
}