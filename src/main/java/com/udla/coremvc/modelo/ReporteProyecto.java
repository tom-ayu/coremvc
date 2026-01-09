package com.udla.coremvc.modelo;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class ReporteProyecto {

    private final Long proyectoId;
    private final String nombreProyecto;
    private final LocalDateTime fechaGeneracion;
    private final Map<String, Double> metricas;
    private final Boolean enRiesgo;
    private final String nivelRiesgo;
    private final String descripcion;

    private ReporteProyecto(Builder builder) {
        this.proyectoId = builder.proyectoId;
        this.nombreProyecto = builder.nombreProyecto;
        this.fechaGeneracion = builder.fechaGeneracion;
        this.metricas = new HashMap<>(builder.metricas);
        this.enRiesgo = builder.enRiesgo;
        this.nivelRiesgo = builder.nivelRiesgo;
        this.descripcion = builder.descripcion;
    }

    public Long getProyectoId() { return proyectoId; }
    public String getNombreProyecto() { return nombreProyecto; }
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public Map<String, Double> getMetricas() { return new HashMap<>(metricas); }
    public Boolean getEnRiesgo() { return enRiesgo; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public String getDescripcion() { return descripcion; }

    public static class Builder {
        private final Long proyectoId;
        private final String nombreProyecto;
        private final LocalDateTime fechaGeneracion;
        private Map<String, Double> metricas = new HashMap<>();
        private Boolean enRiesgo = false;
        private String nivelRiesgo = "BAJO";
        private String descripcion = "";

        public Builder(Long proyectoId, String nombreProyecto) {
            this.proyectoId = proyectoId;
            this.nombreProyecto = nombreProyecto;
            this.fechaGeneracion = LocalDateTime.now();
        }

        public Builder conMetricas(Map<String, Double> metricas) {
            if (metricas != null) {
                this.metricas = new HashMap<>(metricas);
            }
            return this;
        }

        public Builder agregarMetrica(String nombre, Double valor) {
            this.metricas.put(nombre, valor);
            return this;
        }

        public Builder conRiesgo(Boolean enRiesgo) {
            this.enRiesgo = enRiesgo != null ? enRiesgo : false;
            return this;
        }

        public Builder conNivelRiesgo(String nivelRiesgo) {
            if (nivelRiesgo != null && !nivelRiesgo.trim().isEmpty()) {
                this.nivelRiesgo = nivelRiesgo.toUpperCase();
            }
            return this;
        }

        public Builder conDescripcion(String descripcion) {
            this.descripcion = descripcion != null ? descripcion : "";
            return this;
        }

        public ReporteProyecto build() {
            if (metricas.isEmpty()) {
                throw new IllegalStateException("El reporte debe contener al menos una métrica");
            }
            return new ReporteProyecto(this);
        }
    }

    @Override
    public String toString() {
        return String.format("ReporteProyecto{proyecto='%s', fecha=%s, enRiesgo=%s, nivelRiesgo='%s'}",
                nombreProyecto, fechaGeneracion, enRiesgo, nivelRiesgo);
    }
}