package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;

public class Visita {
	
	private static int contadorVisita = 0;
	
	private String codigo;
	private LocalDate fechaVisita;
	private String observaciones;
	private String tipo;
	private String codOrdenRetiro;
	private ArrayList<Bien> bienesRecolectados;
	private boolean esFinal;
	private String estado;
 	//posibles estados realizada o fallida;
	
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


	
	
	
	public String getCodOrdenRetiro() {
		return codOrdenRetiro;
	}

	public void setCodOrdenRetiro(String codOrdenRetiro) {
		this.codOrdenRetiro = codOrdenRetiro;
	}

	public boolean isEsFinal() {
		return esFinal;
	}

	public void setEsFinal(boolean esFinal) {
		this.esFinal = esFinal;
	}

	public void setEstado(String estado) throws StateChangeException,DataNullException{
	    if (estado == null || estado.isEmpty()) {
	        throw new DataNullException("El campo 'estado' no puede estar vacío");
	    }
	    //si las dos son verdaderas entraria en el if
	    if (!estado.equals("realizada") && !estado.equals("fallida")) {
	        throw new StateChangeException("El estado ingresado es invalido. Debe ser 'realizada' o 'fallida'.");
	    }
	    this.estado=estado;
	}


	private void crearCodigo() {
		  contadorVisita++;
		  this.codigo = "VI" + String.format("%05d", contadorVisita);
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
