package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BienDTO {

	
	private String codigo;
	private String tipo;
	private Double peso;
	private String nombre;
	private String descripcion;
	private int nivelNecesidad;
	private LocalDate fechaVencimiento;
	private Double talle;
	private String material;
	
	public BienDTO(String codigo, String tipo, Double peso, String nombre, String descripcion, int nivelNecesidad,
			LocalDate fechaVencimiento, Double talle, String material) {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
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

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
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
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    BienDTO bien = (BienDTO) o;
	    return codigo != null && codigo.equals(bien.codigo);
	}

	@Override
	public int hashCode() {
	    return codigo != null ? codigo.hashCode() : 0;
	}

	
	
	

	
}
