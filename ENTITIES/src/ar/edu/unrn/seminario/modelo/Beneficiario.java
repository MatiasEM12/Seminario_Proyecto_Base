package ar.edu.unrn.seminario.modelo;


import java.time.LocalDate;
import java.util.ArrayList;


import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;


public class Beneficiario extends Persona{
	private static int contadorBeneficiarios = 0;
	
	private Ubicacion ubicacion;
	private String username;
	private String codigo;
	private ArrayList<OrdenEntrega> ordenesEntrega;
	private int cantAcargo;
	private int prioridad;
	
	public Beneficiario(String nombre, String apellido,LocalDate fecha_nac, String dni, String Contacto,Ubicacion ubicacion,String username,int prioridad, int cantAcargo) throws DataEmptyException,DataObjectException ,DataNullException, DataDateException, DataLengthException, DataIntException{
		super(nombre, apellido, dni, fecha_nac, Contacto);
		
		this.validarObjectNull(ubicacion);
		this.validarCampoVacio(username, "nombre usuario");
		this.validarCantidadCargo(cantAcargo);
		this.validarRangoPrioridad(prioridad);
		this.ubicacion=ubicacion;
		this.username=username;
		this.cantAcargo=cantAcargo;
		this.prioridad=prioridad;
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
	
	
	
	
	public static int getContadorBeneficiarios() {
		return contadorBeneficiarios;
	}

	public static void setContadorBeneficiarios(int contadorBeneficiarios) {
		Beneficiario.contadorBeneficiarios = contadorBeneficiarios;
	}

	public int getCantAcargo() {
		return cantAcargo;
	}

	public void setCantAcargo(int cantAcargo) throws DataIntException {
		this.validarCantidadCargo(cantAcargo);
		this.cantAcargo = cantAcargo;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) throws DataIntException {
		this.validarRangoPrioridad(prioridad);
		this.prioridad = prioridad;
	}

	public ArrayList<OrdenEntrega> getOrdenesEntrega() {
		return ordenesEntrega;
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
	
	public void setCodigoDesdeBD(String codigo)
	        throws DataNullException, DataEmptyException {
	    if (codigo == null) throw new DataNullException("Código nulo");
	    if (codigo.trim().isEmpty()) throw new DataEmptyException("Código vacío");
	    this.codigo = codigo;
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


	public static void setContadorDonante(int contador) {
		Beneficiario.contadorBeneficiarios = contador;
	}
	
	private void validarCantidadCargo( int cantidadCargo) throws DataIntException {
		if (cantidadCargo<0) {
			throw new DataIntException("Cantidad a Cargo invalido ");
		}
	}
	
	
	private void validarRangoPrioridad( int prioridad) throws DataIntException {
		if (prioridad<0 || prioridad>5) {
			throw new DataIntException("Rango de prioridad invalido");
		}
	}
	
}
