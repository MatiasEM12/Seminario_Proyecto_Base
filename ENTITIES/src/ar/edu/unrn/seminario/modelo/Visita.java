package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;

public class Visita {
	
	private static int contadorVisita = 0;
	
	private String codigo;
	private LocalDate fechaVisita;
	private String observaciones;
	private String tipo;
	private String codOrdenRetiro;
	private ArrayList<Bien> bienesRecolectados;
	private String estado;
	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrdenRetiro,
			ArrayList<Bien> bienesRecolectados) throws DataNullException, DataLengthException{
		super();
		if (fechaVisita==null) {
			throw new DataNullException("El campo Fecha no puede estar VACIO"); 
		}
		if (observaciones == null) {
			throw new DataNullException("El campo  no puede estar VACIO"); 
		}
		if (observaciones.length()>300) {
			throw new DataLengthException("El campo observaciones no puede exceder los 300 caracteres"); 
		}
		if (tipo == null) {
			throw new DataNullException("El campo codigo donante no puede estar VACIO"); 
		}
		if (codOrdenRetiro == null) {
			throw new DataNullException("Debe haber al menos una ordenRetiro"); 
		}

		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codOrdenRetiro = codOrdenRetiro;
		this.bienesRecolectados = bienesRecolectados;
		crearCodigo();
	}
	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrdenRetiro,
			Bien bien) {
		super();
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codOrdenRetiro = codOrdenRetiro;
		this.bienesRecolectados = new ArrayList<>();
		this.bienesRecolectados.add(bien);
		crearCodigo();
	}
	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrdenRetiro,
			ArrayList<Bien> bienesRecolectados,String codigo) throws DataNullException, DataLengthException{
		super();
		if (fechaVisita==null) {
			throw new DataNullException("El campo Fecha no puede estar VACIO"); 
		}
		if (observaciones == null) {
			throw new DataNullException("El campo  no puede estar VACIO"); 
		}
		if (observaciones.length()>300) {
			throw new DataLengthException("El campo observaciones no puede exceder los 300 caracteres"); 
		}
		if (tipo == null) {
			throw new DataNullException("El campo codigo donante no puede estar VACIO"); 
		}
		if (codOrdenRetiro == null) {
			throw new DataNullException("Debe haber al menos una ordenRetiro"); 
		}

		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codOrdenRetiro = codOrdenRetiro;
		this.bienesRecolectados = bienesRecolectados;
		this.codigo=codigo;
	}
	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrdenRetiro,
			Bien bien,String codigo) {
		super();
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		this.codOrdenRetiro = codOrdenRetiro;
		this.bienesRecolectados = new ArrayList<>();
		this.bienesRecolectados.add(bien);
		this.codigo=codigo;
	}
	public String getCodigo() {
		return codigo;
	}

	public LocalDate getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(LocalDate fechaVisita) {
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
	public String getRetiro() {
		return codOrdenRetiro;
	}
	public void setcodOrdenRetiro(String codOrdenRetiro) {
		this.codOrdenRetiro = codOrdenRetiro;
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

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
