package com.udla.coremvc.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "recursos")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El rol es obligatorio")
    @Column(name = "rol", nullable = false)
    private String rol;

    @NotNull(message = "Las horas disponibles son obligatorias")
    @Min(value = 1, message = "Las horas disponibles deben ser mayor a 0")
    @Column(name = "horas_disponibles", nullable = false)
    private Integer horasDisponibles;

    @NotNull(message = "El costo por hora es obligatorio")
    @Min(value = 1, message = "El costo por hora debe ser mayor a 0")
    @Column(name = "costo_hora", nullable = false)
    private Double costoHora;

    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL)
    private List<Tarea> tareas;

    public Recurso() {
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

    public Integer getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(Integer horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public Double getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(Double costoHora) {
        this.costoHora = costoHora;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Double calcularCargaTrabajo() {
        if (this.tareas == null || this.tareas.isEmpty()) {
            return 0.0;
        }

        double horasAsignadas = 0.0;

        for (Tarea tarea : this.tareas) {
            // Solo contar tareas que no están completadas
            if (!"DONE".equals(tarea.getEstado()) && tarea.getOriginalEstimate() != null) {
                horasAsignadas += tarea.getOriginalEstimate();
            }
        }

        return horasAsignadas;
    }

    public Double calcularPorcentajeCarga() {
        if (this.horasDisponibles == null || this.horasDisponibles == 0) {
            return 0.0;
        }

        Double cargaTrabajo = calcularCargaTrabajo();
        return (cargaTrabajo / this.horasDisponibles) * 100;
    }

    public Boolean estaSobrecargado() {
        return calcularPorcentajeCarga() > 100;
    }
}