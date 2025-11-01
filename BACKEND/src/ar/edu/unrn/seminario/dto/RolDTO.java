package ar.edu.unrn.seminario.dto;

public class RolDTO {

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private boolean b;
	private boolean activo;

	public RolDTO(Integer codigo, String nombre, boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
		this.descripcion=descripcion;
	}

	public RolDTO(Integer codigo, String nombre, String desc) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion=desc;
	}
	public RolDTO(Integer codigo, String nombre,boolean activo, String desc) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
		this.descripcion=desc;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
