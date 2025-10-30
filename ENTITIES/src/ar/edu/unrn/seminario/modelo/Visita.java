package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Visita {
	
	private static int contadorVisita = 0;
	
	private String codigo;
	private LocalDateTime fechaVisita;
	private String observaciones;
	private String tipo;
	private OrdenRetiro retiro;
	private ArrayList<Bien> bienesRecolectados;
	private String estado;
	
	public Visita(LocalDateTime fechaVisita, String observaciones, String tipo, OrdenRetiro retiro,
			ArrayList<Bien> bienesRecolectados) {
		super();
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.retiro = retiro;
		this.bienesRecolectados = bienesRecolectados;
		crearCodigo();
	}
	
	public Visita(LocalDateTime fechaVisita, String observaciones, String tipo, OrdenRetiro retiro,
			Bien bien) {
		super();
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.retiro = retiro;
		this.bienesRecolectados = new ArrayList<>();
		this.bienesRecolectados.add(bien);
		crearCodigo();
	}
	
	public String getCodigo() {
		return codigo;
	}

	public LocalDateTime getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(LocalDateTime fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public OrdenRetiro getRetiro() {
		return retiro;
	}
	public void setRetiro(OrdenRetiro retiro) {
		this.retiro = retiro;
	}
	public ArrayList<Bien> getBienesRecolectados() {
		return bienesRecolectados;
	}
	public void setBienesRecolectados(ArrayList<Bien> bienesRecolectados) {
		this.bienesRecolectados = bienesRecolectados;
	}
	
	
	
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	private void crearCodigo() {
		  contadorVisita++;
		  this.codigo = "VI" + String.format("%05d", contadorVisita);
	}
}
