package com.udla.coremvc.repositorio;

import com.udla.coremvc.modelo.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}