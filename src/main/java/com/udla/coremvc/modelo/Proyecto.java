package com.udla.coremvc.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull(message = "El presupuesto es obligatorio")
    @Min(value = 1, message = "El presupuesto debe ser mayor a 0")
    @Column(name = "presupuesto_total", nullable = false)
    private Double presupuestoTotal;

    @NotNull(message = "Las horas estimadas son obligatorias")
    @Min(value = 1, message = "Las horas estimadas deben ser mayor a 0")
    @Column(name = "horas_estimadas", nullable = false)
    private Integer horasEstimadas;

    @NotNull(message = "El porcentaje QA es obligatorio")
    @DecimalMin(value = "0.0", message = "El porcentaje QA debe ser entre 0 y 1")
    @DecimalMax(value = "1.0", message = "El porcentaje QA debe ser entre 0 y 1")
    @Column(name = "porcentaje_qa", nullable = false)
    private Double porcentajeQA;

    // Relación con Tareas: descomentar!! y mira abajo
    // @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    // private List<Tarea> tareas;

    public Proyecto() {
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

    public Double getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public void setPresupuestoTotal(Double presupuestoTotal) {
        this.presupuestoTotal = presupuestoTotal;
    }

    public Integer getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(Integer horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public Double getPorcentajeQA() {
        return porcentajeQA;
    }

    public void setPorcentajeQA(Double porcentajeQA) {
        this.porcentajeQA = porcentajeQA;
    }

    // Genera getter/setter para Tareas aquí
}