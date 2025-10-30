package ar.edu.unrn.seminario.modelo;

public class Ubicacion {
	private String codigo;
	private String zona;
	private String Barrio;
	private String direccion;
	
	public Ubicacion(String zona, String barrio, String direccion) {
		super();
		this.zona = zona;
		Barrio = barrio;
		this.direccion = direccion;
	}

	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getBarrio() {
		return Barrio;
	}
	public void setBarrio(String barrio) {
		Barrio = barrio;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigo() {
		return codigo;
	}
	
	
}
