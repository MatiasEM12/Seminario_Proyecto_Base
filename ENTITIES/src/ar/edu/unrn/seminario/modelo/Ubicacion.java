package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

public class Ubicacion {
	
	private static int contadorUbicacion = 0;
	private String codigo;
	private String zona;
	private String barrio;
	private String direccion;
	private Coordenada coordenada;
	public Ubicacion(String zona, String barrio, String direccion,Coordenada coordenada) throws DataEmptyException, DataNullException, DataObjectException {
		super();
		
		this.validarCampoNull(direccion);
		this.validarCampoVacio(direccion, this.direccion);
		

		this.validarCampoNull(zona);
		this.validarCampoVacio(zona, this.zona);
		

		this.validarCampoNull(barrio);
		this.validarCampoVacio(barrio, this.barrio);
		
		this.validarObjectNull(coordenada);
		
		this.zona = zona;
		this.barrio = barrio;
		this.direccion = direccion;
		this.coordenada=coordenada;
		this.crearCodigo();
	}
	
	

	public Ubicacion(String codigo, String zona, String barrio, String direccion,Coordenada coordenada) throws DataNullException, DataEmptyException, DataObjectException {
		super();
		
		
		
		this.validarCampoNull(direccion);
		this.validarCampoVacio(direccion, this.direccion);
		

		this.validarCampoNull(zona);
		this.validarCampoVacio(zona, this.zona);
		

		this.validarCampoNull(barrio);
		this.validarCampoVacio(barrio, this.barrio);
		
		this.validarObjectNull(coordenada);
		

		this.validarCampoNull(codigo);
		this.validarCampoVacio(codigo, this.codigo);
		
		this.codigo = codigo;
		this.zona = zona;
		this.barrio = barrio;
		this.direccion = direccion;
		this.coordenada=coordenada;
		this.codigo=codigo;
	}
	public Ubicacion(String codigo, String zona, String barrio, String direccion) {
		this.codigo=codigo;
		this.zona=zona;
		this.barrio=barrio;
		this.direccion=direccion;
		
		}
	Ubicacion ubicacionPrueba = new Ubicacion("UB007","ZONA-TEST", "BarrioTest", "Calle Falsa 123");
	



	public String getZona() {
		return zona;
	}
	public void setZona(String zona) throws DataEmptyException, DataNullException {
		this.validarCampoNull(zona);
		this.validarCampoVacio(zona, this.zona);
		this.zona = zona;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) throws DataNullException, DataEmptyException {
		this.validarCampoNull(barrio);
		this.validarCampoVacio(barrio, this.barrio);
		this.barrio = barrio;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) throws DataNullException, DataEmptyException {
		this.validarCampoNull(direccion);
		this.validarCampoVacio(direccion, this.direccion);
		this.direccion = direccion;
	}

	public String getCodigo() {
		return codigo;
	}
	
	

	public Coordenada getCoordenada() {
		return coordenada;
	}



	public void setCoordenada(Coordenada coordenada) throws DataObjectException {
		this.validarObjectNull(coordenada);
		this.coordenada = coordenada;
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
	private void validarObjectNull( Object ob) throws DataObjectException {
		if (ob==null) {
			throw new DataObjectException("Contiene instancia nula ");
		}
	}
	
	private void crearCodigo() {
		  contadorUbicacion++;
		  this.codigo = "UBI" + String.format("%05d", contadorUbicacion);
	}
}
