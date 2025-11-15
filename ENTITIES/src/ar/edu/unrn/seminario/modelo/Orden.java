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
    protected Orden(LocalDate fechaEmision, String estado,String tipo) {
    	
    
    	
        this.fechaEmision = fechaEmision;
        this.estado =	recuperarEstado(estado);
        this.tipo=tipo;
    }

    private EstadoOrden recuperarEstado(String estado) {
    	
    	if(estado.equalsIgnoreCase(EstadoOrden.CANCELADA.toString())) {
    		return EstadoOrden.CANCELADA;
    	}else if(estado.equalsIgnoreCase(EstadoOrden.PENDIENTE.toString())) {
    		return EstadoOrden.PENDIENTE;
    		
    	}else if(estado.equalsIgnoreCase(EstadoOrden.COMPLETADA.toString())) {
    		return EstadoOrden.COMPLETADA;
    	}else {
    		return EstadoOrden.EN_PROCESO;
    	}
    	
    	
    }
    public enum EstadoOrden {
        PENDIENTE("Pendiente"),
        EN_PROCESO("En proceso"),
        COMPLETADA("Completada"),
        CANCELADA("Cancelada");
    	private final String etiqueta;

        EstadoOrden(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public String getEtiqueta() {
            return etiqueta;
        }
        
        @Override
        public String toString() {
            return etiqueta;
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
    public void setEstadoDesdeString(String string) {
        if (string == null) {
            // si querés un default
            this.estado = EstadoOrden.PENDIENTE;
            return;
        }

        String valor = string.trim();

        // 1) Intentar matchear contra la etiqueta del enum ("Pendiente", "En proceso", etc.)
        for (EstadoOrden e : EstadoOrden.values()) {
            if (e.getEtiqueta().equalsIgnoreCase(valor)) {
                this.estado = e;
                return;
            }
        }

        // 2) Intentar matchear contra el nombre del enum ("PENDIENTE", "EN_PROCESO", etc.)
        String mayus = valor.toUpperCase().replace(' ', '_');
        for (EstadoOrden e : EstadoOrden.values()) {
            if (e.name().equals(mayus)) {
                this.estado = e;
                return;
            }
        }

        // 3) Si nada coincide, podés tirar excepción o dejar un default
        throw new IllegalArgumentException("Estado de orden desconocido: " + string);
    }

}
