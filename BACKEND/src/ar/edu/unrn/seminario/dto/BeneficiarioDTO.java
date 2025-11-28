package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class BeneficiarioDTO extends PersonaDTO{
	private UbicacionDTO ubicacion;
	private String username;
	private String codigo;
	//
	private ArrayList<OrdenEntrega> ordenesEntrega;
	
	public BeneficiarioDTO(String nombre, String apellido,LocalDate fecha_nac, String dni, String Contacto,UbicacionDTO ubicacion,String codigo,String username) {
		super(nombre, apellido,Contacto, dni, fecha_nac);
		this.ubicacion=ubicacion;
		this.codigo=codigo;
		this.username=username;
		
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UbicacionDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getCodigo() {
		return codigo;
	}
}
