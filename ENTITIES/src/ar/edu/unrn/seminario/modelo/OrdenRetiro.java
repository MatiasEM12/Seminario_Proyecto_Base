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
			ArrayList<Visita> visitas)throws DataNullException, DataObjectException, DataListException, DataDateException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		
		this.validarObjectNull(pedido);
		this.validarListVisita(visitas);
		this.validarDate(fechaEmision);
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
	}
	



	public OrdenRetiro( LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas,Voluntario voluntario) throws DataNullException, DataObjectException, DataListException, DataDateException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
	
		this.validarObjectNull(pedido);
		this.validarListVisita(visitas);
		this.validarObjectNull(voluntario);
		this.validarDate(fechaEmision);
		
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
		this.voluntario=voluntario;
		}
		

		public OrdenRetiro( String codigo ,String estado,LocalDate fechaEmision, Voluntario voluntario, OrdenPedido ordenPedido,ArrayList <Visita> visitas) throws DataObjectException, DataListException, DataDateException{
			
		
			super(fechaEmision,estado,tipo);
	
			this.validarObjectNull(pedido);
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
	


	public String getEstadoRetiro() {
		return super.getEstadoString();
	}

	
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
	public void setPedido(OrdenPedido pedido) throws DataObjectException {
		this.validarObjectNull(pedido);
		this.pedido = pedido;
	}
	public ArrayList<Visita> getVisitas() {
		return visitas;
	}
	public void agregarVisita(Visita visita) throws StateChangeException, DataObjectException, DataListException {
		this.validarObjectNull(visita);
		
		this.visitas.add(visita);
		if(visita.getBienesRecolectados()!=null) {
			
			this.agregarBienes(visita.getBienesRecolectados());
		}
		
		
		if(visita.isEsFinal()==true ) {
			this.ordenEstadoCompleta();
		}
		
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
