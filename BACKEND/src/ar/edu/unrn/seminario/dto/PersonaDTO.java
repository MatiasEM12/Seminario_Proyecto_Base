package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public abstract class PersonaDTO {


	protected String nombre;
	protected String codigo;
	protected String apellido;
	protected String contacto;
	protected LocalDate fecha_nac;
	protected String  dni;
	
	
	protected PersonaDTO(String nombre, String apellido, String contacto,String dni, LocalDate fecha_nac) {
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.contacto = contacto;
		this.dni=dni;
		this.fecha_nac=fecha_nac;
	
	}

	public String getNombre() {
		return nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getApellido() {
		return apellido;
	}

	public String getContacto() {
		return contacto;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getCodUbicacion() {
		return codUbicacion;
	}

	public void setCodUbicacion(String codUbicacion) {
		this.codUbicacion = codUbicacion;
	}
	
	
	
}
