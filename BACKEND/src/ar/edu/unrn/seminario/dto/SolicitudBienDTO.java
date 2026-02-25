package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;


public class SolicitudBienDTO {
	private String codigo;
	private String codBeneficiario;
	private ArrayList<BienDTO> bienesSolicitados;
	private String estado;
	
	
	
	
	
	public SolicitudBienDTO(String codigo,String codBeneficiario, ArrayList<BienDTO> bienesSolicitados, String estado) {
		super();
		this.codigo = codigo;
		this.codBeneficiario = codBeneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}






	public String getBeneficiario() {
		return codBeneficiario;
	}





	public void setBeneficiario(String codBeneficiario) {
		this.codBeneficiario = codBeneficiario;
	}



	public ArrayList<BienDTO> getBienesSolicitados() {
		return bienesSolicitados;
	}



	public void setBienesSolicitados(ArrayList<BienDTO> bienesSolicitados) {
		this.bienesSolicitados = bienesSolicitados;
	}



	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCodigo() {
		return codigo;
	}


}
