package ar.edu.unrn.seminario.dto;

public abstract class PersonaDTO {

	protected String nombre;
	protected String codigo;
	protected String apellido;
	protected String preferenciaContacto;
	
	protected PersonaDTO(String nombre, String apellido, String preferenciaContacto) {
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.preferenciaContacto = preferenciaContacto;
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

	public String getPreferenciaContacto() {
		return preferenciaContacto;
	}
	
	
}
