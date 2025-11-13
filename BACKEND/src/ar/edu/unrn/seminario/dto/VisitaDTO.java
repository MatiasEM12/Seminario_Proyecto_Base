package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Voluntario;


public class VisitaDTO {
	
	private String codigo;
	private LocalDate fechaVisita;
	private String observaciones;
	private String tipo;
	private String codVoluntario;
	public String codOrdenRetiro;
	public String codBienesRecolectados;
	public VisitaDTO(String codigo, LocalDate fechaVisita, String codVoluntario,String codOrdenRetiro,String codBienesRecolectados, String observaciones, String tipo) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.codBienesRecolectados=codBienesRecolectados;
		this.codOrdenRetiro=codOrdenRetiro;
	}
	
	
	public String getCodigo() {
		return codigo;
	}
	public String getCodOrdenRetiro() {
		return this.codOrdenRetiro;
	}
	public String getCodBienesRecolectados() {
		return this.codBienesRecolectados;
	}
	public LocalDate getFechaVisita() {
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
