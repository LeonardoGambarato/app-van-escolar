package com.appvan.backend.controller;

import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.appvan.backend.model.Motorista;
import com.appvan.backend.repository.MotoristaRepository;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final MotoristaRepository motoristaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository,
                             MotoristaRepository motoristaRepository,
                             BCryptPasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.motoristaRepository = motoristaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @PostMapping("/criar-conta")
    public Usuario criarConta(@RequestBody Usuario usuario) {

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole("MOTORISTA");
        usuario.setAtivo(true);

        Usuario salvo = repository.save(usuario);

        Motorista motorista = new Motorista();

        motorista.setNome(usuario.getNome());
        motorista.setEmail(usuario.getEmail());
        motorista.setTelefone(usuario.getTelefone());
        motorista.setCpf(usuario.getCpf());
        motorista.setAtivo(true);

        motoristaRepository.save(motorista);

        return salvo;
    }
}