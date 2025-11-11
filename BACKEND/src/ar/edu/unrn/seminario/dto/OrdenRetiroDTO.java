package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;


import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;


public class OrdenRetiroDTO extends OrdenDTO{

	private String codigo;
	private String codPedido;
	private String codVoluntario;
	private String[] codVisitas;
	
	public OrdenRetiroDTO(LocalDate fechaEmision, EstadoOrden estado, String tipo, String codigo, String codPedido,
			String codVoluntario, String[] codVisitas) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.codPedido = codPedido;
		this.codVoluntario = codVoluntario;
		this.codVisitas = codVisitas;
	}
	
	

	public OrdenRetiroDTO(LocalDate fechaEmision, EstadoOrden estado, String tipo, String codigo, String codPedido,
			String codVoluntario) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.codPedido = codPedido;
		this.codVoluntario = codVoluntario;
	}



	public String getPedido() {
		return codPedido;
	}

	public void setPedido(String codPedido) {
		this.codPedido = codPedido;
	}

	public String[] getCodVisitas() {
		return codVisitas;
	}

	public void setCodVisitas(String[] codVisitas) {
		this.codVisitas = codVisitas;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCodVoluntario() {
		return codVoluntario;
	}

	public void setCodVoluntario(String codVoluntario) {
		this.codVoluntario = codVoluntario;
	}
	
	
	
	
}
