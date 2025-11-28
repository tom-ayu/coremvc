package com.udla.coremvc.dto;

public class RecursoDTO {

    private Long id;
    private String nombre;
    private String rol;
    private Double cargaTrabajo;
    private Integer horasDisponibles;
    private Double porcentajeCarga;
    private Boolean sobrecargado;

    public RecursoDTO() {
    }

    public RecursoDTO(Long id, String nombre, String rol, Double cargaTrabajo,
                      Integer horasDisponibles, Double porcentajeCarga, Boolean sobrecargado) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.cargaTrabajo = cargaTrabajo;
        this.horasDisponibles = horasDisponibles;
        this.porcentajeCarga = porcentajeCarga;
        this.sobrecargado = sobrecargado;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Double getCargaTrabajo() {
        return cargaTrabajo;
    }

    public void setCargaTrabajo(Double cargaTrabajo) {
        this.cargaTrabajo = cargaTrabajo;
    }

    public Integer getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(Integer horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public Double getPorcentajeCarga() {
        return porcentajeCarga;
    }

    public void setPorcentajeCarga(Double porcentajeCarga) {
        this.porcentajeCarga = porcentajeCarga;
    }

    public Boolean getSobrecargado() {
        return sobrecargado;
    }

    public void setSobrecargado(Boolean sobrecargado) {
        this.sobrecargado = sobrecargado;
    }
}