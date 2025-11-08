package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.*;
public class Donante extends Persona  {
	
	private static int contadorDonante = 0;
	
	private Ubicacion ubicacion; 
	private String username; 
	private String codigo;
	private ArrayList<OrdenPedido> ordenesPedido;
	private ArrayList<Donacion> donaciones;
	
	
	
	public Donante(String nombre, String apellido,LocalDate fecha_nac, String dni, String Contacto,Ubicacion ubicacion) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, Contacto);
		
		this.validarObjectNull(ubicacion);
		this.ubicacion=ubicacion;
		ordenesPedido= new ArrayList<>();
		donaciones= new ArrayList<>();
		crearCodigo();
		
	}
	
	
	public Donante(String nombre, String apellido,LocalDate fecha_nac, String dni, String contacto,Ubicacion ubicacion,String username) throws DataEmptyException ,DataObjectException, DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, contacto);
		
		this.validarObjectNull(ubicacion);
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		this.ubicacion=ubicacion;
		this.username=username;
		ordenesPedido= new ArrayList<>();
		donaciones= new ArrayList<>();
		crearCodigo();
	}


	public Donante(String nombre, String apellido,LocalDate fecha_nac, String dni, String contacto,Ubicacion ubicacion,String username,String codigo) throws DataEmptyException ,DataObjectException, DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, contacto);
		
		
		
		this.validarObjectNull(ubicacion);
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		this.validarCampoNull(codigo);
		this.validarCampoVacio( codigo,this.codigo);
		this.ubicacion=ubicacion;
		this.username=username;
		ordenesPedido= new ArrayList<>();
		donaciones= new ArrayList<>();
		crearCodigo();
	}

	
	
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) throws DataObjectException {
		this.validarObjectNull(ubicacion);
		this.ubicacion = ubicacion;
	}

	public String getCodigo() {
		return codigo;
	}
	



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws DataNullException, DataEmptyException {
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		this.username = username;
	}
	
	
	
	
	public ArrayList<OrdenPedido> getOrdenesPedido() {
		return ordenesPedido;
	}

	public void setOrdenesPedido(ArrayList<OrdenPedido> ordenesPedido) throws DataListException {
		this.validarListPedido(ordenesPedido);
		this.ordenesPedido = ordenesPedido;
	}

	public ArrayList<Donacion> getDonaciones() {
		return donaciones;
	}

	public void setDonaciones(ArrayList<Donacion> donaciones) throws DataListException {
		this.validarListDonacion(donaciones);
		this.donaciones = donaciones;
	}

	private void crearCodigo() {
		  contadorDonante++;
		  this.codigo = "D" + String.format("%05d", contadorDonante);
	}
	
	
	private void addDonacion(Donacion donacion) throws DataObjectException {
		this.validarObjectNull(donacion);
		this.donaciones.add(donacion);
	}
	
	public void addOrdenPedido(OrdenPedido ordenPedido) throws DataObjectException {
		this.validarObjectNull(ordenPedido);
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
	
	
	private void validarListDonacion( ArrayList<Donacion> donaciones2) throws DataListException {
		if (donaciones2==null) {
			throw new DataListException("List invalida");
		}
	}
	
	private void validarListPedido( ArrayList<OrdenPedido> ordenesPedido2) throws DataListException {
		if (ordenesPedido2==null) {
			throw new DataListException("List invalida");
		}
	}
	
	
}
