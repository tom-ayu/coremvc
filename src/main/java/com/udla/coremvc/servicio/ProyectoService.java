package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.repositorio.ProyectoRepository;
import com.udla.coremvc.servicio.estrategias.*;
import com.udla.coremvc.servicio.analisis.AnalizadorRiesgos;
import com.udla.coremvc.servicio.validacion.ValidadorProyecto;
import org.springframework.stereotype.Service;
import com.udla.coremvc.dto.ProyectoDTO;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProyectoService implements IProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ValidadorProyecto validadorProyecto;
    private final EficienciaStrategy eficienciaStrategy;
    private final CostoRealStrategy costoRealStrategy;
    private final DesviacionStrategy desviacionStrategy;
    private final AnalizadorRiesgos analizadorRiesgos;

    public ProyectoService(ProyectoRepository proyectoRepository,
                           ValidadorProyecto validadorProyecto,
                           EficienciaStrategy eficienciaStrategy,
                           CostoRealStrategy costoRealStrategy,
                           DesviacionStrategy desviacionStrategy,
                           AnalizadorRiesgos analizadorRiesgos) {
        this.proyectoRepository = proyectoRepository;
        this.validadorProyecto = validadorProyecto;
        this.eficienciaStrategy = eficienciaStrategy;
        this.costoRealStrategy = costoRealStrategy;
        this.desviacionStrategy = desviacionStrategy;
        this.analizadorRiesgos = analizadorRiesgos;
    }

    @Override
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(Proyecto proyecto) {
        validadorProyecto.validar(proyecto);
        proyectoRepository.save(proyecto);
    }

    @Override
    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public List<ProyectoDTO> obtenerProyectosConEficiencia() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        List<ProyectoDTO> proyectosDTO = new ArrayList<>();

        for (Proyecto p : proyectos) {
            ProyectoDTO dto = new ProyectoDTO(
                    p.getId(),
                    p.getNombre(),
                    eficienciaStrategy.calcular(p),
                    costoRealStrategy.calcular(p),
                    desviacionStrategy.calcular(p),
                    p.getPresupuestoTotal(),
                    analizadorRiesgos.analizarRiesgo(p)
            );
            proyectosDTO.add(dto);
        }

        return proyectosDTO;
    }
}