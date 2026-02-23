package ar.edu.unrn.seminario.modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;
public class OrdenEntrega extends Orden{
	
	private static int contadorEntrega = 0;
	
	public static String tipo="ORDEN_ENTREGA";
	private String codigo=null;
	private LocalDate fechaHoraProgramada;
	private ArrayList<Visita> visitas;
	private ArrayList<Bien> entregados;
	private SolicitudBien solicitud;
	private Beneficiario beneficiario;
	private Voluntario voluntario;

	public OrdenEntrega(ArrayList<Bien> bienes,Beneficiario beneficiario, LocalDate fechaEmision)throws DataNullException, DataDateException, DataEmptyException, DataObjectException, DataListException {
				super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
				
				
		this.validarListBien(bienes);
		visitas=new ArrayList<Visita> ();
		this.validarObjectNull(beneficiario);
		
	 crearCodigo();
	
		this.beneficiario=beneficiario;
	}



	
	public OrdenEntrega(LocalDate fechaEmision, String estado, String codigo,
			LocalDate fechaHoraProgramada, ArrayList<Visita> visitas, Beneficiario beneficiario,
			Voluntario voluntario)
			throws DataDateException, DataEmptyException, DataNullException, DataObjectException {
		super(fechaEmision, estado, tipo);
		
		
		this.codigo = codigo;
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;

		inicializarEntregados();
	}




	public OrdenEntrega(LocalDate fechaEmision, String estado, String tipo, String codigo,
			LocalDate fechaHoraProgramada, ArrayList<Visita> visitas,
			SolicitudBien solicitud, Beneficiario beneficiario, Voluntario voluntario)
			throws DataDateException, DataEmptyException, DataNullException, DataObjectException {
		super(fechaEmision, estado, tipo);
		this.codigo = codigo;
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		inicializarEntregados();
		this.solicitud = solicitud;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;
	}








	public SolicitudBien getSolicitud() {
		return solicitud;
	}




	public void setSolicitud(SolicitudBien solicitud) {
		this.solicitud = solicitud;
	}




	private void setFecha(LocalDate fechaHoraProgramada) throws DataDateException {
		this.validarDate(fechaHoraProgramada);
		this.validarDateProgramacion(fechaHoraProgramada);
		this.fechaHoraProgramada=fechaHoraProgramada;	
	}
	
	
	
	
	public void setVisitas(ArrayList<Visita> visitas) throws DataListException {
		this.validarListVisita(visitas);
		this.visitas = visitas;
	}

	public String getCodigo() {
		return codigo;
	}
	public LocalDate getFechaHoraProgramada() {
		return fechaHoraProgramada;
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
	private void validarObjectNull( Object ob) throws DataObjectException {
		if (ob==null) {
			throw new DataObjectException("Contiene instancia nula ");
		}
	}
	private void validarListBien( ArrayList<Bien> bienes) throws DataListException {
		if (bienes==null) {
			throw new DataListException("List invalida");
		}
	}

	private void validarListVisita( ArrayList<Visita> visitas) throws DataListException {
		if (visitas==null) {
			throw new DataListException("List invalida");
		}
	}
	private void crearCodigo() {
		contadorEntrega++;
		  this.codigo = "OE" + String.format("%05d", contadorEntrega);
	}
	
	public static void setContadorCoordenada(int contador) {
		OrdenEntrega.contadorEntrega = contador;
	}
	
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	private void validarDateProgramacion(LocalDate fecha) throws DataDateException {
		if (fecha.isBefore(LocalDate.now())) {
			throw new DataDateException("Fecha invalida");
		
		}
	}
public void ordenEstadoCompleta() throws StateChangeException, DataObjectException {
		
		if(super.getEstadoString().equals(EstadoOrden.EN_PROCESO.toString()) ) {
			
			super.setEstado(EstadoOrden.COMPLETADA);
		}else {
			  throw new StateChangeException("Cambio deestado de la Orden de Entrega Invalido");
		}
		
	}
	
	public void ordenEstadoProceso() throws StateChangeException, DataObjectException {
		
	if(super.getEstadoString().equals(EstadoOrden.PENDIENTE.toString()) ) {
			
			super.setEstado(EstadoOrden.EN_PROCESO);
		}else {
			
			  throw new StateChangeException("Cambio deestado de la Orden de Entrega Invalido");
		}
		
	}
	
	public void ordenEstadoCancelada() throws StateChangeException, DataObjectException {
		
		
		if(!super.getEstadoString().equals(EstadoOrden.COMPLETADA.toString())) {
			
			super.setEstado(EstadoOrden.CANCELADA);
		}else {

			  throw new StateChangeException("Cambio deestado de la Orden de Entrega Invalido");
		}
		
		
	}
	public void setRecolectados(ArrayList<Bien> entregados) throws DataListException {
		this.validarListBien(entregados);
		this.entregados = entregados;
	}
	public void agregarVisita(Visita visita) throws StateChangeException, DataObjectException, DataListException {
		this.validarObjectNull(visita);
		
		this.visitas.add(visita);
		if(visita.getBienesRecolectados()!=null) {
			
			this.setRecolectados(visita.getBienesRecolectados());
		}
		
		
		if(visita.isEsFinal()==true ) {
			this.ordenEstadoCompleta();
		}
		
	}
	public void agregarBien(Bien bien) throws DataObjectException {
		this.validarObjectNull(bien);
		this.entregados.add(bien);
	}


	

	public ArrayList<Bien> getEntregados() {
		return entregados;
	}


	public void setEntregados(ArrayList<Bien> entregados) throws DataListException {
		this.validarListBien(entregados);
		this.entregados = entregados;
	}


	public ArrayList<Visita> getVisitas() {
		return visitas;
	}


	public void setFechaHoraProgramada(LocalDate fechaHoraProgramada) throws DataDateException {
		this.validarDateProgramacion(fechaHoraProgramada);
		this.fechaHoraProgramada = fechaHoraProgramada;
	}


	public Voluntario getVoluntario() {
		return this.voluntario;
	}


	public static int getContadorEntrega() {
		return contadorEntrega;
	}


	public static void setContadorEntrega(int contadorEntrega) {
		OrdenEntrega.contadorEntrega = contadorEntrega;
	}
  
	

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}


	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}


	public void setVoluntario(Voluntario voluntario) throws DataObjectException {
		this.validarObjectNull(voluntario);
		this.voluntario = voluntario;
	}


	private void inicializarEntregados() {
	    this.entregados = new ArrayList<>();

	    if (this.visitas == null) {
	        return;
	    }

	    for (Visita v : visitas) {
	        if (v.getBienesRecolectados()!=null) {
	            this.entregados.addAll(v.getBienesRecolectados());
	        }
	    }
	}

	public static final boolean esEntrega(String codigo) {
	    return codigo != null && codigo.length() >= 2
	            && codigo.substring(0, 2).equals("OE");
	}


}


