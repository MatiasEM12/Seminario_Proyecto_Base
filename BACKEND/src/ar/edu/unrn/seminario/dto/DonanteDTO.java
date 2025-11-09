package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.util.ArrayList;



public class DonanteDTO extends PersonaDTO{
	
	private String codigo;
	private  UbicacionDTO ubicacion;
	private String username; 
	private ArrayList<OrdenPedidoDTO>ordenesPedido;
	private ArrayList<DonacionDTO> donaciones;
	
	public DonanteDTO(String nombre, String codigo, String apellido,String dni, LocalDate fecha_nac, String contacto,UbicacionDTO ubicacion) {
		super(nombre, apellido, contacto,dni, fecha_nac);
		this.ubicacion=ubicacion;
		this.codigo=codigo;
		
	
	}
	
	public DonanteDTO(String nombre, String codigo, String apellido,String dni, LocalDate fecha_nac,
			String contacto,UbicacionDTO ubicacion,String username, ArrayList<DonacionDTO> donaciones,
			ArrayList<OrdenPedidoDTO>ordenesPedido) {
		super(nombre, apellido, contacto, dni, fecha_nac);
		this.ubicacion=ubicacion;
		this.username=username;
		this.donaciones=donaciones;
		this.ordenesPedido=ordenesPedido;
		this.codigo=codigo;
		
		
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<OrdenPedidoDTO> getOrdenesPedido() {
		return ordenesPedido;
	}

	public void setOrdenesPedido(ArrayList<OrdenPedidoDTO> ordenesPedido) {
		this.ordenesPedido = ordenesPedido;
	}

	public ArrayList<DonacionDTO> getDonaciones() {
		return donaciones;
	}

	public void setDonaciones(ArrayList<DonacionDTO> donaciones) {
		this.donaciones = donaciones;
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

