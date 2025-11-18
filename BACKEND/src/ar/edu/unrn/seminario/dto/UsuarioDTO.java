package ar.edu.unrn.seminario.dto;

public class UsuarioDTO {
	private String username;
	private String password;
	private String nombre;
	private String contacto;
	private String rol;
	private boolean activo;
	private String codigo;
	private String estado;

	public UsuarioDTO(String username, String password, String nombre, String contacto, String rol, boolean activo,
			 String codigo,String estado) {
		super();
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.contacto = contacto;
		this.rol = rol;
		this.activo = activo;
		this.codigo=codigo;
		this.estado=estado;
	}

	public UsuarioDTO(String usuario, String contrasena, String nombre, String email,String rol, boolean activo,String estado) {
		this.username = usuario;
		this.password = contrasena;
		this.nombre = nombre;
		this.contacto = email;
		this.rol=rol;
		this.activo = activo;
		this.estado=estado;
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return contacto;
	}

	public void setEmail(String contacto) {
		this.contacto = contacto;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getEstado() {
		return estado;
	}

	

	public String getCodigo() {
		return codigo;
	}

	

	
	
}
