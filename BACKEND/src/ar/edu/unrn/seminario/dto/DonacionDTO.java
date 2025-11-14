package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.*;

public class DonacionDTO {
	private String codigo;
	private LocalDate fechaDonacion;
	private String observacion;
	
	private ArrayList<BienDTO> bienes;
	private String codDonante;
	private String codPedido;
	private String codRetiro;


	
	

	public DonacionDTO(String codigo, LocalDate fechaDonacion, String observacion, ArrayList<BienDTO> bienes,
			String codDonante, String codPedido, String codRetiro) {
		super();
		this.codigo = codigo;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.codDonante = codDonante;
		this.codPedido = codPedido;
		this.codRetiro = codRetiro;
	}

	


	public DonacionDTO(String codigo, LocalDate fechaDonacion, String observacion, ArrayList<BienDTO> bienes,
			String codDonante, String codPedido) {
		super();
		this.codigo = codigo;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.codDonante = codDonante;
		this.codPedido = codPedido;
	}



	public String getCodigo() {
		return this.codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public LocalDate getFechaDonacion() {
		return this.fechaDonacion;
	}
	public void setFechaDonacion(LocalDate fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}
	public String getObservacion() {
		return this.observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public ArrayList<BienDTO> getBienes() {
		return this.bienes;
	}
	
	
	public void setBienes(ArrayList<BienDTO> bienes){
		this.bienes = bienes;
	}



	public String getCodDonante() {
		return codDonante;
	}



	public void setCodDonante(String codDonante) {
		this.codDonante = codDonante;
	}



	public String getCodPedido() {
		return codPedido;
	}



	public void setCodPedido(String codPedido) {
		this.codPedido = codPedido;
	}



	public String getCodRetiro() {
		return codRetiro;
	}



	public void setCodRetiro(String codRetiro) {
		this.codRetiro = codRetiro;
	}




			

	
}
