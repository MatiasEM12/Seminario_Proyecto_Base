package ar.edu.unrn.seminario.modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;
public class OrdenEntrega extends Orden{
	
	private static int contadorEntrega = 0;
	
	public static String tipo="ORDEN_ENTREGA";
	private String codigo=null;
	private LocalDateTime fechaHoraProgramada;
	
	private boolean entregaEstado = true;
	//esto podriamos usuarlo para saber si ya esta definida para una ruta o usar directamente la fecha, si es null es que no esta en marcha
	private boolean confimacionRecepcion=false;
	private Bien Entrega[];
	private Beneficiario beneficiario;

	public OrdenEntrega(Bien Entrega[],Beneficiario beneficiario, LocalDate fechaEmision)throws DataNullException, DataDateException, DataEmptyException, DataObjectException, DataListException {
				super(fechaEmision, EstadoOrden.PENDIENTE,tipo);
				
				
	    this.validarListBien(Entrega);
		this.validarObjectNull(beneficiario);
		
		if(codigo==null) {
			crearCodigo();
		}
		for (int i=0;i<Entrega.length;i++) {
		    this.Entrega[i]=Entrega[i];
		}
		this.beneficiario=beneficiario;
	}
	
	
	private void setFecha(LocalDateTime fechaHoraProgramada) throws DataDateException {
		this.validarDateTime(fechaHoraProgramada);
		this.validarDateTimeProgramacion(fechaHoraProgramada);
		this.fechaHoraProgramada=fechaHoraProgramada;	
	}
	
	
	private void cambiarConfirmacion() throws DataEmptyException {
		
		if(confimacionRecepcion==true) {
			confimacionRecepcion=false;
		}else {
			throw new DataEmptyException("la recepcion ya ah sido conformada");
		}
	}
	
	private String getCodigo() {
		return codigo;
	}
	private LocalDateTime getFechaHoraProgramada() {
		return fechaHoraProgramada;
	}
	private boolean getEntregaEstado() {
		return entregaEstado;
	}
	private boolean getConfimacionRecepcion() {
		return confimacionRecepcion;
	}
	private Bien[] getEntrega() {
		return Entrega;
	}
	private Beneficiario getBeneficiario() {
		return beneficiario;
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
	private void validarListBien( Bien [] bienes) throws DataListException {
		if (bienes==null) {
			throw new DataListException("Array invalido");
		}
	}

	
	private void crearCodigo() {
		contadorEntrega++;
		  this.codigo = "OE" + String.format("%05d", contadorEntrega);
	}
	
	public static void setContadorCoordenada(int contador) {
		OrdenEntrega.contadorEntrega = contador;
	}
	
	private void validarDateTime(LocalDateTime fecha) throws DataDateException {
		if (fecha==null) {
			throw new DataDateException("La fecha no puede ser nula");
		
		}
	}
	private void validarDateTimeProgramacion(LocalDateTime fecha) throws DataDateException {
		if (fecha.isBefore(LocalDateTime.now())) {
			throw new DataDateException("Fecha invalida, anterior a la actual");
		
		}
	}
}


