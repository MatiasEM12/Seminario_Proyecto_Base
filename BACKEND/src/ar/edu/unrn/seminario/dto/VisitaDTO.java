package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Voluntario;


public class VisitaDTO {
	
	private String codigo;
	private LocalDateTime fechaVisita;
	private String observaciones;
	private String tipo;
	private String codVoluntario;
	
	public VisitaDTO(String codigo, LocalDateTime fechaVisita, String codVoluntario,String observaciones, String tipo) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
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

	public String getCodVoluntario() {
		return this.codVoluntario;
	}
	
	
}
