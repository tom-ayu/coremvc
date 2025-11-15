package com.udla.coremvc.controlador;

import com.udla.coremvc.servicio.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        // Verificar si el usuario es ADMIN
        boolean isAdmin = false;
        if (authentication != null && authentication.getAuthorities() != null) {
            isAdmin = authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }

    // Ruta raíz que redirige al login
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}