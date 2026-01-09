package com.udla.coremvc.servicio.estrategias;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.Tarea;
import org.springframework.stereotype.Component;

@Component
public class CostoRealStrategy implements IMetricaStrategy {

    @Override
    public Double calcular(Proyecto proyecto) {
        if (proyecto.getTareas() == null || proyecto.getTareas().isEmpty()) {
            return 0.0;
        }

        double costoTotal = 0.0;

        for (Tarea tarea : proyecto.getTareas()) {
            if (tarea.getRecurso() != null && tarea.getRecurso().getCostoHora() != null) {
                Double tiempoAjustado = tarea.calcularTiempoAjustado();
                Double costoHora = tarea.getRecurso().getCostoHora();
                costoTotal += tiempoAjustado * costoHora;
            }
        }

        return costoTotal;
    }

    @Override
    public String getNombreMetrica() {
        return "Costo Real";
    }
}