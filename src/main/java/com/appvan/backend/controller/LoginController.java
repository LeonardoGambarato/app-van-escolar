package com.appvan.backend.controller;

import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Controller
@RequestMapping
public class LoginController {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UsuarioRepository repository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session) {

        Optional<Usuario> usuarioOpt = repository.findByEmail(email);

        if (usuarioOpt.isPresent()) {

            Usuario usuario = usuarioOpt.get();

            if (passwordEncoder.matches(senha, usuario.getSenha())) {

                session.setAttribute("usuarioId", usuario.getId());
                session.setAttribute("nome", usuario.getNome());
                session.setAttribute("role", usuario.getRole());

                if ("ADMIN".equals(usuario.getRole())) {
                    return "redirect:/dashboard-admin.html";
                }

                if ("MOTORISTA".equals(usuario.getRole())) {
                    return "redirect:/dashboard.html";
                }

                if ("RESPONSAVEL".equals(usuario.getRole())) {
                    return "redirect:/responsavel.html";
                }
            }
        }

        return "redirect:/login.html?erro=true";
    }
}