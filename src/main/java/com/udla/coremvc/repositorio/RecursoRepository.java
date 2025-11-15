package com.udla.coremvc.repositorio;

import com.udla.coremvc.modelo.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}