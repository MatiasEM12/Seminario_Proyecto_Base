package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
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
			String observaciones, String codDonante, String codDonacion) throws DataNullException, DataLengthException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		try {
			validarStringsPedido(observaciones,"Observaciones");
			validarStringsPedido(codDonante,"Codigo de donante");
			validarStringsPedido(codDonacion,"Codigo de donacion");
		}catch(StateChangeException e) {
			throw new DataNullException(e.getMessage());			
		}
		if (observaciones.length()>300) {
			throw new DataLengthException("El campo observaciones no puede exceder los 300 caracteres"); 
		}
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		this.codDonante = codDonante;
		this.codDonacion = codDonacion;
		crearCodigo();
	}
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) throws StateChangeException {
		validarStringsPedido(codigo,"Codigo de ordene de pedido");
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
	public void setObservaciones(String observaciones) throws StateChangeException {
		validarStringsPedido(observaciones,"Observaciones");
		this.observaciones = observaciones;
	}


	public String getCodDonante() {
		return codDonante;
	}

	public void setCodDonante(String codDonante) throws StateChangeException {
		validarStringsPedido(codDonante,"Codigo donante");
		this.codDonante = codDonante;
	}

	public String getCodDonacion() {
		return codDonacion;
	}

	public void setCodDonacion(String codDonacion) throws StateChangeException {
		validarStringsPedido(codDonacion,"Codigo donacion");
		this.codDonacion = codDonacion;
	}
	
	private void crearCodigo() {
		  contadorOrdenPedido++;
		  this.codigo = "OP" + String.format("%05d", contadorOrdenPedido);
	}
	public static String getTipo() {
		return tipo;
	}
	public static void setTipo(String tipo) throws StateChangeException {
		validarStringsPedido(tipo,"Tipo");
		OrdenPedido.tipo = tipo;
	}
	private static void validarStringsPedido(String campo,String nombreCampo) throws StateChangeException{
		if (campo == null||campo.isEmpty()) {
			 throw new StateChangeException("El campo "+nombreCampo+" es invalido, no puede estar vacio");
		}
	}
	
	
}
