package com.udla.coremvc.repositorio;

import com.udla.coremvc.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}