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

    // CRUD DE TAREAS

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Tarea buscarPorId(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    public void guardar(Tarea tarea) {
        // Validación: si tarea existe, verificar si ha sido reabierta
        if (tarea.getId() != null) {
            try {
                Tarea tareaExistente = tareaRepository.findById(tarea.getId()).orElse(null);

                if (tareaExistente != null) {
                    // Detección reopen: estaba en DONE y cambia a IN_PROGRESS
                    boolean estabaCompletada = "DONE".equals(tareaExistente.getEstado());
                    boolean vuelveAProgreso = "IN_PROGRESS".equals(tarea.getEstado());

                    if (estabaCompletada && vuelveAProgreso) {
                        tarea.setVecesReabierta(tarea.getVecesReabierta() + 1);
                        tarea.setEstado("REOPENED");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al verificar reapertura: " + e.getMessage());
            }
        }

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