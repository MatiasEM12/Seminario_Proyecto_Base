package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.*;

public class Rol {
	private Integer codigo;
	private String nombre;
	private boolean activo = true;
	private String descripcion;

	public Rol() {

	}
	// podriamos agregar un .isEmpty en la condicion para que codigo no pueda ""
	public Rol(Integer codigo, String nombre )throws DataNullException {
		super();
		try {
			validarStringsRol(nombre,"Nombre");
		}catch(StateChangeException e) {
			throw new DataNullException(e.getMessage());			
		}
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public Rol(Integer codigo, String nombre,  String descripcion )throws DataNullException {
		super();
		try {
			validarStringsRol(nombre,"Nombre");
			validarStringsRol(descripcion,"Descripcion");
		}catch(StateChangeException e) {
			throw new DataNullException(e.getMessage());			
		}
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion=descripcion;
	}
	
	public Rol(Integer codigo,  String nombre , boolean estado)throws DataNullException {
		super();
		try {
			validarStringsRol(nombre,"Nombre");
		}catch(StateChangeException e) {
			throw new DataNullException(e.getMessage());			
		}
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		this.codigo = codigo;
		this.nombre=nombre;
		this.activo=estado;
	}
	
	

	public Rol(Integer codigo, String nombre,  String descripcion ,boolean estado )throws DataNullException {
		super();
		try {
			validarStringsRol(nombre,"Nombre");
			validarStringsRol(descripcion,"Descripcion");
		}catch(StateChangeException e) {
			throw new DataNullException(e.getMessage());			
		}
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion=descripcion;
		this.activo=estado; 
	}
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) throws StateChangeException {
	    if (codigo == null) {
	        throw new StateChangeException("El código no puede ser nulo");
	    }
	    // no puede ser el codigo negativo
	    if (codigo <= 0) {
	        throw new StateChangeException("El código debe ser mayor que cero");
	    }
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws StateChangeException {
		validarStringsRol(nombre,"Nombre");
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	
	public void activar(boolean b) throws StateChangeException {
		if (this.activo==true) {
			throw new StateChangeException("Ya se encuentra activo");
		}
		this.activo=true;

	}

	public void desactivar() throws StateChangeException{
		if (this.activo==false) {
			throw new StateChangeException("Ya se encuentra desactivado");
		}
		this.activo=false;
	}
	public void CambiarEstadoADesactivarOActivar(){
		if (this.activo!=false) {
			this.activo=false;
		}
		else {
			this.activo=true;
		}
	}
	

	
	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) throws StateChangeException {
		validarStringsRol(descripcion,"Descripcion");
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rol [codigo=" + codigo + ", nombre=" + nombre + ", activo=" + activo + "]";
	}
	private void validarStringsRol(String campo,String nombreCampo) throws StateChangeException{
		if (campo == null||campo.isEmpty()) {
			 throw new StateChangeException("El campo "+nombreCampo+" es invalido, no puede estar vacio");
		}
	}
	
	

}
