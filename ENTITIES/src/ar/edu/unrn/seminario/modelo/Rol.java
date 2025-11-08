package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.exception.*;

public class Rol {
	private Integer codigo;
	private String nombre;
	private boolean activo;
	private String descripcion;

	public Rol() {

	}
	// podriamos agregar un .isEmpty en la condicion para que codigo no pueda ""
	public Rol(Integer codigo, String nombre )throws DataNullException {
		super();
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		if(nombre==null) {
			throw new DataNullException("El nombre es invalido");
		}
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public Rol(Integer codigo, String nombre,  String descripcion )throws DataNullException {
		super();
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		if(nombre==null) {
			throw new DataNullException("El nombre es invalido");
		}	
		if(descripcion==null) {
			throw new DataNullException("Descrpcion incalida");
		}
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion=descripcion;
	}
	
	public Rol(Integer codigo,  String nombre , boolean estado)throws DataNullException {
		super();
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		if(nombre==null) {
			throw new DataNullException("El nombre es invalido");
		}
		this.codigo = codigo;
		this.nombre=nombre;
		this.activo=estado;
	}
	
	

	public Rol(Integer codigo, String nombre,  String descripcion ,boolean estado )throws DataNullException {
		super();
		if(codigo==null) {
			throw new DataNullException("El codigo es invalido");
		}
		if(nombre==null) {
			throw new DataNullException("El nombre es invalido");
		}	
		if(descripcion==null) {
			throw new DataNullException("Descrpcion incalida");
		}
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion=descripcion;
		this.activo=estado; 
	}
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public void activar() throws StateChangeException {
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

	
	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
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
	
	

}
