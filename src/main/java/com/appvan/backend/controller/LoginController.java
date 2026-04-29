package com.appvan.backend.controller;

import com.appvan.backend.model.Usuario;
import com.appvan.backend.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    private final UsuarioRepository repository;
    public LoginController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session) {

        Optional<Usuario> usuario = repository.findByEmailAndSenha(email, senha);

        if(usuario.isPresent()){

            session.setAttribute("usuarioId", usuario.get().getId());
            session.setAttribute("nome", usuario.get().getNome());
            session.setAttribute("tipo", usuario.get().getTipoUsuario());

            if(usuario.get().getTipoUsuario().equals("ADMIN")){
                return "redirect:/dashboard-admin.html";
            }

            if(usuario.get().getTipoUsuario().equals("MOTORISTA")){
                return "redirect:/dashboard.html";
            }

        }

        return "redirect:/login.html?erro=true";
    }

        }