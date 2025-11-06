package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DataNullException;



public class OrdenPedido extends Orden {
	
	private static int contadorOrdenPedido = 0;
	public static String tipo="ORDEN_PEDIDO";
	private String codigo;
	private boolean cargaPesada;
	private String observaciones;
	private String codDonante;
	private String codDonacion;
	
	

	public OrdenPedido(LocalDateTime fechaEmision, boolean cargaPesada,
			String observaciones, String codDonante, String codDonacion) throws DataNullException {
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		if (fechaEmision==null) {
			throw new DataNullException("El campo Fecha no puede estar VACIO"); 
		}
		if (observaciones != null || observaciones.length() > 300) {
			throw new DataNullException("El campo  no puede estar VACIO"); 
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
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public boolean isCargaPesada() {
		return cargaPesada;
	}
	public void setCargaPesada(boolean cargaPesada) {
		this.cargaPesada = cargaPesada;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public String getCodDonante() {
		return codDonante;
	}

	public void setCodDonante(String codDonante) {
		this.codDonante = codDonante;
	}

	public String getCodDonacion() {
		return codDonacion;
	}

	public void setCodDonacion(String codDonacion) {
		this.codDonacion = codDonacion;
	}
	
	private void crearCodigo() {
		  contadorOrdenPedido++;
		  this.codigo = "OP" + String.format("%05d", contadorOrdenPedido);
	}
	public static String getTipo() {
		return tipo;
	}
	public static void setTipo(String tipo) {
		OrdenPedido.tipo = tipo;
	}
	
	
}
