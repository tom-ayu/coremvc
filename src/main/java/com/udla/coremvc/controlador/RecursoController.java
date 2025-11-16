package com.udla.coremvc.controlador;

import com.udla.coremvc.modelo.Recurso;
import com.udla.coremvc.servicio.RecursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("recursos", recursoService.listarRecursos());
        return "recursos/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("recurso", new Recurso());
        return "recursos/form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Recurso recurso = recursoService.buscarPorId(id);
        if (recurso == null) {
            return "redirect:/recursos";
        }
        model.addAttribute("recurso", recurso);
        return "recursos/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("recurso") Recurso recurso,
                          BindingResult result,
                          Model model) {
        // Si hay errores de validación, regresar al formulario
        if (result.hasErrors()) {
            return "recursos/form";
        }

        recursoService.guardar(recurso);
        return "redirect:/recursos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        recursoService.eliminar(id);
        return "redirect:/recursos";
    }
}