package com.appvan.backend.controller;

import com.appvan.backend.dto.RotaResponse;
import com.appvan.backend.repository.AlunoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/rotas")
@CrossOrigin("*")
public class ApiRotaController {

    private final AlunoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ApiRotaController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RotaResponse> listar(
            HttpServletRequest request) {

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

        AtomicInteger contador =
                new AtomicInteger(1);

        return repository.findByMotoristaId(motoristaId)
                .stream()
                .map(a -> {

                    RotaResponse dto =
                            new RotaResponse();

                    dto.setOrdem(
                            contador.getAndIncrement()
                    );

                    dto.setAluno(a.getNome());
                    dto.setEndereco(a.getEndereco());
                    dto.setTurno(a.getTurno());

                    return dto;

                }).toList();
    }
}