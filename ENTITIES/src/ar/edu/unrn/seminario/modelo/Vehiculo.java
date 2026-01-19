package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.*;

public class Vehiculo {
	private String matricula;
	private String tipo;
	private String modelo;
	private double capacidadMaxCarga;
	private boolean disponibilidad;
	private String cronogramaMantenimiento;
	
	public Vehiculo(String matricula, String tipo, String modelo, double capacidadMaxCarga, boolean disponibilidad, String cronogramaMantenimiento) throws DataEmptyException{
		
		if (matricula == null || matricula.isEmpty()) {
			throw new DataEmptyException("el campo matricula no puede ser null");
		}
		if (tipo == null || tipo.isEmpty()) {
			throw new DataEmptyException("el campo tipo no puede ser null");		
		}
		if(capacidadMaxCarga<=0) {
			throw new DataEmptyException("la capacidad máxima de carga debe ser mayor a 0");		
		}

	    this.matricula = matricula;
	    this.tipo = tipo;
	    this.modelo = modelo;
	    this.capacidadMaxCarga = capacidadMaxCarga;
	    this.disponibilidad = disponibilidad;
	    this.cronogramaMantenimiento = cronogramaMantenimiento;
		
		
	}
	
	public String getMatricula() {
	    return matricula;
	}

	public void setMatricula(String matricula) throws DataEmptyException {
	    if (matricula == null || matricula.isEmpty()) {
	        throw new DataEmptyException("el campo matricula no puede ser null o vacío");
	    }
	    this.matricula = matricula;
	}
	
	public String getTipo() {
	    return tipo;
	}

	public void setTipo(String tipo) throws DataEmptyException {
	    if (tipo == null || tipo.isEmpty()) {
	        throw new DataEmptyException("el campo tipo no puede ser null o vacío");
	    }
	    this.tipo = tipo;
	}
	
	
	public String getModelo() {
	    return modelo;
	}
	
	public void setModelo(String modelo) throws DataEmptyException {
	    if (modelo == null || modelo.isEmpty()) {
	        throw new DataEmptyException("el campo modelo no puede ser null o vacío");
	    }
	    this.modelo = modelo;
	}
	
	public double getCapacidadMaxCarga() {
	    return capacidadMaxCarga;
	}

	public void setCapacidadMaxCarga(double capacidadMaxCarga) throws DataEmptyException {
	    if (capacidadMaxCarga<=0) {
	        throw new DataEmptyException("la capacidad máxima de carga debe ser mayor a 0");
	    }
	    this.capacidadMaxCarga = capacidadMaxCarga;
	}
	
	public boolean isDisponibilidad() {
	    return disponibilidad;
	}
	
	public void Disponible() throws StateChangeException {
		if (this.disponibilidad==true) {
			throw new StateChangeException("Ya se encuentra disponible");
		}
		this.disponibilidad=true;

	}

	public void NoDisponible() throws StateChangeException{
		if (this.disponibilidad==false) {
			throw new StateChangeException("Ya se encuentra no disponible");
		}
		this.disponibilidad=false;
	}
	public void CambiarEstadoADisponibleONoDisponible(){
		if (this.disponibilidad!=false) {
			this.disponibilidad=false;
		}
		else {
			this.disponibilidad=true;
		}
	}
	
	public String getCronogramaMantenimiento() {
	    return cronogramaMantenimiento;
	}

	public void setCronogramaMantenimiento(String cronogramaMantenimiento) throws DataEmptyException {
	    if (cronogramaMantenimiento == null || cronogramaMantenimiento.isEmpty()) {
	        throw new DataEmptyException("el cronograma de mantenimiento no puede ser null o vacío");
	    }
	    this.cronogramaMantenimiento = cronogramaMantenimiento;
	}
	
	

}
