package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Usuario;
import com.udla.coremvc.servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Thymeleaf login.html
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // credenciales correctas, redirige al CRUD
            return "redirect:/usuarios";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }
}
