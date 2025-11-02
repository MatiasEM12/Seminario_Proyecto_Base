package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class Donante extends Persona {
	
	private static int contadorDonante = 0;
	private Ubicacion ubicacion;
	private String username; 
	private String codigo;
	private ArrayList<OrdenPedido> ordenesPedido;
	private ArrayList<Donacion> donaciones;
	
	
	public Donante(String nombre, String apellido, String preferenciaContacto) {
		super(nombre, apellido, preferenciaContacto);
	}
	public Donante(String nombre, String apellido, String preferenciaContacto,Ubicacion ubicacion) {
		super(nombre, apellido, preferenciaContacto);
		this.ubicacion=ubicacion;
		crearCodigo();
		
	}
	
	public Donante(String nombre, String apellido, String preferenciaContacto,Ubicacion ubicacion,String username) {
		super(nombre, apellido, preferenciaContacto);
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

	
}
