package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class SolicitudBien {
private static int contadorSolicitud = 0;
	
	
	private String codigo;
	private Beneficiario beneficiario;
	private ArrayList<Bien> bienesSolicitados;
	private String estado;
	
	
	
	
	
	public SolicitudBien(String codigo, Beneficiario beneficiario, ArrayList<Bien> bienesSolicitados, String estado) {
		super();
		this.codigo = codigo;
		this.beneficiario = beneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}



	public SolicitudBien(Beneficiario beneficiario, ArrayList<Bien> bienesSolicitados, String estado) {
		super();
		crearCodigo();
		this.beneficiario = beneficiario;
		this.bienesSolicitados = bienesSolicitados;
		this.estado = estado;
	}



	public static int getContadorSolicitud() {
		return contadorSolicitud;
	}



	public static void setContadorSolicitud(int contadorSolicitud) {
		SolicitudBien.contadorSolicitud = contadorSolicitud;
	}





	public Beneficiario getBeneficiario() {
		return beneficiario;
	}





	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
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
