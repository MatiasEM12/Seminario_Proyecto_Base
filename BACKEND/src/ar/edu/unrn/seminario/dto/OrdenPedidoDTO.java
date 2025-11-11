package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public class OrdenPedidoDTO  extends OrdenDTO{
	private String codigo;
	private boolean cargaPesada;
	private String observaciones;
	private String codDonante;
	private String codDonacion;
	

	
	

	public OrdenPedidoDTO(LocalDate fechaEmision, String estado, String tipo, String codigo, boolean cargaPesada,
			String observaciones, String codDonante, String codDonacion) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.cargaPesada = cargaPesada;
		this.observaciones = observaciones;
		this.codDonante = codDonante;
		this.codDonacion = codDonacion;
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





	public String getCodigo() {
		return codigo;
	}



	
}
