package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.repositorio.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    // Inyección de dependencias por constructor
    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    // Listar todos los proyectos
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    // Buscar proyecto por ID
    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    // Guardar proyecto (crear o actualizar)
    public void guardar(Proyecto proyecto) {

        if (proyecto.getPresupuestoTotal() != null && proyecto.getPresupuestoTotal() > 1000000) {
            throw new IllegalArgumentException("El presupuesto no puede exceder $1,000,000");
        }
        if (proyecto.getHorasEstimadas() != null && proyecto.getPorcentajeQA() != null) {
            // Validar que las horas de QA no excedan el total
            double horasQA = proyecto.getHorasEstimadas() * proyecto.getPorcentajeQA();
            if (horasQA > proyecto.getHorasEstimadas()) {
                throw new IllegalArgumentException("Las horas de QA no pueden exceder las horas totales");
            }
        }
        proyectoRepository.save(proyecto);
    }

    // Eliminar proyecto por ID
    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }
}