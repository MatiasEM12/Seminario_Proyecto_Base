package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.*;

public class DonacionDTO {
	private String codigo;
	private LocalDateTime fechaDonacion;
	private String observacion;
	//ian: cambie el bien por un arraylist para que sea igual que tanto donacion como visita sean arraylist
	//private Bien bienes[];//vercion original
	private ArrayList<Bien> bienes;
	private String codDonante;
	private String codPedido;
	private String codRetiro;
	//public DonacionDTO(String codigo, LocalDateTime fechaDonacion, String observacion, Bien[] bienes, Donante donacion) {
	//original
	public DonacionDTO(String codigo, LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes, String codDonante) {
		super();
		this.codigo = codigo;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.codDonante = codDonante;
	}
	
	

	public DonacionDTO(String codigo, LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes,
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

	


	public DonacionDTO(String codigo, LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes,
			String codDonacion, String codPedido) {
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
	public LocalDateTime getFechaDonacion() {
		return this.fechaDonacion;
	}
	public void setFechaDonacion(LocalDateTime fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}
	public String getObservacion() {
		return this.observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	//original
	//public Bien[] getBienes() {
	public ArrayList<Bien> getBienes() {
		return this.bienes;
	}
	//original
	//public void setBienes(Bien[] bienes) {
	public void setBienes(ArrayList<Bien> bienes){
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
