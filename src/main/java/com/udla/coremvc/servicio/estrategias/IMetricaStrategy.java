package com.udla.coremvc.servicio.estrategias;

import com.udla.coremvc.modelo.Proyecto;

public interface IMetricaStrategy {
    Double calcular(Proyecto proyecto);
    String getNombreMetrica();
}