package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.servicio.TareaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (result.hasErrors()) {
            // Recargar listas para los dropdowns en caso de error
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

    @GetMapping("/{id}/registrar-tiempo")
    public String mostrarFormularioTiempo(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.buscarPorId(id);
        if (tarea == null) {
            return "redirect:/tareas";
        }
        model.addAttribute("tarea", tarea);
        return "tareas/registrar-tiempo";
    }

    @PostMapping("/{id}/registrar-tiempo")
    public String registrarTiempo(@PathVariable Long id,
                                  @RequestParam Integer tiempoReal,
                                  RedirectAttributes redirectAttributes) {
        try {
            tareaService.registrarTiempo(id, tiempoReal);
            redirectAttributes.addFlashAttribute("mensaje", "Tiempo registrado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar tiempo: " + e.getMessage());
        }
        return "redirect:/tareas";
    }
}