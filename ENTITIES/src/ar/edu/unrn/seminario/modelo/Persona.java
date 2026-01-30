package ar.edu.unrn.seminario.modelo;
import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.*;
public class Persona {
	protected String nombre;
	protected String apellido;
	protected String contacto;
	protected LocalDate fecha_nac;
	protected String  dni;
	
	protected Persona(String nombre, String apellido, String dni, LocalDate fecha_nac ,String contacto) throws DataEmptyException,DataNullException, DataDateException, DataLengthException{
		
		this.validarCampoNull(nombre);
		this.validarCampoNull(apellido);
		this.validarCampoNull(contacto);
		this.validarDate(fecha_nac);
		this.validarCampoNull(dni);
		
		
		this.validarCampoVacio(dni,"DNI");
		this.validarCampoVacio(nombre,"Nombre");
		this.validarCampoVacio (apellido,"Apellido");
		this.validarCampoVacio( contacto,"Contacto");
		
		this.validarMayorEdad(fecha_nac);
		this.validarLongitudCampo20(dni,"dni");
		this.validarContacto(contacto);
		this.validarLongitudCampo20(nombre, "nombre");
		this.validarLongitudCampo20(apellido, "apellido");
		this.nombre = nombre;
		this.apellido = apellido;
		this.contacto = contacto;
		this.fecha_nac=fecha_nac;
		this.dni=dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws DataNullException, DataEmptyException, DataLengthException {
		this.validarCampoNull(nombre);
		this.validarCampoVacio(nombre,"nombre");
		this.validarLongitudCampo20(nombre, "nombre");
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) throws DataNullException, DataEmptyException, DataLengthException {
		this.validarCampoNull(apellido);
		this.validarCampoVacio(apellido,"Apellido");
		this.validarLongitudCampo20(apellido, "apellido");
		this.apellido = apellido;
	}

	
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) throws DataNullException, DataEmptyException {
		this.validarCampoNull(contacto);
		this.validarCampoVacio( contacto,this.contacto);
		this.contacto = contacto;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) throws DataDateException {
		this.validarDate(fecha_nac);
		this.validarMayorEdad(fecha_nac);
		this.fecha_nac = fecha_nac;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) throws DataNullException, DataEmptyException {
		this.validarCampoNull(dni);
		this.validarCampoVacio(dni,this.dni);
		this.dni = dni;
	}

	private void validarCampoVacio(String valorCampo, String nombreCampo) throws DataEmptyException {
		if (valorCampo.equals("")) {
			throw new DataEmptyException("el campo " + nombreCampo + " no puede ser vacio");
		}
	}
	private void validarCampoNull( String nombreCampo) throws DataNullException {
		if (nombreCampo==null) {
			throw new DataNullException("el campo " + nombreCampo + " no puede ser nulo");
		}
	}
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
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
	private void validarLongitudCampo20( String campo,String nombreCampo) throws DataLengthException {
		
			if(nombreCampo=="nombre" || nombreCampo=="apellido") {
				
				if (campo.length()>20 || campo.length()<3) {
					throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 3 caracteres y como maximo 20 ");
				}
			}
			if (campo.length()>20 || campo.length()<8) {
				throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 8 caracteres y como maximo 20 ");
			}
		
	}
	
	private void validarMayorEdad(LocalDate fechaNac) throws DataDateException{
		
		if (fechaNac.plusYears(18).isAfter(LocalDate.now())) {
	        throw new DataDateException("Inválido, debe ser mayor de edad");
	    }
	}
}
