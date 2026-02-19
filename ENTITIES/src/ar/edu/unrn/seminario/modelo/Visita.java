package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;

import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public class Visita {
	
	private static int contadorVisita = 0;
	
	private String codigo;
	private LocalDate fechaVisita;
	private String observaciones;
	private String tipo;
	private String codOrdenRetiro;
	private String codOrdenEntrega;
	private ArrayList<Bien> bienesRecolectados;
	private boolean esFinal;
	private String estado;

	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrden,
			ArrayList<Bien> bienesRecolectados, boolean esFinal) throws DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException, StateChangeException{
		super();
		
		
		this.validarDate(fechaVisita);
		this.validarFechaVisita(fechaVisita);
		
		this.validarCampoVacio(observaciones, "observaciones");
		this.validarCampoNull(observaciones);
		this.validarLongitudCampo255(observaciones, "observaciones");
		
		
		this.validarCampoVacio(tipo, "tipo");
		this.validarCampoNull(tipo);
		
		this.validarCampoVacio(codOrden, "codOrden");
		this.validarCampoNull(codOrden);
		
		this.validarList(bienesRecolectados);
		
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		
		if(this.esOrdenEntrega(codOrden)) {
			this.codOrdenEntrega=codOrden;
		}else {
			this.codOrdenRetiro = codOrden;
		}
		
		this.bienesRecolectados = bienesRecolectados;
		this.esFinal=esFinal;
		
	
		crearCodigo();
	
	
	}
	





	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrden,
			ArrayList<Bien> bienesRecolectados,String codigo) throws DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException{
		super();
	
		this.validarDate(fechaVisita);
		this.validarFechaVisita(fechaVisita);
		
		this.validarCampoVacio(observaciones, "observaciones");
		this.validarCampoNull(observaciones);
		this.validarLongitudCampo255(observaciones, "observaciones");
		
		
		this.validarCampoVacio(tipo, "tipo");
		this.validarCampoNull(tipo);
		
		this.validarCampoVacio(codOrden, "codOrden");
		this.validarCampoNull(codOrden);
		this.validarList(bienesRecolectados);
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		if(this.esOrdenEntrega(codOrden)) {
			this.codOrdenEntrega=codOrden;
		}else {
			this.codOrdenRetiro = codOrden;
		}
		
		this.bienesRecolectados = bienesRecolectados;
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	}
	
	public Visita(LocalDate fechaVisita, String observaciones, String tipo, String codOrden,
			Bien bien,String codigo) throws DataDateException, DataEmptyException, DataLengthException, DataNullException, DataObjectException {
		super();
		
		this.validarDate(fechaVisita);
		this.validarFechaVisita(fechaVisita);
		
		this.validarCampoVacio(observaciones, "observaciones");
		this.validarCampoNull(observaciones);
		this.validarLongitudCampo255(observaciones, "observaciones");
		
		
		this.validarCampoVacio(tipo, "tipo");
		this.validarCampoNull(tipo);
		
		this.validarCampoVacio(codOrden, "codOrden");
		this.validarCampoNull(codOrden);
		this.validarObjectNull(bien);
		
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		if(this.esOrdenEntrega(codOrden)) {
			this.codOrdenEntrega=codOrden;
		}else {
			this.codOrdenRetiro = codOrden;
		}
		
		this.bienesRecolectados = new ArrayList<>();
		this.bienesRecolectados.add(bien);
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	}
	
	
	public Visita(String codigo, LocalDate fechaVisita, String observaciones, String tipo, String codOrden, ArrayList<Bien> bienesRecolectados, boolean esFinal, String estado) throws DataDateException, DataEmptyException, DataLengthException, DataNullException, DataListException {
		super();
		
		this.validarDate(fechaVisita);
		this.validarFechaVisita(fechaVisita);
		
		this.validarCampoVacio(observaciones, "observaciones");
		this.validarCampoNull(observaciones);
		this.validarLongitudCampo255(observaciones, "observaciones");
		
		
		this.validarCampoVacio(tipo, "tipo");
		this.validarCampoNull(tipo);
		
		this.validarCampoVacio(codOrden, "codOrden");
		this.validarCampoNull(codOrden);
		
		this.validarList(bienesRecolectados);
		
	
		this.fechaVisita = fechaVisita;
		this.observaciones = observaciones;
		this.tipo = tipo;
		
		this.bienesRecolectados = bienesRecolectados;
		this.esFinal = esFinal;
		this.estado = estado;
		if(this.esOrdenEntrega(codOrden)) {
			this.codOrdenEntrega=codOrden;
		}else {
			this.codOrdenRetiro = codOrden;
		}
		if(codigo==null) {
			crearCodigo();
		}else {
			this.codigo=codigo;
		}
	}






	public String getCodigo() {
		return codigo;
	}

	public LocalDate getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(LocalDate fechaVisita) throws DataDateException {
		this.validarDate(fechaVisita);
		this.validarFechaVisita(fechaVisita);
		
		this.fechaVisita = fechaVisita;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) throws DataEmptyException, DataNullException {
		this.validarCampoVacio(observaciones, "observaciones");
		this.validarCampoNull(observaciones);
		this.observaciones = observaciones;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) throws DataEmptyException, DataNullException {
		this.validarCampoVacio(tipo, "tipo");
		this.validarCampoNull(tipo);
		
		this.tipo = tipo;
	}
	public String getRetiro() {
		return codOrdenRetiro;
	}
	public void setcodOrdenRetiro(String codOrdenRetiro) throws DataEmptyException, DataNullException {

		this.validarCampoVacio(codOrdenRetiro, "codOrdenRetiro");
		this.validarCampoNull(codOrdenRetiro);
		this.codOrdenRetiro = codOrdenRetiro;
	}
	public ArrayList<Bien> getBienesRecolectados() {
		return bienesRecolectados;
	}
	public void setBienesRecolectados(ArrayList<Bien> bienesRecolectados) throws DataListException {
		this.validarList(bienesRecolectados);
		this.bienesRecolectados = bienesRecolectados;
	}
	
	public void agregarBien(Bien bien) throws DataObjectException {
		this.validarObjectNull(bien);
		this.bienesRecolectados.add(bien);
	}
	
	
	public String getEstado() {
		return estado;
	}
	
	public String getCodOrdenRetiro() {
		return codOrdenRetiro;
	}
	public String getCodOrdenEntrega() {
		return codOrdenEntrega;
	}

	public void setCodOrdenRetiro(String codOrdenRetiro) throws DataEmptyException, DataNullException {
		this.validarCampoVacio(codOrdenRetiro, "codOrdenRetiro");
		this.validarCampoNull(codOrdenRetiro);
		this.codOrdenRetiro = codOrdenRetiro;
	}

	public void setCodOrdenEntrega(String codOrden) throws DataEmptyException, DataNullException {
		this.validarCampoVacio(codOrden, "codOrdenEngrega");
		this.validarCampoNull(codOrden);
		this.codOrdenEntrega = codOrden;
	}

	public boolean isEsFinal() {
		return esFinal;
	}

	public void setEsFinal(boolean esFinal) {
		this.esFinal = esFinal;
	}

	public void completar() throws StateChangeException {
	    if (!estado.equalsIgnoreCase("En proceso")) {
	        throw new StateChangeException("La visita no puede completarse");
	    }
	    this.estado = "Completada";
	}

	public void cancelar() throws StateChangeException {
	    if (estado.equalsIgnoreCase("Completada")) {
	        throw new StateChangeException("No se puede cancelar una visita completada");
	    }
	    this.estado = "Cancelada";
	}

	public void enProceso() throws StateChangeException {
	    if (estado.equalsIgnoreCase("Cancelada")||estado.equalsIgnoreCase("Completada")) {
	        throw new StateChangeException("No se poner en proceso la visita");
	    }
	    this.estado = "En proceso";
	}

	private void validarFechaVisita(LocalDate fechaVisita) throws DataDateException {
	    if (!fechaVisita.equals(LocalDate.now())) {
	        throw new DataDateException("La fecha de visita debe ser hoy");
	    }
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
	private void validarDate(LocalDate fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	private void validarLongitudCampo255( String campo,String nombreCampo) throws DataLengthException {
		
		if (campo.length()>255 || campo.length()<4) {
			throw new DataLengthException("el campo " + nombreCampo + " tiene tener min 10 caracteres y como maximo 255 ");
		}

	}

	private void validarList( ArrayList<Bien> Bienes) throws DataListException {
		if (Bienes==null) {
			throw new DataListException("List invalida");
		}
	}
	
	
	private void crearCodigo() {
		  contadorVisita++;
		  this.codigo = "VI" + String.format("%05d", contadorVisita);
	}
	
	public static void setContadorVisita(int contador) {
		Visita.contadorVisita = contador;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	private Boolean esOrdenRetiro(String codOrden) {
		
		String primerosDos = codOrden.substring(0, 2); 
		return primerosDos.equals("OR");
	}
	private Boolean esOrdenEntrega(String codOrden) {
		
		String primerosDos = codOrden.substring(0, 2); 
		return primerosDos.equals("OE");
	}
	public boolean tieneBienes() {
	    return bienesRecolectados != null && !bienesRecolectados.isEmpty();
	}

}
