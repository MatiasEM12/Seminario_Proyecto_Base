package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

public class Donacion {
	private static int contadorDonacion = 0;
	
	private String codigo;
	private LocalDate fechaDonacion;
	private String observacion;
	private ArrayList<Bien> bienes;

	private Donante donante;
	private OrdenPedido pedido;
	
	
	public Donacion( LocalDate fechaDonacion, String observacion, ArrayList<Bien> bienes, Donante donante,OrdenPedido pedido,String codigo) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException {
		super();
		
		this.validarCampoNull(observacion);
		this.validarLongitudCampo255(observacion,"observacion");
	    this.validarDate(fechaDonacion);
	    this.validarDatePeriodo(fechaDonacion);
		this.validarCampoVacio(observacion,"observacion");
		this.validarObjectNull(donante);
		this.validarObjectNull(pedido);
		this.validarList(bienes);
		this.pedido=pedido;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.donante = donante;
		
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	
	}
	
	public Donacion( LocalDate fechaDonacion, String observacion, ArrayList<Bien> bienes, Donante donante,String codigo) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException {
		super();
		
		this.validarCampoNull(observacion);
		this.validarLongitudCampo255(observacion,"observacion");
	    this.validarDate(fechaDonacion);
	    this.validarDatePeriodo(fechaDonacion);
		this.validarCampoVacio(observacion,"observacion");
		this.validarObjectNull(donante);
		this.validarList(bienes);
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.donante = donante;
		
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	public void setCodigo(String codigo) throws DataNullException, DataEmptyException {
		this.validarCampoNull(codigo);
		this.validarCampoVacio(codigo, "codigo");
		this.codigo = codigo;
	}
	public LocalDate getFechaDonacion() {
		return this.fechaDonacion;
	}
	public void setFechaDonacion(LocalDate fechaDonacion) throws DataDateException {
	    this.validarDate(fechaDonacion);
	    this.validarDatePeriodo(fechaDonacion);
		this.fechaDonacion = fechaDonacion;
	}
	public String getObservacion() {
		return this.observacion;
	}
	public void setObservacion(String observacion) throws DataNullException, DataLengthException, DataEmptyException {
		this.validarCampoNull(observacion);
		this.validarCampoVacio(observacion,"observacion");
		this.validarLongitudCampo255(observacion,"observacion");
		this.observacion = observacion;
	}

	public void agregarBien(Bien bien) throws DataObjectException {
		this.validarObjectNull(bien);
		this.bienes.add(bien);
		
	}
	
	public ArrayList<Bien> getBienes() {
		return bienes;
	}

	public void setBienes(ArrayList<Bien> bienes) throws DataListException {
		this.validarList(bienes);
		this.bienes = bienes;
	}


	
	public Donante getDonante() {
		return donante;
	}
	public void setDonante(Donante donante) throws DataObjectException {
		this.validarObjectNull(donante);
		this.donante = donante;
	}
	public OrdenPedido getPedido() {
		return pedido;
	}
	public void setPedido(OrdenPedido pedido) throws DataObjectException {
		this.validarObjectNull(pedido);
		this.pedido = pedido;
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
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	private void validarDatePeriodo(LocalDate fecha) throws DataDateException {
		if (fecha.isAfter(LocalDate.now())) {
			throw new DataDateException("La fecha es invalida");
		
		}
	}
	private void validarLongitudCampo255( String campo,String nombreCampo) throws DataLengthException {
		
		if (campo.length()>255 || campo.length()<10) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 10 caracteres y como maximo 255 ");
		}

	}
	
	private void validarList( ArrayList<Bien> Bienes) throws DataListException {
		if (Bienes==null) {
			throw new DataListException("List invalida");
		}
	}
	private void crearCodigo() {
		this.codigo = String.format("DN%05d", contadorDonacion++);
	}
	public static void setContadorDonacion(int contador) {
		Donacion.contadorDonacion = contador;
	}
}
