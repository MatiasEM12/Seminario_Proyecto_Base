package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;

public class Coordenada {
	private double latitud;
	private double longitud;
	private String codigo;
	
	private static int contadorCoordenada= 0;
	
	public Coordenada(double latitud, double longitud) throws DataNullException {
		super();
		
		this.validarCampoNull(latitud);
	
		
		this.validarCampoNull(longitud);
	
		
		this.latitud = latitud;
		this.longitud = longitud;
		this.crearCodigo();
	}

	
	public Coordenada(double latitud, double longitud,String codigo) throws DataNullException, DataEmptyException {
		super();
		this.validarCampoNull(latitud);
	
		
		this.validarCampoNull(longitud);
	
		this.validarCampoNull(codigo);
		this.validarCampoVacio( codigo,this.codigo);
		
		this.latitud = latitud;
		this.longitud = longitud;
		this.codigo=codigo;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) throws DataNullException {
		this.validarCampoNull(latitud);

		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) throws DataNullException {
		this.validarCampoNull(longitud);
	
		this.longitud = longitud;
	}
	
	private void crearCodigo() {
		  contadorCoordenada++;
		  this.codigo = "C" + String.format("%05d", contadorCoordenada);
	}
	
	
	private void validarCampoNull( Double nombreCampo) throws DataNullException {
		if (nombreCampo==null) {
			throw new DataNullException("el campo " + nombreCampo + " no puede ser nulo");
		}
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
}
