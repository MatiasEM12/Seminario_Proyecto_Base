package ar.edu.unrn.seminario.dto;


public class UbicacionDTO {

	
	private String codigo;
	private String zona;
	private String barrio;
	private String direccion;
	private CoordenadaDTO coordenada;
	public UbicacionDTO(String codigo, String zona, String barrio, String direccion, CoordenadaDTO coordenada) {
		super();
		this.codigo = codigo;
		this.zona = zona;
		this.barrio = barrio;
		this.direccion = direccion;
		this.coordenada = coordenada;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public CoordenadaDTO getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(CoordenadaDTO coordenada) {
		this.coordenada = coordenada;
	}
	public String getCodigo() {
		return codigo;
	}

	
	
}
