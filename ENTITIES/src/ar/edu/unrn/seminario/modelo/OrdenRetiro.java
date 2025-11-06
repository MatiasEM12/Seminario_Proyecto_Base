package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import ar.edu.unrn.seminario.exception.*;


public class OrdenRetiro extends Orden{

	public static final String tipo="ORDEN_RETIRO";
	private static int contadorOrdenRetiro = 0;
	
	private String codigo;
	private String estadoRetiro;
	private OrdenPedido pedido;
	private Voluntario voluntario;
	
	private ArrayList<Visita> visitas;
	private ArrayList<Bien> recolectados;
	
	public OrdenRetiro( LocalDateTime fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas)throws DataNullException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		if(pedido==null) {
			throw new DataNullException("La orden de pedido no puede ser vacio");
		}
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
	}
	

	public OrdenRetiro(LocalDateTime fechaEmision, OrdenPedido pedido) throws DataNullException{
		super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
		if(pedido==null) {
			throw new DataNullException("La orden de pedido no puede ser vacio");
		}
		this.pedido = pedido;
		crearCodigo();
		this.visitas= new ArrayList<>();
		this.recolectados = new ArrayList<>();
	}


	public OrdenRetiro( LocalDateTime fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas,Voluntario voluntario) throws DataNullException{
		super(fechaEmision,EstadoOrden.PENDIENTE,tipo);
		if(pedido==null) {
			throw new DataNullException("La orden de pedido no puede ser vacio");
		}
		if(voluntario==null) {
			throw new DataNullException("El voluntario no puede ser vacio");
		}
		this.pedido = pedido;
		this.visitas = visitas;
		this.recolectados = new ArrayList<>();
		crearCodigo();
		this.voluntario=voluntario;
	}
	



	public String getEstadoRetiro() {
		return estadoRetiro;
	}
	public void setEstadoRetiro(String estadoRetiro) {
		this.estadoRetiro = estadoRetiro;
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
	public void agregarVisita(Visita visita) {
		this.visitas.add(visita);
	}
	public void agregarBien(Bien bien) {
		this.recolectados.add(bien);
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
