package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Usuario;
import com.udla.coremvc.servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}
