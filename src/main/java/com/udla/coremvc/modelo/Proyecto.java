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

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Tarea> tareas;

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

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Double calcularEficiencia() {
        if (this.tareas == null || this.tareas.isEmpty()) {
            return 0.0;
        }

        double sumaEficienciasPonderadas = 0.0;
        double sumaPesos = 0.0;

        for (Tarea tarea : this.tareas) {
            if (tarea.getOriginalEstimate() != null && tarea.getOriginalEstimate() > 0) {
                double eficienciaTarea = tarea.calcularEficiencia();
                double peso = tarea.getOriginalEstimate();

                sumaEficienciasPonderadas += eficienciaTarea * peso;
                sumaPesos += peso;
            }
        }

        if (sumaPesos == 0) {
            return 0.0;
        }

        return sumaEficienciasPonderadas / sumaPesos;
    }

    public Double calcularCostoReal() {
        if (this.tareas == null || this.tareas.isEmpty()) {
            return 0.0;
        }

        double costoTotal = 0.0;

        for (Tarea tarea : this.tareas) {
            if (tarea.getRecurso() != null && tarea.getRecurso().getCostoHora() != null) {
                Double tiempoAjustado = tarea.calcularTiempoAjustado();
                Double costoHora = tarea.getRecurso().getCostoHora();
                costoTotal += tiempoAjustado * costoHora;
            }
        }

        return costoTotal;
    }


    public Double calcularDesviacion() {
        if (this.presupuestoTotal == null) {
            return 0.0;
        }

        return calcularCostoReal() - this.presupuestoTotal;
    }

    public Boolean estaEnRiesgo() {
        if (this.tareas == null || this.tareas.isEmpty()) {
            return false;
        }

        // Criterio 1: Eficiencia baja
        Double eficiencia = calcularEficiencia();
        if (eficiencia < 0.8) {
            return true;
        }

        // Criterio 2: Desviación alta
        if (this.presupuestoTotal != null && this.presupuestoTotal > 0) {
            Double desviacion = calcularDesviacion();
            Double porcentajeDesviacion = desviacion / this.presupuestoTotal;
            if (porcentajeDesviacion > 0.1) { // Más del 10% de sobrecosto
                return true;
            }
        }

        // Criterio 3: Muchas reaperturas
        long totalReaperturas = this.tareas.stream()
                .mapToInt(t -> t.getVecesReabierta() != null ? t.getVecesReabierta() : 0)
                .sum();

        if (totalReaperturas > 5) { // Más de 5 reaperturas totales
            return true;
        }

        // Criterio 4: Tareas críticas atoradas
        long tareasProblematicas = this.tareas.stream()
                .filter(t -> "QA_REVIEW".equals(t.getEstado()) || "REOPENED".equals(t.getEstado()))
                .count();

        if (tareasProblematicas > 2) { // Más de 2 tareas en QA o reabierto
            return true;
        }

        return false;
    }
}