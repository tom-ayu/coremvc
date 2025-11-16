package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.servicio.TareaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    // Inyección de dependencias
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tareas", tareaService.listarTareas());
        return "tareas/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("tarea", new Tarea());
        // Añadir listas para los dropdowns
        model.addAttribute("proyectos", tareaService.obtenerProyectos());
        model.addAttribute("recursos", tareaService.obtenerRecursos());
        return "tareas/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.buscarPorId(id);
        if (tarea == null) {
            return "redirect:/tareas";
        }
        model.addAttribute("tarea", tarea);
        // Añadir listas para los dropdowns
        model.addAttribute("proyectos", tareaService.obtenerProyectos());
        model.addAttribute("recursos", tareaService.obtenerRecursos());
        return "tareas/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("tarea") Tarea tarea,
                          BindingResult result,
                          Model model) {
        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            // Re-cargar listas para los dropdowns
            model.addAttribute("proyectos", tareaService.obtenerProyectos());
            model.addAttribute("recursos", tareaService.obtenerRecursos());
            return "tareas/form";
        }

        tareaService.guardar(tarea);
        return "redirect:/tareas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return "redirect:/tareas";
    }
}