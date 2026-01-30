package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.exception.*;
public class Usuario {
	private static int contadorUsuario = 0;
	private String codigo;
	private String usuario;
	private String contrasena;
	private String nombre;
	private String contacto;
	private Rol rol;
	private boolean activo=false;
	private String estado;

	public Usuario(String usuario, String contrasena, String nombre, String contacto, Rol rol,boolean activo,String codigo) throws DataEmptyException, DataNullException, DataObjectException, DataLengthException {

	
		
		validarCampoVacio(usuario, "usuario");
		validarCampoVacio(contrasena, "contraseña");
		validarCampoVacio(nombre, "nombre");
		validarCampoVacio(contacto, "contacto");
		
		validarRol(rol,"rol");
		validarCampoNull(usuario, "usuario");
		validarCampoNull(contrasena, "contraseña");
		validarCampoNull(nombre, "nombre");
		validarCampoNull(contacto, "contacto");
		
		validarLongitudCampo20(usuario, "usuario");
		validarLongitudCampo20(contrasena, "contraseña");
		validarLongitudCampo50(nombre,"nombre");
		
		validarContacto(contacto);
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.contacto = contacto;
		this.rol = rol;
		this.activo=activo;
		this.setEstado();
		
		if(codigo==null) {
			this.crearCodigo();
		}else {
			this.codigo=codigo;
		}
	}

	private void setEstado() {
		
		if(this.activo) {
			this.estado="Activo";
		}else {
			this.estado="Inactivo";
		}
		
	}
	public String getEstado() {
		return estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) throws DataEmptyException, DataLengthException, DataNullException {
		validarCampoVacio(usuario, "usuario");
		validarLongitudCampo20(usuario, "usuario");
		validarCampoNull(usuario, "usuario");
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) throws DataEmptyException, DataNullException, DataLengthException {
		validarCampoVacio(contrasena, "contraseña");
		validarCampoNull(contrasena, "contraseña");
		validarLongitudCampo20(contrasena, "contraseña");
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws DataEmptyException, DataNullException, DataLengthException {
		validarCampoVacio(nombre, "nombre");
		validarCampoNull(nombre, "nombre");
		validarLongitudCampo50(nombre,"nombre");
		this.nombre = nombre;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) throws DataEmptyException {
		validarCampoVacio(contacto, "contacto");
		this.contacto= contacto;
	}

	public Rol getRol() {
		return rol;
	}
	
	public String getRolName() {
		return rol.getNombre();
	}

	public void setRol(Rol rol) throws DataObjectException {
		validarRol(rol,"rol");
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
	private void validarCampoNull( String campo,String nombreCampo) throws DataNullException {
		if (campo==null) {
			throw new DataNullException("el campo " + nombreCampo + " no puede ser nulo");
		}
	}
	
	private void validarLongitudCampo20( String campo,String nombreCampo) throws DataLengthException {
	
		if(nombreCampo=="usuario") {
			if (campo.length()>20 || campo.length()<3) {
				throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 3 caracteres y como maximo 20 ");
			}
		}else if(nombreCampo=="contraseña") {
			if (campo.length()>20 || campo.length()<8) {
				throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 8 caracteres y como maximo 20 ");
			}
			
		}
	}
	
	private void validarContacto(String contacto) throws DataLengthException {
       

        if (esEmail(contacto)) {
        	if (contacto.length()>30 || contacto.length()<17) {
    			throw new DataLengthException("el correo debe tener como minimo 17 caracteres incluyendo "+"@tipo_correo.com");
    		}
            return; // válido como email
        }

        if (esTelefono(contacto)) {
        	
        	if (contacto.length()>13 || contacto.length()<10) {
    			throw new DataLengthException("el telefono debe tener como minimo 10 caracteres y como maximo 13");
    		}
            return; // válido como teléfono
        }

        throw new IllegalArgumentException("El contacto no es ni un teléfono ni un email válido");
    }
	
	 private boolean esEmail(String valor) {
	        return valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
	    }

	    private boolean esTelefono(String valor) {
	        return valor.matches("^\\+?[0-9]{8,15}$");
	    }
	
	private void validarLongitudCampo50( String campo,String nombreCampo) throws DataLengthException {
		if (campo.length()>50 || campo.length()<3) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 3 caracteres y como maximo 50 ");
		}
	}
	
	
	private void validarRol(Rol rol, String nombreCampo) throws DataObjectException {
		if (rol==null) {
			throw new DataObjectException("el campo " + nombreCampo + " no puede ser vacio");
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
	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", usuario=" + usuario + ", nombre=" + nombre + ", email=" + contacto
				+ ", rol=" + rol + ", activo=" + activo + "]";
	}

	public static void setContadorUsuario(int contador) {
		Usuario.contadorUsuario = contador;
	}
	
	
	
}
