package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.*;

public class Bien {
	
	private static int contadorBien = 0;
	
	private String codigo;
	private String tipo;
	private double peso;
	private String nombre;
	private String descripcion;
	private int nivelNecesidad;
	private LocalDateTime fechaVencimiento;
	private Double talle;
	private String material;
	
	

	public Bien(String nombre, String descripcion, LocalDateTime fechaVencimiento,String tipo) throws DataNullException{ //alimento,medicamento 
		super();
		if(nombre==null) {
			throw new DataNullException("Nombre ingresado es invalido");
		}
		//la descripcion no se si es necesaria por si las dudas lo pongo
		if(descripcion==null) {
			throw new DataNullException("Descripcion ingresada es invalida");
		}
		//este tambien podria no ser nesesario que sea obligatorio
		if(tipo==null) {
			throw new DataNullException("Se deve ingresar un tipo valido");
		}
		if(fechaVencimiento==null) {
			throw new DataNullException("Fecha de vencimiento invalida");
		}
		
		this.tipo=tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaVencimiento = fechaVencimiento;
		crearCodigo();
	}
	
	
	
	
	
	public Bien(double peso, String nombre, String descripcion, String material,String tipo)throws DataNullException,DataDoubleException{ //mueble, electrodomestico 
		super();
		if(peso <= 0) {
		    throw new DataDoubleException("Ingrese un peso valido");
		}
		if(nombre==null) {
			throw new DataNullException("El nombre es invalido");
		}
		if(tipo==null) {
			throw new DataNullException("Se deve ingresar un tipo valido");
		}
		this.tipo=tipo;
		this.peso = peso;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.material = material;
		crearCodigo();
	}

	


// le cambie el talle lo aviamos puesto como Double que era una objeto enves de doueble
	public Bien(String nombre, String descripcion, double talle, String material,String tipo)throws DataNullException,DataDoubleException { //ropa
		super();
		if(nombre==null) {
			throw new DataNullException("Nombre ingresado es invalido");
		}
		if(descripcion==null) {
			throw new DataNullException("Descripcion ingresada es invalida");
		}	
		if(talle <= 0) {
		    throw new DataDoubleException("Se deve ingresar un talle valido");
		}
		if(tipo==null) {
			throw new DataNullException("Se deve ingresar un tipo valido");
		}
		
		this.tipo=tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.talle = talle;
		this.material = material;
		crearCodigo();
	}

	
	


	public Bien(double peso, String nombre, String descripcion, LocalDateTime fechaVencimiento, double talle,
			String material, String tipo) throws DataNullException { //otro
		super();
		if(nombre==null) {
			throw new DataNullException("Nombre ingresado es invalido");
		}
		if(descripcion==null) {
			throw new DataNullException("Descripcion ingresada es invalida");
		}
		if(tipo==null) {
			throw new DataNullException("Se deve ingresar un tipo valido");
		}
		this.tipo=tipo;
		this.peso = peso;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaVencimiento = fechaVencimiento;
		this.talle = talle;
		this.material = material;
		crearCodigo();
	}





	public String getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	
	
	private void crearCodigo() {
		  contadorBien++;
		  this.codigo = "B" + String.format("%05d", contadorBien);
	}
	
}
