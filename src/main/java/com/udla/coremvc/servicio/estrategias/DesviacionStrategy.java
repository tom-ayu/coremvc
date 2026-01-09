package com.udla.coremvc.servicio.estrategias;

import com.udla.coremvc.modelo.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class DesviacionStrategy implements IMetricaStrategy {

    private final CostoRealStrategy costoRealStrategy;

    public DesviacionStrategy(CostoRealStrategy costoRealStrategy) {
        this.costoRealStrategy = costoRealStrategy;
    }

    @Override
    public Double calcular(Proyecto proyecto) {
        if (proyecto.getPresupuestoTotal() == null) {
            return 0.0;
        }

        Double costoReal = costoRealStrategy.calcular(proyecto);
        return costoReal - proyecto.getPresupuestoTotal();
    }

    @Override
    public String getNombreMetrica() {
        return "Desviación Presupuestaria";
    }
}