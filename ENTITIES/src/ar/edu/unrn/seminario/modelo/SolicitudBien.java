package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class SolicitudBien {
private static int contadorSolicitud = 0;
	
	
	private String codigo;
	private String codBeneficiario;
	private ArrayList<Bien> bienesSolicitados;
	private String estado;
	
	
	
	
	
	public SolicitudBien(String codigo, String codBeneficiario, ArrayList<Bien> bienesSolicitados, String estado) {
		super();
		this.codigo = codigo;
		this.codBeneficiario = codBeneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}



	public SolicitudBien(String  codBeneficiario, ArrayList<Bien> bienesSolicitados, String estado) {
		super();
		crearCodigo();
		this.codBeneficiario = codBeneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}



	public static int getContadorSolicitud() {
		return contadorSolicitud;
	}



	public static void setContadorSolicitud(int contadorSolicitud) {
		SolicitudBien.contadorSolicitud = contadorSolicitud;
	}





	public String getBeneficiario() {
		return codBeneficiario;
	}





	public void setBeneficiario(String codBeneficiario) {
		this.codBeneficiario = codBeneficiario;
	}



	public ArrayList<Bien> getBienesSolicitados() {
		return bienesSolicitados;
	}



	public void setBienesSolicitados(ArrayList<Bien> bienesSolicitados) {
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



	private void crearCodigo() {
		contadorSolicitud++;
		  this.codigo = "SB" + String.format("%05d", contadorSolicitud);
	}
}
