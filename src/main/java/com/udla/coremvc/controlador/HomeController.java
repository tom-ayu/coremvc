package com.udla.coremvc.controlador;

import com.udla.coremvc.dto.ProyectoDTO;
import com.udla.coremvc.dto.RecursoDTO;
import com.udla.coremvc.servicio.ProyectoService;
import com.udla.coremvc.servicio.RecursoService;
import com.udla.coremvc.servicio.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final ProyectoService proyectoService;
    private final RecursoService recursoService;

    public HomeController(UsuarioService usuarioService,
                          ProyectoService proyectoService,
                          RecursoService recursoService) {
        this.usuarioService = usuarioService;
        this.proyectoService = proyectoService;
        this.recursoService = recursoService;
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

        // Si es ADMIN, cargar datos del dashboard
        if (isAdmin) {
            // Proyectos con sus métricas
            List<ProyectoDTO> proyectos = proyectoService.obtenerProyectosConEficiencia();
            // Recursos con su carga de trabajo
            List<RecursoDTO> recursos = recursoService.obtenerRecursosConCarga();
            // Proyectos en riesgo
            List<ProyectoDTO> proyectosEnRiesgo = proyectos.stream()
                    .filter(p -> p.getEnRiesgo() != null && p.getEnRiesgo())
                    .collect(Collectors.toList());
            // Agregar datos al modelo
            model.addAttribute("proyectos", proyectos);
            model.addAttribute("recursos", recursos);
            model.addAttribute("proyectosEnRiesgo", proyectosEnRiesgo);
        }
        return "home";
    }

    // Ruta raíz que redirige al login
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}