package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;


public class VisitaDTO {
	
	
	private String codigo;
	private LocalDateTime fechaVisita;
	private String observaciones;
	private String tipo;
	private String codOrdenRetiro;
	private String[] codBienesRecolectados;
	private String estado;
	
	public VisitaDTO(String codigo, LocalDateTime fechaVisita, String observaciones, String tipo, String codOrdenRetiro,
			String[] codBienesRecolectados, String estado) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codOrdenRetiro = codOrdenRetiro;
		this.codBienesRecolectados = codBienesRecolectados;
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public LocalDateTime getFechaVisita() {
		return fechaVisita;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public String getTipo() {
		return tipo;
	}

	public String getCodOrdenRetiro() {
		return codOrdenRetiro;
	}

	public String[] getCodBienesRecolectados() {
		return codBienesRecolectados;
	}

	public String getEstado() {
		return estado;
	}
	
	
	
	
	
}
