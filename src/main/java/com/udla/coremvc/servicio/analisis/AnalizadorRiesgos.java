package com.udla.coremvc.servicio.analisis;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.servicio.estrategias.EficienciaStrategy;
import com.udla.coremvc.servicio.estrategias.DesviacionStrategy;
import org.springframework.stereotype.Component;

@Component
public class AnalizadorRiesgos {

    private final EficienciaStrategy eficienciaStrategy;
    private final DesviacionStrategy desviacionStrategy;

    public AnalizadorRiesgos(EficienciaStrategy eficienciaStrategy,
                             DesviacionStrategy desviacionStrategy) {
        this.eficienciaStrategy = eficienciaStrategy;
        this.desviacionStrategy = desviacionStrategy;
    }

    public Boolean analizarRiesgo(Proyecto proyecto) {
        if (proyecto.getTareas() == null || proyecto.getTareas().isEmpty()) {
            return false;
        }

        // Criterio 1: Eficiencia baja
        Double eficiencia = eficienciaStrategy.calcular(proyecto);
        if (eficiencia < 0.8) {
            return true;
        }

        // Criterio 2: Desviación alta
        if (proyecto.getPresupuestoTotal() != null && proyecto.getPresupuestoTotal() > 0) {
            Double desviacion = desviacionStrategy.calcular(proyecto);
            Double porcentajeDesviacion = desviacion / proyecto.getPresupuestoTotal();
            if (porcentajeDesviacion > 0.1) {
                return true;
            }
        }

        // Criterio 3: Muchas reaperturas
        long totalReaperturas = proyecto.getTareas().stream()
                .mapToInt(t -> t.getVecesReabierta() != null ? t.getVecesReabierta() : 0)
                .sum();

        if (totalReaperturas > 5) {
            return true;
        }

        // Criterio 4: Tareas críticas
        long tareasProblematicas = proyecto.getTareas().stream()
                .filter(t -> "QA_REVIEW".equals(t.getEstado()) || "REOPENED".equals(t.getEstado()))
                .count();

        return tareasProblematicas > 2;
    }
}