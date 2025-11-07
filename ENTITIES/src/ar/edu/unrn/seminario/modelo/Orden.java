package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

public abstract class Orden {

    private LocalDate fechaEmision;
    private EstadoOrden estado;
    private String tipo;

    protected Orden(LocalDate fechaEmision, EstadoOrden estado,String tipo) {
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.tipo=tipo;
    }

    public enum EstadoOrden {
        PENDIENTE("Pendiente"),
        EN_PROCESO("En proceso"),
        COMPLETADA("Completada"),
        CANCELADA("Cancelada");

        private final String descripcion;

        EstadoOrden(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
    public String getEstadoString() {
        return estado.toString();
    }
}
