package ar.edu.unrn.seminario.modelo;
import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.*;
public class Persona {
	protected String nombre;
	protected String apellido;
	protected String contacto;
	protected LocalDate fecha_nac;
	protected String  dni;
	
	protected Persona(String nombre, String apellido, String dni, LocalDate fecha_nac ,String contacto) throws DataEmptyException,DataNullException, DataDateException{
		
		this.validarCampoNull(nombre);
		this.validarCampoNull(apellido);
		this.validarCampoNull(contacto);
		this.validarDate(fecha_nac);
		this.validarCampoNull(dni);
		
		this.validarCampoVacio(dni,this.dni);
		this.validarCampoVacio(this.nombre, nombre);
		this.validarCampoVacio(this.apellido, apellido);
		this.validarCampoVacio(this.contacto, nombre);
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.contacto = contacto;
		this.fecha_nac=fecha_nac;
		this.dni=dni;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	
	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	private void validarCampoNull( String nombreCampo) throws DataNullException {
		if (nombreCampo==null) {
			throw new DataNullException("el campo " + nombreCampo + " no puede ser nulo");
		}
	}
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
}
