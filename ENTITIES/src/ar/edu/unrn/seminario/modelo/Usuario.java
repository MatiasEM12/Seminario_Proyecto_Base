package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.exception.*;
public class Usuario {
	private static int contadorUsuario = 0;
	private String codigo;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String email;
	private Rol rol;
	private boolean activo;

	public Usuario(String usuario, String contrasena, String nombre, String email, Rol rol) throws DataEmptyException {

		if (usuario == null) {
			System.out.println("usuario no puede ser nulo");
			//TODO: disparar exception propia
		}
		
		validarCampoVacio(usuario, "usuario");
		validarCampoVacio(contrasena, "contraseña");
		
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		
		crearCodigo();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}
	
	public String getRolName() {
		return rol.getNombre();
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public String obtenerEstado() {
		return isActivo() ? "ACTIVO" : "INACTIVO";
	}

	public void activar() throws StateChangeException  {
		
		if (activo) {
            throw new StateChangeException("El usuario ya está activado");
        }
        this.activo = true;
		
		
	}

	public void desactivar() throws StateChangeException {
		 if (!activo) {
	            throw new StateChangeException("El usuario ya está desactivado");
	        }
	        this.activo = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	
	private void crearCodigo() {
		  contadorUsuario++;
		  this.codigo = "U" + String.format("%05d", contadorUsuario);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
