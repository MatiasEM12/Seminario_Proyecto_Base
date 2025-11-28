package ar.edu.unrn.seminario.modelo;


import java.time.LocalDate;
import java.util.ArrayList;


import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;


public class Beneficiario extends Persona{
	private static int contadorBeneficiarios = 0;
	
	private Ubicacion ubicacion;
	private String username;
	private String codigo;
	//no estoy seguro si el lo nesesita ya que orden de entrega ya tiene beneficiario pero puede servir para el mostrar usuarios y poder ver si este tiene una orden
	private ArrayList<OrdenEntrega> ordenesEntrega;
	
	public Beneficiario(String nombre, String apellido,LocalDate fecha_nac, String dni, String Contacto,Ubicacion ubicacion) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException{
		super(nombre, apellido, dni, fecha_nac, Contacto);
		
		this.validarObjectNull(ubicacion);
		this.ubicacion=ubicacion;
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
	
	
	
	
	public ArrayList<OrdenEntrega> getOrdenEntrega() {
		return ordenesEntrega;
	}


	public void setOrdenesEntrega(ArrayList<OrdenEntrega> ordenesEntrega) throws DataListException {
		this.validarListEntrega(ordenesEntrega);
		this.ordenesEntrega = ordenesEntrega;
	}


	private void crearCodigo() {
		contadorBeneficiarios++;
		  this.codigo = "B" + String.format("%05d", contadorBeneficiarios);
	}


	@Override
	public String toString() {
		return "Beneficiario [username=" + username + ", codigo=" + codigo + "]";
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
	
	private void validarListEntrega( ArrayList<OrdenEntrega> ordenesPedido2) throws DataListException {
		if (ordenesPedido2==null) {
			throw new DataListException("List invalida");
		}
	}


	public static void setContadorDonante(int contadorBeneficiarios) {
		Beneficiario.contadorBeneficiarios = contadorBeneficiarios;
	}
	
	
}
