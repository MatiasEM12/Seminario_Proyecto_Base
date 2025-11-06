package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import ar.edu.unrn.seminario.exception.DataEmptyException;
public class Donante extends Persona  {
	
	private static int contadorDonante = 0;
	private Ubicacion ubicacion; 
	private String username; 
	private String codigo;
	private ArrayList<OrdenPedido> ordenesPedido;
	private ArrayList<Donacion> donaciones;
	
	
	
	public Donante(String nombre, String apellido, String Contacto,Ubicacion ubicacion) {
		super(nombre, apellido, Contacto);
		
		this.ubicacion=ubicacion;
		crearCodigo();
		
	}
	
	
	public Donante(String nombre, String apellido, String Contacto,Ubicacion ubicacion,String username) {
		super(nombre, apellido, Contacto);
		this.ubicacion=ubicacion;
		this.username=username;
		crearCodigo();
	}


	
	
	
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	public ArrayList<OrdenPedido> getOrdenesPedido() {
		return ordenesPedido;
	}

	public void setOrdenesPedido(ArrayList<OrdenPedido> ordenesPedido) {
		this.ordenesPedido = ordenesPedido;
	}

	public ArrayList<Donacion> getDonaciones() {
		return donaciones;
	}

	public void setDonaciones(ArrayList<Donacion> donaciones) {
		this.donaciones = donaciones;
	}

	private void crearCodigo() {
		  contadorDonante++;
		  this.codigo = "D" + String.format("%05d", contadorDonante);
	}
	
	
	private void addDonacion(Donacion donacion) {
		this.donaciones.add(donacion);
	}
	
	public void addOrdenPedido(OrdenPedido ordenPedido) {
		
		this.ordenesPedido.add(ordenPedido);
	}


	@Override
	public String toString() {
		return "Donante [username=" + username + ", codigo=" + codigo + "]";
	}

	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	private void validarCampoNull( String nombreCampo) throws DataEmptyException {
		if (nombreCampo==null) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser nulo");
		}
	}
	private void validarCampoNull( Object ob) throws DataEmptyException {
		if (ob==null) {
			throw new DataEmptyException("Contiene instancia nula ");
		}
	}
}
