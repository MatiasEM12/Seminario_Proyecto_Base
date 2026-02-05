package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public class OrdenEntregaDTO extends OrdenDTO{

	
	private String codigo;
	private LocalDateTime fechaHoraProgramada;
	private boolean entregaEstado;
	private boolean confimacionRecepcion;
	private String[] codBienes;
	private String[] codVisitas;
	private String codBeneficiario;
	
	
	
	

	public OrdenEntregaDTO(LocalDate fechaEmision, EstadoOrden estado, String tipo, String codigo,
			LocalDateTime fechaHoraProgramada, boolean entregaEstado, boolean confimacionRecepcion, String[] codBienes,
			String[] codVisitas, String codBeneficiario) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.entregaEstado = entregaEstado;
		this.confimacionRecepcion = confimacionRecepcion;
		this.codBienes = codBienes;
		this.codVisitas = codVisitas;
		this.codBeneficiario = codBeneficiario;
	}

	public boolean isEntregaEstado() {
		return entregaEstado;
	}

	public boolean isConfimacionRecepcion() {
		return confimacionRecepcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getFechaHoraProgramada() {
		return fechaHoraProgramada;
	}

	public void setFechaHoraProgramada(LocalDateTime fechaHoraProgramada) {
		this.fechaHoraProgramada = fechaHoraProgramada;
	}

	public String[] getCodBienes() {
		return codBienes;
	}

	public void setCodBienes(String[] codBienes) {
		this.codBienes = codBienes;
	}

	public String[] getCodVisitas() {
		return codVisitas;
	}

	public void setCodVisitas(String[] codVisitas) {
		this.codVisitas = codVisitas;
	}

	public String getCodBeneficiario() {
		return codBeneficiario;
	}

	public void setCodBeneficiario(String codBeneficiario) {
		this.codBeneficiario = codBeneficiario;
	}

	public void setEntregaEstado(boolean entregaEstado) {
		this.entregaEstado = entregaEstado;
	}

	public void setConfimacionRecepcion(boolean confimacionRecepcion) {
		this.confimacionRecepcion = confimacionRecepcion;
	}



	
}
