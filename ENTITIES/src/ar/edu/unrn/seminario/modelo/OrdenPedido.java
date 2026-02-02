package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;



public class OrdenPedido extends Orden {
	
	private static int contadorOrdenPedido = 0;
	public static String tipo="ORDEN_PEDIDO";
	private String codigo;
	private boolean cargaPesada;
	private String observaciones;
	private String codDonante;
	private String codDonacion;
	
	

	public OrdenPedido(LocalDate fechaEmision, boolean cargaPesada,
			String observaciones, String codDonante, String codDonacion) throws DataNullException, DataLengthException,DataEmptyException, DataDateException, DataObjectException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		
			this.validarCampoNull(observaciones);
			this.validarCampoVacio(observaciones,"Observaciones");
			this.validarLongitudCampo255(observaciones, "Observaciones");
		
			this.validarCampoNull(codDonacion);
			this.validarCampoVacio(codDonacion, "Codigo Donacion");
			
			this.validarCampoNull(codDonante);
			this.validarCampoVacio(codDonante, "Codigo Donante");
		
	
		
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		this.codDonante = codDonante;
		this.codDonacion = codDonacion;
		crearCodigo();
	}
	public OrdenPedido(String codigo,LocalDate fechaEmision,
			String observaciones, boolean cargaPesada, String codDonante) throws DataNullException, DataLengthException, DataEmptyException, DataDateException, DataObjectException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		
		this.validarCampoNull(observaciones);
		this.validarCampoVacio(observaciones,"Observaciones");
		this.validarLongitudCampo255(observaciones, "Observaciones");
	
		this.validarCampoNull(codDonacion);
		this.validarCampoVacio(codDonacion, "Codigo Donacion");
		
		this.validarCampoNull(codDonante);
		this.validarCampoVacio(codDonante, "Codigo Donante");
	
		if(codigo==null) {
			this.crearCodigo();
		}else {
			this.codigo=codigo;
		}

		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		this.codDonante = codDonante;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) throws StateChangeException, DataEmptyException, DataNullException {
		this.validarCampoNull(codigo);
		this.validarCampoVacio(codigo, "codigo");
		this.codigo = codigo;
	}
	public boolean isCargaPesada() {
		return cargaPesada;
	}
	public void setCargaPesada(boolean cargaPesada) throws StateChangeException {
		if (this.cargaPesada==cargaPesada) {
			if(cargaPesada==true) {
				throw new StateChangeException("Ya se encuentra como carga pesada");
			}
			else {
				throw new StateChangeException("Ya se encuentra como carga libiana");
			}
		}
		this.cargaPesada = cargaPesada;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) throws StateChangeException, DataNullException, DataEmptyException, DataLengthException {

		this.validarCampoNull(observaciones);
		this.validarCampoVacio(observaciones,"Observaciones");
		this.validarLongitudCampo255(observaciones, "Observaciones");
	
		this.observaciones = observaciones;
	}


	public String getCodDonante() {
		return codDonante;
	}

	public void setCodDonante(String codDonante) throws StateChangeException, DataNullException, DataEmptyException {
		this.validarCampoNull(codDonante);
		this.validarCampoVacio(codDonante, "Codigo Donante");
		this.codDonante = codDonante;
	}

	public String getCodDonacion() {
		return codDonacion;
	}

	public void setCodDonacion(String codDonacion) throws StateChangeException, DataNullException, DataEmptyException {
		this.validarCampoNull(codDonacion);
		this.validarCampoVacio(codDonacion, "Codigo Donacion");
		
		this.codDonacion = codDonacion;
	}
	
	private void crearCodigo() {
		  contadorOrdenPedido++;
		  this.codigo = "OP" + String.format("%05d", contadorOrdenPedido);
	}
	public static String getTipo() {
		return tipo;
	}
	public static void setTipo(String tipo) throws DataNullException, DataEmptyException {
	
		OrdenPedido.tipo = tipo;
	}
	
	public static void setContadorPedido(int contador) {
		OrdenPedido.contadorOrdenPedido = contador;
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
	private void validarLongitudCampo255( String campo,String nombreCampo) throws DataLengthException {
		
		if (campo.length()>255 || campo.length()<10) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 10 caracteres y como maximo 255 ");
		}

	}

}
