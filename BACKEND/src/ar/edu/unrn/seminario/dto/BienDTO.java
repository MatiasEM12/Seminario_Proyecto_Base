package ar.edu.unrn.seminario.dto;

import java.time.LocalDateTime;

public class BienDTO {

	
	private String codigo;
	private String tipo;
	private double peso;
	private String nombre;
	private String descripcion;
	private int nivelNecesidad;
	private LocalDateTime fechaVencimiento;
	private Double talle;
	private String material;
	
	
	
	
	
	public BienDTO(String codigo, String tipo, double peso, String nombre, String descripcion, int nivelNecesidad,
			LocalDateTime fechaVencimiento, Double talle, String material) {//otro
		super();
		this.codigo = codigo;
		this.tipo = tipo;
		this.peso = peso;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.nivelNecesidad = nivelNecesidad;
		this.fechaVencimiento = fechaVencimiento;
		this.talle = talle;
		this.material = material;
	}





	public BienDTO(String nombre, String descripcion, LocalDateTime fechaVencimiento,String tipo) { //alimento,medicamento 
		super();
		this.tipo=tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaVencimiento = fechaVencimiento;
	}
	
	
	
	
	
	public BienDTO(double peso, String nombre, String descripcion, String material,String tipo) { //mueble, electrodomestico 
		super();
		this.tipo=tipo;
		this.peso = peso;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.material = material;
	}

	



	public BienDTO(String nombre, String descripcion, Double talle, String material,String tipo) { //ropa
		super();
		
		this.tipo=tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.talle = talle;
		this.material = material;
	}





	public double getPeso() {
		return peso;
	}





	public void setPeso(double peso) {
		this.peso = peso;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getDescripcion() {
		return descripcion;
	}





	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}





	public int getNivelNecesidad() {
		return nivelNecesidad;
	}





	public void setNivelNecesidad(int nivelNecesidad) {
		this.nivelNecesidad = nivelNecesidad;
	}





	public LocalDateTime getFechaVencimiento() {
		return fechaVencimiento;
	}





	public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}





	public Double getTalle() {
		return talle;
	}





	public void setTalle(Double talle) {
		this.talle = talle;
	}





	public String getMaterial() {
		return material;
	}





	public void setMaterial(String material) {
		this.material = material;
	}





	public String getCodigo() {
		return codigo;
	}





	public String getTipo() {
		return tipo;
	}





	
	
}
