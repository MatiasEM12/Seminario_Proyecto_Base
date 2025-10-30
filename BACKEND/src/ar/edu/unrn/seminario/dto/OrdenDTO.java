package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public abstract class OrdenDTO {

	
	private LocalDateTime fechaEmision;
	private String estado;
	private String tipo;
	
	
	protected OrdenDTO(LocalDateTime fechaEmision, String estado,String tipo) {
		this.fechaEmision = fechaEmision;
		this.estado = estado;
		this.tipo=tipo;
	}


	public LocalDateTime getFechaEmision() {
		return fechaEmision;
	}



	public String getEstado() {
		return estado;
	}


	public String getTipo() {
		return tipo;
	}

	

	
	
}
