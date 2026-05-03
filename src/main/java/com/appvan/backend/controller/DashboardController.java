package com.appvan.backend.controller;

import com.appvan.backend.repository.AlunoRepository;
import com.appvan.backend.repository.MotoristaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final MotoristaRepository motoristaRepository;
    private final AlunoRepository alunoRepository;

    public DashboardController(MotoristaRepository motoristaRepository,
                               AlunoRepository alunoRepository) {
        this.motoristaRepository = motoristaRepository;
        this.alunoRepository = alunoRepository;
    }

    @GetMapping("/admin")
    public Map<String, Object> admin() {

        Map<String, Object> dados = new HashMap<>();

        dados.put("motoristas", motoristaRepository.count());
        dados.put("alunos", alunoRepository.count());

        dados.put("receita", 0);
        dados.put("pendentes", 0);

        return dados;
    }
}