package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.servicio.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "proyectos/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        return "proyectos/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.buscarPorId(id);
        if (proyecto == null) {
            return "redirect:/proyectos";
        }
        model.addAttribute("proyecto", proyecto);
        return "proyectos/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("proyecto") Proyecto proyecto,
                          BindingResult result,
                          Model model) {
        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            return "proyectos/form";
        }

        proyectoService.guardar(proyecto);
        return "redirect:/proyectos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return "redirect:/proyectos";
    }
}