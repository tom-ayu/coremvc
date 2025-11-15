package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Recurso;
import com.udla.coremvc.repositorio.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    // Inyección de dependencias por constructor
    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    // Listar todos los recursos
    public List<Recurso> listarRecursos() {
        return recursoRepository.findAll();
    }

    // Buscar recurso por ID
    public Recurso buscarPorId(Long id) {
        return recursoRepository.findById(id).orElse(null);
    }

    // Guardar recurso (crear o actualizar)
    public void guardar(Recurso recurso) {
        recursoRepository.save(recurso);
    }

    // Eliminar recurso por ID
    public void eliminar(Long id) {
        recursoRepository.deleteById(id);
    }
}