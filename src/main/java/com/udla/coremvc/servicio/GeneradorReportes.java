package com.udla.coremvc.servicio;

import com.udla.coremvc.modelo.Proyecto;
import com.udla.coremvc.modelo.ReporteProyecto;
import com.udla.coremvc.servicio.estrategias.*;
import com.udla.coremvc.servicio.analisis.AnalizadorRiesgos;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeneradorReportes {

    private final EficienciaStrategy eficienciaStrategy;
    private final CostoRealStrategy costoRealStrategy;
    private final DesviacionStrategy desviacionStrategy;
    private final AnalizadorRiesgos analizadorRiesgos;

    public GeneradorReportes(EficienciaStrategy eficienciaStrategy,
                             CostoRealStrategy costoRealStrategy,
                             DesviacionStrategy desviacionStrategy,
                             AnalizadorRiesgos analizadorRiesgos) {
        this.eficienciaStrategy = eficienciaStrategy;
        this.costoRealStrategy = costoRealStrategy;
        this.desviacionStrategy = desviacionStrategy;
        this.analizadorRiesgos = analizadorRiesgos;
    }

    public ReporteProyecto generarReporteCompleto(Proyecto proyecto) {
        Map<String, Double> metricas = new HashMap<>();
        metricas.put("eficiencia", eficienciaStrategy.calcular(proyecto));
        metricas.put("costoReal", costoRealStrategy.calcular(proyecto));
        metricas.put("desviacion", desviacionStrategy.calcular(proyecto));

        Boolean enRiesgo = analizadorRiesgos.analizarRiesgo(proyecto);
        String nivelRiesgo = determinarNivelRiesgo(metricas, enRiesgo);

        return new ReporteProyecto.Builder(proyecto.getId(), proyecto.getNombre())
                .conMetricas(metricas)
                .conRiesgo(enRiesgo)
                .conNivelRiesgo(nivelRiesgo)
                .conDescripcion(generarDescripcion(proyecto, metricas))
                .build();
    }

    public ReporteProyecto generarReporteBasico(Proyecto proyecto) {
        return new ReporteProyecto.Builder(proyecto.getId(), proyecto.getNombre())
                .agregarMetrica("eficiencia", eficienciaStrategy.calcular(proyecto))
                .conRiesgo(false)
                .build();
    }

    private String determinarNivelRiesgo(Map<String, Double> metricas, Boolean enRiesgo) {
        if (!enRiesgo) return "BAJO";

        Double eficiencia = metricas.get("eficiencia");
        if (eficiencia < 0.5) return "CRÍTICO";
        if (eficiencia < 0.7) return "ALTO";
        return "MEDIO";
    }

    private String generarDescripcion(Proyecto proyecto, Map<String, Double> metricas) {
        return String.format("Proyecto: %s. Eficiencia: %.2f%%. Desviación: $%.2f.",
                proyecto.getNombre(),
                metricas.get("eficiencia") * 100,
                metricas.get("desviacion"));
    }
}