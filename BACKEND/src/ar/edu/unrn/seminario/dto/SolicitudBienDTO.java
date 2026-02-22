package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;


public class SolicitudBienDTO {
	private String codigo;
	private BeneficiarioDTO beneficiario;
	private ArrayList<BienDTO> bienesSolicitados;
	private String estado;
	
	
	
	
	
	public SolicitudBienDTO(String codigo, BeneficiarioDTO beneficiario, ArrayList<BienDTO> bienesSolicitados, String estado) {
		super();
		this.codigo = codigo;
		this.beneficiario = beneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}






	public BeneficiarioDTO  getBeneficiario() {
		return beneficiario;
	}





	public void setBeneficiario(BeneficiarioDTO beneficiario) {
		this.beneficiario = beneficiario;
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
