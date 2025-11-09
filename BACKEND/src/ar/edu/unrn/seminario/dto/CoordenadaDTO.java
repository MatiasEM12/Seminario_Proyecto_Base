package ar.edu.unrn.seminario.dto;

public class CoordenadaDTO {
	
	private double latitud;
	private double longitud;
	private String codigo;
	public CoordenadaDTO(double latitud, double longitud, String codigo) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.codigo = codigo;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public String getCodigo() {
		return codigo;
	}
	
	

}
