package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.OrdenRetiro;

public class VoluntarioDTO extends PersonaDTO{

    private  String codigo;
    private  String tarea;
    private  boolean disponible;
    private  String username;
	private ArrayList<OrdenRetiroDTO> ordenesRetiro;
    
    
    
 

    public VoluntarioDTO(String nombre, String apellido, String contacto, String dni, LocalDate fecha_nac,
			 String codigo, String nombre2, String apellido2, String contacto2, String tarea,
			boolean disponible, String username, ArrayList<OrdenRetiroDTO> ordenesRetiro) {
		super(nombre, apellido, contacto, dni, fecha_nac);
		this.codigo = codigo;
		nombre = nombre2;
		apellido = apellido2;
		contacto = contacto2;
		this.tarea = tarea;
		this.disponible = disponible;
		this.username = username;
		this.ordenesRetiro = ordenesRetiro;
	}





	public VoluntarioDTO(String nombre, String apellido, String contacto, String dni, LocalDate fecha_nac,
			String codUbicacion, String codigo, boolean disponible) {
		super(nombre, apellido, contacto, dni, fecha_nac);
		this.codigo = codigo;
		this.disponible = disponible;
	}





	public String getTarea() {
		return tarea;
	}





	public void setTarea(String tarea) {
		this.tarea = tarea;
	}





	public boolean isDisponible() {
		return disponible;
	}





	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}





	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public ArrayList<OrdenRetiroDTO> getOrdenesRetiro() {
		return ordenesRetiro;
	}





	public void setOrdenesRetiro(ArrayList<OrdenRetiroDTO> ordenesRetiro) {
		this.ordenesRetiro = ordenesRetiro;
	}





	public String getCodigo() {
		return codigo;
	}
    
    

}