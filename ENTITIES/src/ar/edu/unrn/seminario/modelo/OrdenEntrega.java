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
			throws DataDateException, DataEmptyException, DataNullException, DataObjectException, DataListException {
		super(fechaEmision, estado, tipo);
		
		this.validarDate(fechaEmision);
		this.validarDate(fechaHoraProgramada);
		this.validarObjectNull(voluntario);
		this.validarObjectNull(beneficiario);
		this.validarDate(fechaHoraProgramada);
		this.validarListVisita(visitas);
		if (codigo == null) {
		    crearCodigo();
		} else {
		    this.codigo = codigo;
		}
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;

		inicializarEntregados();
	}




	public OrdenEntrega(LocalDate fechaEmision, String estado, String tipo, String codigo,
			LocalDate fechaHoraProgramada, ArrayList<Visita> visitas,
			SolicitudBien solicitud, Beneficiario beneficiario, Voluntario voluntario)
			throws DataDateException, DataEmptyException, DataNullException, DataObjectException, DataListException {
		super(fechaEmision, estado, tipo);
		if (codigo == null) {
		    crearCodigo();
		} else {
		    this.codigo = codigo;
		}
		this.validarObjectNull(voluntario);
		this.validarObjectNull(beneficiario);
		this.validarObjectNull(solicitud);
		this.validarDate(fechaHoraProgramada);
		this.validarListVisita(visitas);
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		
		inicializarEntregados();
		this.solicitud = solicitud;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;
	}







	public OrdenEntrega(LocalDate fechaEmision,String estado, String codigo,
			LocalDate fechaHoraProgramada, ArrayList<Visita> visitas, SolicitudBien solicitud,
			Beneficiario beneficiario, Voluntario voluntario)
			throws DataDateException, DataEmptyException, DataNullException, DataObjectException {
		super(fechaEmision, estado, tipo);
		if (codigo == null) {
		    crearCodigo();
		} else {
		    this.codigo = codigo;
		}
		this.fechaHoraProgramada = fechaHoraProgramada;
		this.visitas = visitas;
		this.solicitud = solicitud;
		this.beneficiario = beneficiario;
		this.voluntario = voluntario;
		inicializarEntregados();
	}




	public SolicitudBien getSolicitud() {
		return solicitud;
	}




	public void setSolicitud(SolicitudBien solicitud) {
		this.solicitud = solicitud;
	}




	private void setFecha(LocalDate fechaHoraProgramada) throws DataDateException {
		this.validarDate(fechaHoraProgramada);
	
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
	
	private void ordenEstadoCompleta() throws StateChangeException, DataObjectException {
		
		if(super.getEstadoString().equals(EstadoOrden.EN_PROCESO.toString()) || super.getEstadoString().equals(EstadoOrden.PENDIENTE.toString())) {
			
			super.setEstado(EstadoOrden.COMPLETADA);
		}else {
			
			  throw new StateChangeException("Cambio de estado de la Orden de Entrega Invalido aqui");
		}
		
	}
	
	private void ordenEstadoProceso() throws StateChangeException, DataObjectException {
	    EstadoOrden actual = super.getEstado();
	    // Permitir que PENDIENTE pase a EN_PROCESO o que ya esté en EN_PROCESO y se mantenga
	    if (actual == EstadoOrden.PENDIENTE || actual == EstadoOrden.EN_PROCESO) {
	        super.setEstado(EstadoOrden.EN_PROCESO);
	    } else {
	        throw new StateChangeException(
	            "Cambio de estado de la Orden de Entrega inválido: " + actual
	        );
	    }
	}

	private void ordenEstadoCancelada() throws StateChangeException, DataObjectException {
		
		
		if(!super.getEstadoString().equals(EstadoOrden.COMPLETADA.toString())) {
			
			super.setEstado(EstadoOrden.CANCELADA);
		}else {

			  throw new StateChangeException("Cambio de estado de la Orden de Entrega Invalido");
		}
		
		
	}
	public void setRecolectados(ArrayList<Bien> entregados) throws DataListException {
		this.validarListBien(entregados);
		this.entregados = entregados;
	}
	public void agregarVisita(Visita visita) throws StateChangeException, DataObjectException, DataListException {
		 

		this.validarObjectNull(visita);

			  
			    
			    // Primera visita
		if (visitas.isEmpty()) {
			
			 visitas.add(visita);
			 agregarBienesSiCorresponde(visita);
			 actualizarEstadoOrden(visita);
			 return;
		}

			    // Última visita existente
		 Visita ultima = visitas.get(visitas.size() - 1);
			  if (!ultima.getEstado().equalsIgnoreCase("pendiente") &&
			        !ultima.getEstado().equalsIgnoreCase("en proceso")) {
			        throw new StateChangeException(
			            "No se puede agregar una nueva visita si la última ya fue finalizada"
			        );
			    }

		visitas.add(visita);
		if(visita.getEstado().equalsIgnoreCase("Pendiente")) {
			comprobarYCambiarEstadoCancelado();
		}
		agregarBienesSiCorresponde(visita);
		actualizarEstadoOrden(visita);
		
		}
	
	
		  
	private void actualizarEstadoOrden(Visita visita)throws StateChangeException, DataObjectException {


			
				
			 //this.setEstado(EstadoOrden.EN_PROCESO);
		boolean todosBienes =comprobarBienes(solicitud.getBienesSolicitados(), entregados);//true si todos los bienes fueron entredos
		if (visita.isEsFinal()) {

			if(!visita.getBienesRecolectados().isEmpty()&&todosBienes) {
			    	  //caso: es la visita Final y tiene bienes, además todos los bienes fueron entregados 
			    	  
			 visita.completar();
			 this.ordenEstadoCompleta();
			 this.solicitud.setEstado(this.getEstado().toString());
		}else {
			    	  //caso: la visita es final y no tiene bienes, pero no habran más visitas para entregar los bienes restantes
			  visita.completar();
			  this.ordenEstadoCompleta();
			  this.solicitud.setEstado(this.getEstado().toString());
		}

			       		
			       	
		}else {
			    	
			if(!visita.getBienesRecolectados().isEmpty() && !this.entregados.isEmpty()) {
			    		//caso: La visita no es final , tiene bienes y la OrdenEntrega tiene bienes  por lo cual quedan bienes a entregar
				visita.enProceso();
			    this.ordenEstadoProceso();
			    		
			 }else {
			    		//caso: La visita no es final, no tiene bienes y la OrdenEntrega no tiene bienes, esa visita no modificaciones de bienes.
			    visita.enPendiente();
			    this.ordenEstadoProceso();
			    this.solicitud.setEstado(this.getEstado().toString());
			    		
			    comprobarYCambiarEstadoCancelado();//por si es valida a cancelar
			    		
			    		
			  }
			    	
			    	
			    	
			    }
			    
	}
	public void comprobarYCambiarEstadoCancelado()
			        throws StateChangeException, DataObjectException {

		if (visitas == null || visitas.size() < 3) {
			        return; 
	    }

	   int size = visitas.size();

			    // Tomamos las últimas 3 visitas
	   Visita v1 = visitas.get(size - 1);
	   Visita v2 = visitas.get(size - 2);
	   Visita v3 = visitas.get(size - 3);

	   if (sonTodasPendientes(v1, v2, v3)) {

			    
		v1.cancelar();
		this.ordenEstadoCancelada();
			if (this.solicitud != null) {
			   this.solicitud.setEstado(this.getEstado().toString());
			 }
	   }
	}
	private boolean comprobarBienes(ArrayList<Bien> bienesPedido,
            ArrayList<Bien> bienesRecolectados) {
		
		if (bienesPedido == null || bienesRecolectados == null) {
		return false;
		}
		
		if (bienesPedido.size() != bienesRecolectados.size()) {
		return false;
		}
		
		return bienesPedido.containsAll(bienesRecolectados)
		&& bienesRecolectados.containsAll(bienesPedido);
}

	private boolean sonTodasPendientes(Visita v1, Visita v2, Visita v3) {

			 return v1.getEstado().equalsIgnoreCase("Pendiente")
			 && v2.getEstado().equalsIgnoreCase("Pendiente")
			 && v3.getEstado().equalsIgnoreCase("Pendiente");
	}
		  
	private void agregarBienesSiCorresponde(Visita visita) throws DataListException {
			 if (visita.getBienesRecolectados() != null) {
			       this.agregarBienes(visita.getBienesRecolectados());
			}
	}
	
	public void agregarBienes (ArrayList<Bien> bienes) throws DataListException {
		this.validarListBien(bienes);
		this.entregados.addAll(bienes);
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
		this.validarDate(fechaHoraProgramada);
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
	
	public ArrayList<Bien> obtenerBienesFaltantes(ArrayList<Bien> esperados, ArrayList<Bien> entregados) {
		ArrayList<Bien> faltantes = new ArrayList<>();
		
		    
		    for (Bien bien : esperados) {
		        if (!entregados.contains(bien)) {
		           		faltantes.add(bien);
		        }
		    }
		
	    return faltantes;
	}


}


