package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.dto.ProyectoDTO;
import java.util.List;

public interface IProyectoService {
    List<Proyecto> listarProyectos();
    Proyecto buscarPorId(Long id);
    void guardar(Proyecto proyecto);
    void eliminar(Long id);
    List<ProyectoDTO> obtenerProyectosConEficiencia();
}