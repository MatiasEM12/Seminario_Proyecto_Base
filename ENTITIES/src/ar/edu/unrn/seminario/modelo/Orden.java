package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

public abstract class Orden {

    private LocalDateTime fechaEmision;
    private EstadoOrden estado;
    private String tipo;

    protected Orden(LocalDateTime fechaEmision, EstadoOrden estado,String tipo) {
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

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
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
