package com.appvan.backend.controller;

import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @PostMapping("/criar-conta")
    public Usuario criarConta(@RequestBody Usuario usuario) {

        usuario.setTipoUsuario("MOTORISTA");
        usuario.setAtivo(true);

        return repository.save(usuario);
    }

}