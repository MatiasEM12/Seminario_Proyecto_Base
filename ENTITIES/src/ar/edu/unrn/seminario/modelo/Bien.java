
package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.*;

public class Bien {
	
	private static int contadorBien = 0;
	
	private String codigo;
	private String tipo;
	private Double peso;
	private String nombre;
	private String descripcion;
	private int nivelNecesidad;
	private LocalDate fechaVencimiento;
	private Double talle;
	private String material;
	
	
	
	
	public Bien(String codigo, String tipo, Double peso, String nombre, String descripcion, int nivelNecesidad,
			LocalDate fechaVencimiento, Double talle, String material) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException  {
		
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
		
		
		if(tipo.equalsIgnoreCase("Mueble") ||tipo.equalsIgnoreCase("Electrodomestico"))   {
			
			
				validarDoubleBien(peso,"peso"); 
			
				validarStringsBien(nombre,"Nombre");
				validarLongitudCampo50(nombre,"nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
				validarStringsBien(material,"material");
				validarLongitudCampo50(material,"material");
				validarLongitudCampo255(descripcion,"Descripcion");
		
			
			this.tipo=tipo;
			this.peso = peso;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.material = material;
	
		}else if(tipo.equalsIgnoreCase("Alimento") ||tipo.equalsIgnoreCase("Medicamento")){
			
			
		
				validarStringsBien(nombre,"Nombre");
				validarLongitudCampo50(nombre,"nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
				validarLongitudCampo255(descripcion,"Descripcion");
				validarVencimiento(fechaVencimiento);
				validarDate(fechaVencimiento);
			this.tipo=tipo;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.fechaVencimiento = fechaVencimiento;
			
			
			
			
		}else if(tipo.equalsIgnoreCase("Ropa")) {
			
		
				validarStringsBien(nombre,"Nombre");
				validarLongitudCampo50(nombre,"nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
				validarLongitudCampo255(descripcion,"Descripcion");
		
			this.tipo=tipo;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.talle = talle;
			this.material = material;
			
			
			
			
		}else {
			
				validarStringsBien(nombre,"Nombre");
				validarLongitudCampo50(nombre,"nombre");
				validarStringsBien(descripcion,"Descripcion");
				validarStringsBien(tipo,"Tipo");
				validarLongitudCampo255(descripcion,"Descripcion");
		
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
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) throws  DataDoubleException {
		validarDoubleBien(peso,"Peso");
		this.peso = peso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws StateChangeException, DataLengthException {
		validarStringsBien(nombre,"Nombre");
		validarLongitudCampo50(nombre,"nombre");
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) throws StateChangeException, DataLengthException {
		validarStringsBien(descripcion,"Descripcion");
		validarLongitudCampo255(descripcion,"Descripcion");
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
	public void setFechaVencimiento(LocalDate fechaVencimiento) throws  DataDateException {
		validarVencimiento(fechaVencimiento);
		validarDate(fechaVencimiento);
		this.fechaVencimiento = fechaVencimiento;
	}
	public Double getTalle() {
		return talle;
	}
	public void setTalle(Double talle) throws  DataDoubleException {
		validarDoubleBien(talle,"Talle");
		this.talle = talle;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) throws StateChangeException, DataLengthException {
		validarStringsBien(material,"Material");
		validarLongitudCampo50(material,"material");
		this.material = material;
	}
	
	public static void setContadorBien(int contador) {
		Bien.contadorBien = contador;
	}
	
	private void crearCodigo() {
		  contadorBien++;
		  this.codigo = "B" + String.format("%05d", contadorBien);
	}
	private void validarStringsBien(String campo,String nombreCampo) throws StateChangeException{
		if (campo == null||campo.isEmpty()) {
			 throw new StateChangeException("El campo "+nombreCampo+" es invalido, no puede estar vacio o null");
		}
	}
	private void validarDoubleBien(double campo,String nombreCampo) throws DataDoubleException{
		if (campo<=0) {
			 throw new DataDoubleException("El campo "+nombreCampo+" es invalido: no puede ser 0 ni negativo");
			 
		}
	}
	
	private void validarLongitudCampo50( String campo,String nombreCampo) throws DataLengthException {
		if(nombreCampo=="nombre" || campo.length()<3) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 3 caracteres y como maximo 50 ");
		}
		
		if (campo.length()>50 || campo.length()<4) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 4 caracteres y como maximo 50 ");
		}
	}
	
	private void validarLongitudCampo255( String campo,String nombreCampo) throws DataLengthException {
		
		if (campo.length()>255 || campo.length()<10) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 10 caracteres y como maximo 255 ");
		}

	}
	
	private void validarVencimiento(LocalDate fechaVencimiento) throws DataDateException {
		   
	    if (fechaVencimiento.isBefore(LocalDate.now())) {
	        throw new DataDateException("Inválido, el producto está vencido");
	    }
	}
	
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
}
