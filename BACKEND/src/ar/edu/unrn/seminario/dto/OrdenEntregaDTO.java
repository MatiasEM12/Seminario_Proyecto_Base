package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.SolicitudBien;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public class OrdenEntregaDTO extends OrdenDTO{

	
	private String codigo;
	private LocalDate fechaHoraProgramada;
	private ArrayList<VisitaDTO> visitas;
	private ArrayList<BienDTO> entregados;
	private SolicitudBienDTO solicitud;
	private BeneficiarioDTO beneficiario;
	private VoluntarioDTO voluntario;
	
	public OrdenEntregaDTO(LocalDate fechaEmision, EstadoOrden estado, String tipo, String codigo,
			LocalDate fechaHoraProgramada, ArrayList<VisitaDTO> visitas, ArrayList<BienDTO> entregados,
			SolicitudBienDTO solicitud, BeneficiarioDTO beneficiario, VoluntarioDTO voluntario) {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		this.entregados = entregados;
		this.solicitud = solicitud;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaHoraProgramada() {
		return fechaHoraProgramada;
	}

	public void setFechaHoraProgramada(LocalDate fechaHoraProgramada) {
		this.fechaHoraProgramada = fechaHoraProgramada;
	}

	public ArrayList<VisitaDTO> getVisitas() {
		return visitas;
	}

	public void setVisitas(ArrayList<VisitaDTO> visitas) {
		this.visitas = visitas;
	}

	public ArrayList<BienDTO> getEntregados() {
		return entregados;
	}

	public void setEntregados(ArrayList<BienDTO> entregados) {
		this.entregados = entregados;
	}

	public SolicitudBienDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudBienDTO solicitud) {
		this.solicitud = solicitud;
	}

	public BeneficiarioDTO getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(BeneficiarioDTO beneficiario) {
		this.beneficiario = beneficiario;
	}

	public VoluntarioDTO getVoluntario() {
		return voluntario;
	}

	public void setVoluntario(VoluntarioDTO voluntario) {
		this.voluntario = voluntario;
	}

	
	
	
}
