package com.appvan.backend.controller;

import com.appvan.backend.model.Motorista;
import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.MotoristaRepository;
import com.appvan.backend.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
@CrossOrigin("*")
public class MotoristaController {

    private final MotoristaRepository repository;
    private final UsuarioRepository usuarioRepository;

    public MotoristaController(MotoristaRepository repository,
                               UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Motorista> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Motorista salvar(@RequestBody Motorista motorista) {

        Motorista salvo = repository.save(motorista);

        Usuario usuario = new Usuario();

        usuario.setNome(motorista.getNome());
        usuario.setEmail(motorista.getEmail());

        usuario.setSenha("123456");

        usuario.setTipoUsuario("MOTORISTA");
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        return salvo;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}