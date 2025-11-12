package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Donacion {
	private static int contadorDonacion = 0;
	
	private String codigo;
	private LocalDateTime fechaDonacion;
	private String observacion;
	private ArrayList<Bien> bienes;

	private Donante donante;
	private OrdenPedido pedido;
	
	
	public Donacion( LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes, Donante donante,OrdenPedido pedido) {
		super();
		this.pedido=pedido;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.donante = donante;
		crearCodigo();
	}
	public Donacion(LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes,Donante donante,OrdenPedido pedido,String codigo) {
		super();

		this.pedido=pedido;
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.donante = donante;
		this.codigo=codigo;
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

	
	public ArrayList<Bien> getBienes() {
		return bienes;
	}

	public void setBienes(ArrayList<Bien> bienes) {
		this.bienes = bienes;
	}


	
	public Donante getDonante() {
		return donante;
	}
	public void setDonante(Donante donante) {
		this.donante = donante;
	}
	public OrdenPedido getPedido() {
		return pedido;
	}
	public void setPedido(OrdenPedido pedido) {
		this.pedido = pedido;
	}
	private void crearCodigo() {
		  contadorDonacion++;
		  this.codigo = "DN" + String.format("%05d", contadorDonacion);
	}

}
