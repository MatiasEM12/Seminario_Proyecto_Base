package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
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
	private LocalDate fechaVencimiento;
	private double talle;
	private String material;
	
	
	
	
	public Bien(String codigo, String tipo, double peso, String nombre, String descripcion, int nivelNecesidad,
			LocalDate fechaVencimiento, double talle, String material) throws DataNullException, DataDoubleException {
		
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
		
		
		if(tipo.equalsIgnoreCase("Mueble") ||tipo.equalsIgnoreCase("Electrodomestico"))   {
			
			try {
				validarDoubleBien(peso,"peso"); 
			}catch(StateChangeException e){
				throw new DataDoubleException(e.getMessage());
			}
			try {
				validarStringsBien(nombre,"Nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
			}catch(StateChangeException e) {
				throw new DataNullException(e.getMessage());
			}
			this.tipo=tipo;
			this.peso = peso;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.material = material;
	
		}else if(tipo.equalsIgnoreCase("Alimento") ||tipo.equalsIgnoreCase("Medicamento")){
			
			
			try {
				validarStringsBien(nombre,"Nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
			}catch(StateChangeException e) {
				throw new DataNullException(e.getMessage());
			}
			if(fechaVencimiento==null) {
				throw new DataNullException("Fecha de vencimiento invalida");
			}
			
			this.tipo=tipo;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.fechaVencimiento = fechaVencimiento;
			
			
			
			
		}else if(tipo.equalsIgnoreCase("Ropa")) {
			
			try {
				validarStringsBien(nombre,"Nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
			}catch(StateChangeException e) {
				throw new DataNullException(e.getMessage());
			}
			
			this.tipo=tipo;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.talle = talle;
			this.material = material;
			
			
			
			
		}else {
			try {
				validarStringsBien(nombre,"Nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
			}catch(StateChangeException e) {
				throw new DataNullException(e.getMessage());
			}
			this.tipo=tipo;
			this.peso = peso;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.fechaVencimiento = fechaVencimiento;
			this.talle = talle;
			this.material = material;
			
			
		}
		
	}


	public String getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) throws StateChangeException {
		validarStringsBien(tipo,"Tipo");
		this.tipo = tipo;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) throws StateChangeException {
		validarDoubleBien(peso,"Peso");
		this.peso = peso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws StateChangeException {
		validarStringsBien(nombre,"Nombre");
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) throws StateChangeException {
		validarStringsBien(descripcion,"Descripcion");
		this.descripcion = descripcion;
	}
	public int getNivelNecesidad() {
		return nivelNecesidad;
	}
	public void setNivelNecesidad(int nivelNecesidad)  throws StateChangeException {
		if(nivelNecesidad<0) {
			throw new StateChangeException("El nivel de necesidad no puede ser negativo");
		}
		this.nivelNecesidad = nivelNecesidad;
	}
	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(LocalDate fechaVencimiento) throws StateChangeException {
		if(fechaVencimiento==null) {
			throw new StateChangeException("La fecha es invalida");
		}
		this.fechaVencimiento = fechaVencimiento;
	}
	public double getTalle() {
		return talle;
	}
	public void setTalle(double talle) throws StateChangeException {
		validarDoubleBien(talle,"Talle");
		this.talle = talle;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) throws StateChangeException {
		validarStringsBien(material,"Material");
		this.material = material;
	}
	
	
	private void crearCodigo() {
		  contadorBien++;
		  this.codigo = "B" + String.format("%05d", contadorBien);
	}
	private void validarStringsBien(String campo,String nombreCampo) throws StateChangeException{
		if (campo == null||campo.isEmpty()) {
			 throw new StateChangeException("El campo "+nombreCampo+" es invalido, no puede estar vacio");
		}
	}
	private void validarDoubleBien(double campo,String nombreCampo) throws StateChangeException{
		if (campo<=0) {
			 throw new StateChangeException("El campo "+nombreCampo+" es invalido: no puede ser 0 ni negativo");
		}
	}
}
