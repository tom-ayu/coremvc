package com.udla.coremvc.dto;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private Double eficiencia;
    private Double costoReal;
    private Double desviacion;
    private Double presupuestoTotal;
    private Boolean enRiesgo;

    public ProyectoDTO() {
    }

    public ProyectoDTO(Long id, String nombre, Double eficiencia, Double costoReal,
                       Double desviacion, Double presupuestoTotal, Boolean enRiesgo) {
        this.id = id;
        this.nombre = nombre;
        this.eficiencia = eficiencia;
        this.costoReal = costoReal;
        this.desviacion = desviacion;
        this.presupuestoTotal = presupuestoTotal;
        this.enRiesgo = enRiesgo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(Double eficiencia) {
        this.eficiencia = eficiencia;
    }

    public Double getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(Double costoReal) {
        this.costoReal = costoReal;
    }

    public Double getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(Double desviacion) {
        this.desviacion = desviacion;
    }

    public Double getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public void setPresupuestoTotal(Double presupuestoTotal) {
        this.presupuestoTotal = presupuestoTotal;
    }

    public Boolean getEnRiesgo() {
        return enRiesgo;
    }

    public void setEnRiesgo(Boolean enRiesgo) {
        this.enRiesgo = enRiesgo;
    }
}