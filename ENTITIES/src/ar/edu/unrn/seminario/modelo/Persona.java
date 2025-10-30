package ar.edu.unrn.seminario.modelo;

public class Persona {
	protected String nombre;
	protected String apellido;
	protected String preferenciaContacto;
	
	protected Persona(String nombre, String apellido, String preferenciaContacto) {
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.preferenciaContacto = preferenciaContacto;
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
	public String getPreferenciaContacto() {
		return preferenciaContacto;
	}
	public void setPreferenciaContacto(String preferenciaContacto) {
		this.preferenciaContacto = preferenciaContacto;
	}
	
}
