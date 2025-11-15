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
	
	public OrdenRetiro( LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas)throws DataNullException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		
		if(pedido==null) {
			throw new DataNullException("La orden de pedido no puede ser null");
		}
		
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
	}
	



	public OrdenRetiro( LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas,Voluntario voluntario) throws DataNullException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		if(pedido==null) {
			throw new DataNullException("La orden de pedido no puede ser null");
		}
		if(voluntario==null) {
			throw new DataNullException("El voluntario no puede ser null");
		}
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
		this.voluntario=voluntario;
		}
		

		public OrdenRetiro( String codigo ,String estado,LocalDate fechaEmision, Voluntario voluntario, OrdenPedido ordenPedido,ArrayList <Visita> visitas){
			
		
			super(fechaEmision,estado,tipo);
	
	
			this.pedido = ordenPedido;
			this.visitas = visitas;
			this.recolectados = new ArrayList<>();
			this.voluntario=voluntario;
	}
	


	public String getEstadoRetiro() {
		return super.getEstadoString();
	}
	/*
	public void setEstadoRetiro(String estadoRetiro) throws StateChangeException,DataNullException{
	    if (estadoRetiro == null || estadoRetiro.isEmpty()) {
	        throw new DataNullException("El campo 'estado de retiro' no puede estar vacío");
	    }
	    //si todas las condiciones son verdaderas entraria en el if
	    if (!estadoRetiro.equals("Pendiente") && !estadoRetiro.equals("En proceso") && !estadoRetiro.equals("Completada") && !estadoRetiro.equals("Cancelada")) {
	        throw new StateChangeException("El estado de retiro ingresado es invalido");
	    }
	    this.estadoRetiro = estadoRetiro;
	    //actualisa la orden y el pedido si es necesario
	    if(estadoRetiro.equals("Completada")  || estadoRetiro.equals("Cancelada")) {	    
	    	if(estadoRetiro.equals("Completada")) {
	    		super.setEstado(EstadoOrden.COMPLETADA);
	    		pedido.setEstado(EstadoOrden.COMPLETADA);
	    	}
	    	else {
	    		super.setEstado(EstadoOrden.CANCELADA);
	    		pedido.setEstado(EstadoOrden.CANCELADA);
	    	}
	    	
	    }
	} */
	
	
	public void ordenEstadoCompleta() throws StateChangeException {
		
		if(super.getEstadoString().equals(EstadoOrden.EN_PROCESO.toString()) ) {
			
			super.setEstado(EstadoOrden.COMPLETADA);
		}else {
			  throw new StateChangeException("Cambio deestado de la Orden de Retiro Invalido");
		}
		
	}
	
	public void ordenEstadoProceso() throws StateChangeException {
		
	if(super.getEstadoString().equals(EstadoOrden.PENDIENTE.toString()) ) {
			
			super.setEstado(EstadoOrden.EN_PROCESO);
		}else {
			
			  throw new StateChangeException("Cambio deestado de la Orden de Retiro Invalido");
		}
		
	}
	
	public void ordenEstadoCancelada() throws StateChangeException {
		
		
		if(!super.getEstadoString().equals(EstadoOrden.COMPLETADA.toString())) {
			
			super.setEstado(EstadoOrden.CANCELADA);
		}else {

			  throw new StateChangeException("Cambio deestado de la Orden de Retiro Invalido");
		}
		
		
	}
	public OrdenPedido getPedido() {
		return pedido;
	}
	public void setPedido(OrdenPedido pedido) {
		this.pedido = pedido;
	}
	public ArrayList<Visita> getVisitas() {
		return visitas;
	}
	public void agregarVisita(Visita visita) throws StateChangeException {
		
		
		this.visitas.add(visita);
		if(visita.getBienesRecolectados()!=null) {
			
			this.agregarBienes(visita.getBienesRecolectados());
		}
		
		
		if(visita.isEsFinal()==true ) {
			this.ordenEstadoCompleta();
		}
		
	
		
		
	}
	public void agregarBien(Bien bien) {
		this.recolectados.add(bien);
	}
	
	public void agregarBienes (ArrayList<Bien> bienes) {
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


	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}


	public void setRecolectados(ArrayList<Bien> recolectados) {
		this.recolectados = recolectados;
	}






	public void setVisitas(ArrayList<Visita> visitas) {
		this.visitas = visitas;
	}






	public static String getTipo() {
		return tipo;
	}


	public int getCantBienes() {
		return (recolectados == null) ? 0 : recolectados.size();
	}
}
