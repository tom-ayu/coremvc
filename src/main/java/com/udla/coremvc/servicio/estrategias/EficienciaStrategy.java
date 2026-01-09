package com.udla.coremvc.servicio.estrategias;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.Tarea;
import org.springframework.stereotype.Component;

@Component
public class EficienciaStrategy implements IMetricaStrategy {

    @Override
    public Double calcular(Proyecto proyecto) {
        if (proyecto.getTareas() == null || proyecto.getTareas().isEmpty()) {
            return 0.0;
        }

        double sumaEficienciasPonderadas = 0.0;
        double sumaPesos = 0.0;

        for (Tarea tarea : proyecto.getTareas()) {
            if (tarea.getOriginalEstimate() != null && tarea.getOriginalEstimate() > 0) {
                double eficienciaTarea = tarea.calcularEficiencia();
                double peso = tarea.getOriginalEstimate();

                sumaEficienciasPonderadas += eficienciaTarea * peso;
                sumaPesos += peso;
            }
        }

        return sumaPesos == 0 ? 0.0 : sumaEficienciasPonderadas / sumaPesos;
    }

    @Override
    public String getNombreMetrica() {
        return "Eficiencia";
    }
}