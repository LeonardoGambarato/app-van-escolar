package com.appvan.backend.controller;

import com.appvan.backend.model.Aluno;
import com.appvan.backend.repository.AlunoRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@CrossOrigin("*")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aluno> listar(HttpSession session) {

        String tipo = (String) session.getAttribute("tipo");

        if(tipo != null && tipo.equals("ADMIN")){
            return repository.findAll();
        }

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        return repository.findByUsuarioId(usuarioId);
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno,
                        HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        aluno.setUsuarioId(usuarioId);

        return repository.save(aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping("/buscar")
    public List<Aluno> buscarPorNome(@RequestParam String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable Integer id,
                           @RequestBody Aluno aluno,
                           HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        aluno.setId(id);
        aluno.setUsuarioId(usuarioId);

        return repository.save(aluno);
    }


}
