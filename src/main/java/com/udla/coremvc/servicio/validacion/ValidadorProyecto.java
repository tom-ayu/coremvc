package com.udla.coremvc.servicio.validacion;

import com.udla.coremvc.modelo.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class ValidadorProyecto {

    private static final double PRESUPUESTO_MAXIMO = 1000000.0;

    public void validar(Proyecto proyecto) {
        validarPresupuesto(proyecto);
        validarHorasQA(proyecto);
    }

    private void validarPresupuesto(Proyecto proyecto) {
        if (proyecto.getPresupuestoTotal() != null &&
                proyecto.getPresupuestoTotal() > PRESUPUESTO_MAXIMO) {
            throw new IllegalArgumentException(
                    String.format("El presupuesto no puede exceder $%,.2f", PRESUPUESTO_MAXIMO)
            );
        }
    }

    private void validarHorasQA(Proyecto proyecto) {
        if (proyecto.getHorasEstimadas() != null && proyecto.getPorcentajeQA() != null) {
            double horasQA = proyecto.getHorasEstimadas() * proyecto.getPorcentajeQA();

            if (horasQA > proyecto.getHorasEstimadas()) {
                throw new IllegalArgumentException(
                        "Las horas de QA no pueden exceder las horas totales del proyecto"
                );
            }
        }
    }
}