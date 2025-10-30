package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Donacion {
	private static int contadorDonacion = 0;
	
	private String codigo;
	private LocalDateTime fechaDonacion;
	private String observacion;
	private ArrayList<Bien> bienes;
	private Donante donacion;
	private String cod_Donante;
	private String cod_Pedido;
	private String cod_Retiro;
	
	public Donacion( LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes, Donante donacion) {
		super();
	
		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.donacion = donacion;
		crearCodigo();
	}
	public Donacion(LocalDateTime fechaDonacion, String observacion, ArrayList<Bien> bienes, String cod_Donante) {
		super();

		this.fechaDonacion = fechaDonacion;
		this.observacion = observacion;
		this.bienes = bienes;
		this.cod_Donante = cod_Donante;
		crearCodigo();
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

	public String getCod_Donante() {
		return cod_Donante;
	}

	public void setCod_Donante(String cod_Donante) {
		this.cod_Donante = cod_Donante;
	}

	public Donante getDonacion() {
		return this.donacion;
	}
	public void setDonacion(Donante donacion) {
		this.donacion = donacion;
	}
	
	private void crearCodigo() {
		  contadorDonacion++;
		  this.codigo = "DN" + String.format("%05d", contadorDonacion);
	}
	public String getCod_Pedido() {
		return cod_Pedido;
	}
	public void setCod_Pedido(String cod_Pedido) {
		this.cod_Pedido = cod_Pedido;
	}
	public String getCod_Retiro() {
		return cod_Retiro;
	}
	public void setCod_Retiro(String cod_Retiro) {
		this.cod_Retiro = cod_Retiro;
	}

}
