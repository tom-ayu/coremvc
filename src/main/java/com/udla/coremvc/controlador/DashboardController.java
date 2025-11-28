package com.udla.coremvc.controlador;

import com.udla.coremvc.dto.ProyectoDTO;
import com.udla.coremvc.dto.RecursoDTO;
import com.udla.coremvc.servicio.ProyectoService;
import com.udla.coremvc.servicio.RecursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ProyectoService proyectoService;
    private final RecursoService recursoService;

    public DashboardController(ProyectoService proyectoService, RecursoService recursoService) {
        this.proyectoService = proyectoService;
        this.recursoService = recursoService;
    }

    @GetMapping
    public String mostrarDashboard(Model model) {
        // 1. Obtener proyectos con sus métricas
        List<ProyectoDTO> proyectos = proyectoService.obtenerProyectosConEficiencia();

        // 2. Obtener recursos con su carga de trabajo
        List<RecursoDTO> recursos = recursoService.obtenerRecursosConCarga();

        // 3. Filtrar proyectos en riesgo
        List<ProyectoDTO> proyectosEnRiesgo = proyectos.stream()
                .filter(p -> p.getEnRiesgo() != null && p.getEnRiesgo())
                .collect(Collectors.toList());

        // 4. Agregar datos al modelo
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("recursos", recursos);
        model.addAttribute("proyectosEnRiesgo", proyectosEnRiesgo);

        return "dashboard/index";
    }
}