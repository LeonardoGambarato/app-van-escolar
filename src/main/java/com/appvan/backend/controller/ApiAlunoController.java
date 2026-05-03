package com.appvan.backend.controller;

import com.appvan.backend.dto.AlunoResponse;
import com.appvan.backend.model.Aluno;
import com.appvan.backend.repository.AlunoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin("*")
public class ApiAlunoController {

    private final AlunoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ApiAlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AlunoResponse> listar(
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

        return repository.findByMotoristaId(motoristaId)
                .stream()
                .map(a -> {

                    AlunoResponse dto =
                            new AlunoResponse();

                    dto.setId(a.getId());
                    dto.setNome(a.getNome());
                    dto.setEscola(a.getEscola());
                    dto.setTurno(a.getTurno());
                    dto.setMensalidade(a.getMensalidade());

                    return dto;

                }).toList();
    }

    @PostMapping
    public Aluno salvar(
            @RequestBody Aluno aluno,
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

        aluno.setMotoristaId(motoristaId);

        return repository.save(aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}