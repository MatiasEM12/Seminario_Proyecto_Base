package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;



public class OrdenPedido extends Orden {
	
	private static int contadorOrdenPedido = 0;
	public static String tipo="ORDEN_PEDIDO";
	private String codigo;
	private boolean cargaPesada;
	private String observaciones;
	private String codDonacion;
	
	

	public OrdenPedido(LocalDate fechaEmision, boolean cargaPesada,
			String observaciones, String codDonacion) throws DataNullException, DataLengthException,DataEmptyException, DataDateException, DataObjectException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		
			this.validarCampoNull(observaciones);
			this.validarCampoVacio(observaciones,"Observaciones");
			this.validarLongitudCampo255(observaciones, "Observaciones");
		
			this.validarCampoNull(codDonacion);
			this.validarCampoVacio(codDonacion, "Codigo Donacion");
			
		
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		
		this.codDonacion = codDonacion;
		crearCodigo();
	}

	public OrdenPedido(String codigo,LocalDate fechaEmision, boolean cargaPesada,
			String observaciones, String codDonacion) throws DataNullException, DataLengthException,DataEmptyException, DataDateException, DataObjectException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		
			this.validarCampoNull(observaciones);
			this.validarCampoVacio(observaciones,"Observaciones");
			this.validarLongitudCampo255(observaciones, "Observaciones");
		
			this.validarCampoNull(codDonacion);
			this.validarCampoVacio(codDonacion, "Codigo Donacion");
			
		
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		
		this.codDonacion = codDonacion;
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	}
	public OrdenPedido(String codigo,LocalDate fechaEmision, boolean cargaPesada,EstadoOrden estado,
			String observaciones, String codDonacion) throws DataNullException, DataLengthException,DataEmptyException, DataDateException, DataObjectException{
		super(fechaEmision, estado,tipo);
		
			this.validarCampoNull(observaciones);
			this.validarCampoVacio(observaciones,"Observaciones");
			this.validarLongitudCampo255(observaciones, "Observaciones");
		
			this.validarCampoNull(codDonacion);
			this.validarCampoVacio(codDonacion, "Codigo Donacion");
			
		
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		
		this.codDonacion = codDonacion;
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
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
	
	private void validarLongitudCampo255( String campo,String nombreCampo) throws DataLengthException {
		
		if (campo.length()>255 || campo.length()<10) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 10 caracteres y como maximo 255 ");
		}

	}
	
	
	public void setEstado(EstadoOrden nuevoEstado)
	        throws StateChangeException, DataObjectException {

	    if (nuevoEstado == null) {
	        throw new DataObjectException("El estado de la orden no puede ser null");
	    }

	    switch (nuevoEstado) {

	        case EN_PROCESO:
	            ordenEstadoProceso();
	            break;

	        case COMPLETADA:
	            ordenEstadoCompleta();
	            break;

	        case CANCELADA:
	            ordenEstadoCancelada();
	            break;

	        default:
	            throw new StateChangeException(
	                "Estado de Orden de Retiro inválido: " + nuevoEstado
	            );
	    }
	}
	
	
	private void ordenEstadoCompleta() throws StateChangeException, DataObjectException {
		
		if(super.getEstadoString().equals(EstadoOrden.EN_PROCESO.toString()) || super.getEstadoString().equals(EstadoOrden.PENDIENTE.toString())) {
			
			super.setEstado(EstadoOrden.COMPLETADA);
		}else {
			// este es el qque proboca el error
			  throw new StateChangeException("Cambio de estado de la Orden de Retiro Invalido aqui");
		}
		
	}
	
	private void ordenEstadoProceso() throws StateChangeException, DataObjectException {
	    EstadoOrden actual = super.getEstado();
	    // Permitir que PENDIENTE pase a EN_PROCESO o que ya esté en EN_PROCESO y se mantenga
	    if (actual == EstadoOrden.PENDIENTE || actual == EstadoOrden.EN_PROCESO) {
	        super.setEstado(EstadoOrden.EN_PROCESO);
	    } else {
	        throw new StateChangeException(
	            "Cambio de estado de la Orden de Retiro inválido: " + actual
	        );
	    }
	}

	private void ordenEstadoCancelada() throws StateChangeException, DataObjectException {
		
		
		if(!super.getEstadoString().equals(EstadoOrden.COMPLETADA.toString())) {
			
			super.setEstado(EstadoOrden.CANCELADA);
		}else {

			  throw new StateChangeException("Cambio de estado de la Orden de Retiro Invalido");
		}
		
		
	}

}
