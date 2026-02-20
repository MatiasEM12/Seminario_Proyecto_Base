package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import java.util.ArrayList;


import ar.edu.unrn.seminario.exception.*;


public class OrdenRetiro extends Orden{
	
	public static final String tipo="ORDEN_RETIRO";
	private static int contadorOrdenRetiro = 0;
	
	private String codigo;
	
	private OrdenPedido pedido;
	private Voluntario voluntario;
	
	private ArrayList<Visita> visitas;
	private ArrayList<Bien> recolectados;
    private ArrayList<Bien> bienesEsperados;
	
	public OrdenRetiro( LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas)throws DataNullException, DataObjectException, DataListException, DataDateException, DataEmptyException, StateChangeException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		
		this.validarObjectNull(pedido);
		this.validarListVisita(visitas);
		this.validarDate(fechaEmision);
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		pedido.setEstado(Orden.EstadoOrden.EN_PROCESO);
		crearCodigo();
	}
	



	public OrdenRetiro( LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas,Voluntario voluntario) throws DataNullException, DataObjectException, DataListException, DataDateException, DataEmptyException, StateChangeException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
	
		this.validarObjectNull(pedido);
		this.validarListVisita(visitas);
		this.validarObjectNull(voluntario);
		this.validarDate(fechaEmision);
		
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
		pedido.setEstado(Orden.EstadoOrden.EN_PROCESO);
		this.voluntario=voluntario;
		}
		

		public OrdenRetiro( String codigo ,String estado,LocalDate fechaEmision, Voluntario voluntario, OrdenPedido ordenPedido,ArrayList <Visita> visitas) throws DataObjectException, DataListException, DataDateException, DataNullException, DataEmptyException{
			
		
			super(fechaEmision,estado,tipo);
	
			this.validarObjectNull(ordenPedido);
			this.validarListVisita(visitas);
			this.validarObjectNull(voluntario);
			this.validarDate(fechaEmision);
			this.pedido = ordenPedido;
			this.visitas = visitas;
			this.recolectados = new ArrayList<>();
			this.voluntario=voluntario;
			if(codigo==null) {
				crearCodigo();
			}else {
				this.codigo=codigo;
			}
	}
	
	

	public ArrayList<Bien> getBienesEsperados() {
			return bienesEsperados;
		}

		public void setBienesEsperados(ArrayList<Bien> bienesEsperados) {
			this.bienesEsperados = bienesEsperados;
		}




	public String getEstadoRetiro() {
		return super.getEstadoString();
	}

	public void setEstado(EstadoOrden nuevoEstado)
	        throws StateChangeException, DataObjectException {

	    if (nuevoEstado == null) {
	        throw new DataObjectException("El estado de la orden no puede ser null");
	    }

	    switch (nuevoEstado) {

	        case EN_PROCESO:
	            ordenEstadoProceso();
	            break;

	        case COMPLETADA:
	            ordenEstadoCompleta();
	            break;

	        case CANCELADA:
	            ordenEstadoCancelada();
	            break;

	        default:
	            throw new StateChangeException(
	                "Estado de Orden de Retiro inválido: " + nuevoEstado
	            );
	    }
	}

	private void ordenEstadoCompleta() throws StateChangeException, DataObjectException {
		
		if(super.getEstadoString().equals(EstadoOrden.EN_PROCESO.toString()) ) {
			
			super.setEstado(EstadoOrden.COMPLETADA);
		}else {
			  throw new StateChangeException("Cambio deestado de la Orden de Retiro Invalido");
		}
		
	}
	
	private void ordenEstadoProceso() throws StateChangeException, DataObjectException {
	    EstadoOrden actual = super.getEstado();
	    // Permitir que PENDIENTE pase a EN_PROCESO o que ya esté en EN_PROCESO y se mantenga
	    if (actual == EstadoOrden.PENDIENTE || actual == EstadoOrden.EN_PROCESO) {
	        super.setEstado(EstadoOrden.EN_PROCESO);
	    } else {
	        throw new StateChangeException(
	            "Cambio de estado de la Orden de Retiro inválido: " + actual
	        );
	    }
	}

	private void ordenEstadoCancelada() throws StateChangeException, DataObjectException {
		
		
		if(!super.getEstadoString().equals(EstadoOrden.COMPLETADA.toString())) {
			
			super.setEstado(EstadoOrden.CANCELADA);
		}else {

			  throw new StateChangeException("Cambio deestado de la Orden de Retiro Invalido");
		}
		
		
	}
	public OrdenPedido getPedido() {
		return pedido;
	}
	public void setPedido(OrdenPedido pedido) throws DataObjectException {
		this.validarObjectNull(pedido);
		this.pedido = pedido;
	}
	public ArrayList<Visita> getVisitas() {
		return visitas;
	}
	public void agregarVisita(Visita visita)
	        throws DataObjectException, StateChangeException, DataListException {

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

	private void actualizarEstadoOrden(Visita visita)
	        throws StateChangeException, DataObjectException {


	
		
	 //this.setEstado(EstadoOrden.EN_PROCESO);
	  boolean todosBienes =comprobarBienes(bienesEsperados, recolectados);
	    if (visita.isEsFinal()) {

	      if(!visita.getBienesRecolectados().isEmpty()&&todosBienes) {
	    	  //caso: es la visita Final y tiene bienes, además todos los bienes fueron retirados 
	    	  
	    	  visita.completar();
	    	  this.ordenEstadoCompleta();
	    	  this.pedido.setEstado(this.getEstado());
	      }else {
	    	  //caso: la visita es final y no tiene materiales, pero no habran más visitas para retirar los bienes pendientes 
	    	  visita.completar();
	    	  this.ordenEstadoCompleta();
	    	  this.pedido.setEstado(this.getEstado());
	      }

	       		
	       	
	    }else {
	    	
	    	if(!visita.getBienesRecolectados().isEmpty() && !!this.recolectados.isEmpty()) {
	    		//caso: La visita no es final , tiene bienes y la OrdenRetiro tiene bienes  por lo cual quedan bienes a retirar. 
	    		visita.enProceso();
	    		this.ordenEstadoProceso();
	    		
	    	}else {
	    		//caso: La visita no es final, no tiene bienes y la OrdenRetiro no tiene bienes, esa visita no modificaciones de bienes.
	    		visita.enPendiente();
	    		this.ordenEstadoProceso();
	    		pedido.setEstado(this.getEstado());
	    		
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
	        if (this.pedido != null) {
	            this.pedido.setEstado(this.getEstado());
	        }
	    }
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

	public void agregarBien(Bien bien) throws DataObjectException {
		this.validarObjectNull(bien);
		this.recolectados.add(bien);
	}
	
	public void agregarBienes (ArrayList<Bien> bienes) throws DataListException {
		this.validarListBien(bienes);
		this.recolectados.addAll(bienes);
	}
	public String getCodigo() {
		return codigo;
	}
	
	private void crearCodigo() {
		  contadorOrdenRetiro++;
		  this.codigo = "OR" + String.format("%05d", contadorOrdenRetiro);
	}
	
	public  String[]  getCodVisitas() {
		
	if (visitas == null || visitas.isEmpty()) {
        return new String[0]; // retorna arreglo vacío en vez de null
    }
    
    String[] visitaCod = new String[visitas.size()];
    	for (int i = 0; i < visitas.size(); i++) {
    		visitaCod[i] = visitas.get(i).getCodigo();
    	}
    	return visitaCod;
	}
	
	public int getCantVisitas() {
		
		
		return (visitas == null) ? 0 : visitas.size();
	}
	

	
	public ArrayList<Bien> getRecolectados() {
		return recolectados;
	}

	public Voluntario getVoluntario() {
		return voluntario;
	}


	public void setVoluntario(Voluntario voluntario) throws DataObjectException {
		this.validarObjectNull(voluntario);
		this.voluntario = voluntario;
	}


	public void setRecolectados(ArrayList<Bien> recolectados) throws DataListException {
		this.validarListBien(recolectados);
		this.recolectados = recolectados;
	}

	public void setVisitas(ArrayList<Visita> visitas) throws DataListException {
		this.validarListVisita(visitas);
		this.visitas = visitas;
	}


	public static String getTipo() {
		return tipo;
	}


	public int getCantBienes() {
		return (recolectados == null) ? 0 : recolectados.size();
	}
	
	public static void setContadorOrdenRetiro(int contador) {
		OrdenRetiro.contadorOrdenRetiro = contador;
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
	
	private void validarListVisita( ArrayList<Visita> visitas) throws DataListException {
		if (visitas==null) {
			throw new DataListException("List invalida");
		}
	}
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	

	private void validarListBien( ArrayList<Bien> bienes) throws DataListException {
		if (bienes==null) {
			throw new DataListException("List invalida");
		}
	}

}
