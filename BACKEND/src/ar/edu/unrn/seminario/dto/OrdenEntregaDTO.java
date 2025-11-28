package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;

public class OrdenEntregaDTO extends OrdenDTO{

	
	private String codigo;
	private LocalDateTime fechaHoraProgramada;
	private boolean entregaEstado;
	private boolean confimacionRecepcion;
	private Bien Entrega[];
	private Beneficiario beneficiario;
	
	
	public OrdenEntregaDTO(LocalDate fechaEmision, String estado, String tipo, String codigo, Bien Entrega[],Beneficiario beneficiario,boolean entregaEstado, boolean confimacionRecepcion) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.entregaEstado = entregaEstado;
		this.confimacionRecepcion = confimacionRecepcion;
		for (int i=0;i<Entrega.length;i++) {
		    this.Entrega[i]=Entrega[i];
		}
	}
	

	public boolean isEntregaEstado() {
		return entregaEstado;
	}

	public boolean isConfimacionRecepcion() {
		return confimacionRecepcion;
	}



	public void setFechaHoraProgramada(LocalDateTime fechaHoraProgramada) {
		this.fechaHoraProgramada = fechaHoraProgramada;
	}

	public LocalDateTime getFechaHoraProgramada() {
		return fechaHoraProgramada;
	}





	public void setEntrega(Bien Entrega[]) {
		for (int i=0;i<Entrega.length;i++) {
			this.Entrega[i]=Entrega[i];
		}
	}





	public Bien[] getEntrega() {
		return Entrega;
	}



	public String getCodigo() {
		return codigo;
	}
}
