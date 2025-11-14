package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Voluntario;


public class VisitaDTO {
	
	private String codigo;
	private LocalDate fechaVisita;
	private String observaciones;
	private String tipo;
	private String codVoluntario;
	public String codOrdenRetiro;
	public ArrayList <BienDTO> bienesRecolectados = new ArrayList<>();
	
	private boolean esFinal;
	
	public VisitaDTO(String codigo, LocalDate fechaVisita, String codVoluntario,String codOrdenRetiro,ArrayList <BienDTO> bienesRecolectados, String observaciones, String tipo) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrdenRetiro=codOrdenRetiro;
	}
	
	public VisitaDTO(String codigo, LocalDate fechaVisita, String codVoluntario,String codOrdenRetiro,ArrayList <BienDTO> bienesRecolectados, String observaciones, String tipo,boolean esFinal) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrdenRetiro=codOrdenRetiro;
		this.esFinal=esFinal;
	}
	
	
	public VisitaDTO( LocalDate fechaVisita,String codVoluntario  ,String codOrdenRetiro,ArrayList <BienDTO> bienesRecolectados,String observaciones,String tipo, boolean esFinal) {
		super();

		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrdenRetiro=codOrdenRetiro;
		this.esFinal=esFinal;
	}
	
	public boolean isEsFinal() {
		return esFinal;
	}

	public void setEsFinal(boolean esFinal) {
		this.esFinal = esFinal;
	}

	public String getCodigo() {
		return codigo;
	}
	public String getCodOrdenRetiro() {
		return this.codOrdenRetiro;
	}

	public void setBienesRecolectados(ArrayList<BienDTO> bienesRecolectados) {
		this.bienesRecolectados = bienesRecolectados;
	}


	public void setFechaVisita(LocalDate fechaVisita) {
		this.fechaVisita = fechaVisita;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public ArrayList<BienDTO> getBienesRecolectados() {
		return bienesRecolectados;
	}


	public void setCodVoluntario(String codVoluntario) {
		this.codVoluntario = codVoluntario;
	}


	public void setCodOrdenRetiro(String codOrdenRetiro) {
		this.codOrdenRetiro = codOrdenRetiro;
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

