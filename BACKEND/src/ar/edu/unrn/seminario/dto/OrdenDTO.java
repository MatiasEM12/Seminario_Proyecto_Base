package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public abstract class OrdenDTO {

	
	private LocalDate fechaEmision;
	private EstadoOrden estado;
	private String tipo;
	
	
	protected OrdenDTO(LocalDate fechaEmision, EstadoOrden estado,String tipo) {
		this.fechaEmision = fechaEmision;
		this.estado = estado;
		this.tipo=tipo;
	}
	  protected OrdenDTO(LocalDate fechaEmision, String estado,String tipo) {
	    	
		
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

	public LocalDate getFechaEmision() {
		return this.fechaEmision;
	}



	public EstadoOrden getEstado() {
		return this.estado;
	}


	public String getTipo() {
		return this.tipo;
	}

	

	
	
}
