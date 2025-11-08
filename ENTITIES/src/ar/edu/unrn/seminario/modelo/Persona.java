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
		this.validarCampoVacio(nombre,this.nombre);
		this.validarCampoVacio (apellido,this.apellido);
		this.validarCampoVacio( contacto,this.contacto);
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.contacto = contacto;
		this.fecha_nac=fecha_nac;
		this.dni=dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws DataNullException, DataEmptyException {
		this.validarCampoNull(nombre);
		this.validarCampoVacio(this.nombre, nombre);
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) throws DataNullException, DataEmptyException {
		this.validarCampoNull(apellido);
		this.validarCampoVacio(apellido,this.apellido);
		this.apellido = apellido;
	}

	
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) throws DataNullException, DataEmptyException {
		this.validarCampoNull(contacto);
		this.validarCampoVacio( contacto,this.contacto);
		this.contacto = contacto;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) throws DataDateException {
		this.validarDate(fecha_nac);
		this.fecha_nac = fecha_nac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) throws DataNullException, DataEmptyException {
		this.validarCampoNull(dni);
		this.validarCampoVacio(dni,this.dni);
		this.dni = dni;
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
