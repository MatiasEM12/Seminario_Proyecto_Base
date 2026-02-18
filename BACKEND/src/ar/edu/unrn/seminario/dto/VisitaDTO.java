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
	public String codOrden;
	public ArrayList <BienDTO> bienesRecolectados = new ArrayList<>();
	public String estado;
	
	private boolean esFinal;
	
	public VisitaDTO(String codigo, LocalDate fechaVisita, String codVoluntario,String codOrden,ArrayList <BienDTO> bienesRecolectados, String observaciones, String tipo,String estado) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrden=codOrden;
		this.estado=estado;
	}
	
	public VisitaDTO(String codigo, LocalDate fechaVisita, String codVoluntario,String codOrden,ArrayList <BienDTO> bienesRecolectados, String observaciones, String tipo,boolean esFinal,String estado) {
		super();
		this.codigo = codigo;
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrden=codOrden;
		this.esFinal=esFinal;
		this.estado=estado;
	}
	
	
	public VisitaDTO( LocalDate fechaVisita,String codVoluntario  ,String codOrden,ArrayList <BienDTO> bienesRecolectados,String observaciones,String tipo, boolean esFinal,String estado) {
		super();

		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codVoluntario=codVoluntario;
		this.bienesRecolectados=bienesRecolectados;
		this.codOrden=codOrden;
		this.esFinal=esFinal;
		this.estado=estado;
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
	public String getCodOrden() {
		return this.codOrden;
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


	public void setCodOrden(String codOrden) {
		this.codOrden=codOrden;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}

