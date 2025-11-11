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
