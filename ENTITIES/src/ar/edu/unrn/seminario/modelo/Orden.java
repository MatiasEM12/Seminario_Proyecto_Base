package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

public abstract class Orden {

    private LocalDate fechaEmision;
    private EstadoOrden estado;
    private String tipo;

    protected Orden(LocalDate fechaEmision, EstadoOrden estado,String tipo) throws DataDateException, DataEmptyException, DataNullException, DataObjectException {
    	this.validarDate(fechaEmision);
    	this.validarCampoNull(tipo);
    	this.validarCampoVacio(tipo, "tipo");
    	this.validarObjectNull(estado);
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.tipo=tipo;
    }
    protected Orden(LocalDate fechaEmision, String estado,String tipo) throws DataDateException, DataNullException, DataEmptyException, DataObjectException {
    	
    	this.validarDate(fechaEmision);
    	this.validarCampoNull(tipo);
    	this.validarCampoVacio(tipo, "tipo");
     	this.validarObjectNull(estado);
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

    public void setFechaEmision(LocalDate fechaEmision) throws DataDateException {
    	this.validarDate(fechaEmision);
        this.fechaEmision = fechaEmision;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) throws DataObjectException {
     	this.validarObjectNull(estado);
        this.estado = estado;
    }
    public String getEstadoString() {
        return estado.toString();
    }
    private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	private void validarCampoNull( String nombreCampo) throws DataNullException {
		if (nombreCampo==null) {
			throw new DataNullException("el campo " + nombreCampo + " no puede ser nulo");
		}
	}
    private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	private void validarObjectNull( Object ob) throws DataObjectException {
		if (ob==null) {
			throw new DataObjectException("Contiene instancia nula ");
		}
	}
}
