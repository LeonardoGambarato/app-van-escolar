package com.appvan.backend.controller;

import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.UsuarioRepository;
import com.appvan.backend.service.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class ApiAuthController {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ApiAuthController(UsuarioRepository repository,
                             BCryptPasswordEncoder passwordEncoder,
                             JwtService jwtService) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario dados) {

        Map<String, Object> resp = new HashMap<>();

        Optional<Usuario> usuarioOpt =
                repository.findByEmail(dados.getEmail());

        if(usuarioOpt.isPresent()){

            Usuario usuario = usuarioOpt.get();

            if(passwordEncoder.matches(
                    dados.getSenha(),
                    usuario.getSenha()
            )){

                String token =
                        jwtService.gerarToken(usuario.getEmail());

                resp.put("sucesso", true);
                resp.put("id", usuario.getId());
                resp.put("nome", usuario.getNome());
                resp.put("role", usuario.getRole());
                resp.put("token", token);

                return resp;
            }
        }

        resp.put("sucesso", false);
        resp.put("mensagem", "Login inválido");

        return resp;
    }
}