package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class Voluntario extends Persona{
	
	private static int contadorVoluntario = 0;

	private String tarea;
	private boolean disponible;
	private String username;
	private String codigo;
	private ArrayList<OrdenRetiro> ordenesRetiro;
	
	
	public Voluntario(String nombre, String apellido, String preferenciaContacto, String username) {
		super(nombre, apellido, preferenciaContacto);
		this.username = username;
		crearCodigo();
	}

	
	
	public String toString() {
	    return nombre + " " + apellido; // o como prefieras mostrarlo
	}
	

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodigo() {
		return codigo;
	}

	
	public void asignarOrden(OrdenRetiro orden) {
		this.ordenesRetiro.add(orden);
	}
	
	
	public ArrayList<OrdenRetiro> getOrdenesRetiro() {
		return ordenesRetiro;
	}

	private void crearCodigo() {
		  contadorVoluntario++;
		  this.codigo = "V" + String.format("%05d", contadorVoluntario);
	}
	

	
}
