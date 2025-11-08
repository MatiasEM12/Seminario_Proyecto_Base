package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

public class Voluntario extends Persona{
	
	private static int contadorVoluntario = 0;

	private String tarea;
	private boolean disponible;
	private String username;
	private String codigo;
	private ArrayList<OrdenRetiro> ordenesRetiro;
	
	
	public Voluntario(String nombre, String apellido,LocalDate fecha_nac,String contacto, String dni,String username) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, contacto);
	
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		
		this.username = username;
		crearCodigo();
	}

	public Voluntario(String nombre, String apellido,LocalDate fecha_nac,String contacto, String dni,String username,String codigo) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, contacto);
	
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		this.validarCampoNull(codigo);
		this.validarCampoVacio( codigo,this.codigo);
		
		
		this.username = username;
		this.codigo=codigo;
	}

	public Voluntario(String nombre, String apellido,LocalDate fecha_nac,String contacto, String dni,String username,boolean disponible) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, contacto);
		
		
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		
		
		this.disponible=disponible;
		this.username = username;
		crearCodigo();
	}
	
	public String toString() {
	    return nombre + " " + apellido; // o como prefieras mostrarlo
	}
	

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) throws DataNullException, DataEmptyException {
		this.validarCampoNull(tarea);
		this.validarCampoVacio( tarea,this.tarea);
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

	public void setUsername(String username) throws DataNullException, DataEmptyException {
		this.validarCampoNull(username);
		this.validarCampoVacio( username,this.username);
		this.username = username;
	}

	public String getCodigo() {
		return codigo;
	}

	
	public void asignarOrden(OrdenRetiro orden) throws DataObjectException {
		this.validarObjectNull(orden);
		this.ordenesRetiro.add(orden);
	}
	
	
	public ArrayList<OrdenRetiro> getOrdenesRetiro() {
		return ordenesRetiro;
	}

	private void crearCodigo() {
		  contadorVoluntario++;
		  this.codigo = "V" + String.format("%05d", contadorVoluntario);
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
	

	
	private void validarListPedido( ArrayList<OrdenRetiro> ordenesRetiro2) throws DataListException {
		if (ordenesRetiro2==null) {
			throw new DataListException("List invalida");
		}
	}
	
}
