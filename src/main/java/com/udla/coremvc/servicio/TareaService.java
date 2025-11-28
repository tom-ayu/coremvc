package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Tarea;
import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.Recurso;
import com.udla.coremvc.repositorio.TareaRepository;
import com.udla.coremvc.repositorio.ProyectoRepository;
import com.udla.coremvc.repositorio.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final RecursoRepository recursoRepository;

    // Inyección de dependencias
    public TareaService(TareaRepository tareaRepository,
                        ProyectoRepository proyectoRepository,
                        RecursoRepository recursoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.recursoRepository = recursoRepository;
    }

    // CRUD BÁSICO DE TAREAS

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Tarea buscarPorId(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    public void guardar(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void eliminar(Long id) {
        tareaRepository.deleteById(id);
    }

    // Obtener todos los proyectos
    public List<Proyecto> obtenerProyectos() {
        return proyectoRepository.findAll();
    }

    // Obtener todos los recursos
    public List<Recurso> obtenerRecursos() {
        return recursoRepository.findAll();
    }

    public void reabrirTarea(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.reabrirTarea();
        tareaRepository.save(tarea);
    }

    public void registrarTiempo(Long tareaId, Integer tiempoReal) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.setTiempoReal(tiempoReal);
        tareaRepository.save(tarea);
    }
}