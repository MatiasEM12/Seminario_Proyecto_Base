package ar.edu.unrn.seminario.modelo;
import java.time.LocalDateTime;


import ar.edu.unrn.seminario.exception.DataNullException;
public class OrdenEntrega {
	
	private static int contadorEntrega = 0;
	
	private String codigo=null;
	private LocalDateTime fechaHoraProgramada=null;
	// aranca siempre como pendiente que es true
	private boolean entregaEstado = true;
	//esto podriamos usuarlo para saver si ya esta definida para una ruta o usar directamente la fecha, si es null es que no esta en marcha
	private boolean confimacionRecepcion=false;
	private Bien Entrega[];
	private Beneficiario beneficiario;
	
	public OrdenEntrega(Bien Entrega[],Beneficiario beneficiario)throws DataNullException {
		if(Entrega.length==0) {
			throw new DataNullException("no puede crearse una Entrega sin bienes");
		}
		if(beneficiario==null) {
			throw new DataNullException("no puede crearse una Entrega sin beneficiario");
		}
		if(codigo==null) {
			crearCodigo();
		}
		for (int i=0;i<Entrega.length;i++) {
		    this.Entrega[i]=Entrega[i];
		}
		this.beneficiario=beneficiario;
	}
	private void setFecha(LocalDateTime fechaHoraProgramada) {
		this.fechaHoraProgramada=fechaHoraProgramada;	
	}
	private void cambiarEstado() {
		if(entregaEstado==true) {
			entregaEstado=false;
		}
		else {
			entregaEstado=true;
		}
	}
	
	private void cambiarConfirmacion() {
		//este tengo mi duda de si no deveria ser que si ya se confirmo no se puede desconfirmar sino que solo se puede cancelar
		if(confimacionRecepcion==true) {
			confimacionRecepcion=false;
		}
		else {
			confimacionRecepcion=true;
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
	
	// lo saque de bien
	private void crearCodigo() {
		contadorEntrega++;
		  this.codigo = "E" + String.format("%05d", contadorEntrega);
	}
	}


