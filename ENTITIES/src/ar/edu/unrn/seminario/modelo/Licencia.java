package ar.edu.unrn.seminario.modelo;

import java.time.LocalDateTime;

public class Licencia {

	private String tipoVehiculo;
	private String clase;
	private LocalDateTime fechaOtorgada;
	private LocalDateTime fechaVencimiento;

	public Licencia(String tipoVehiculo, String clase, LocalDateTime fechaOtorgada, LocalDateTime fechaVencimiento) {
		super();
		this.tipoVehiculo = tipoVehiculo;
		this.clase = clase;
		this.fechaOtorgada = fechaOtorgada;
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public LocalDateTime getFechaOtorgada() {
		return fechaOtorgada;
	}

	public void setFechaOtorgada(LocalDateTime fechaOtorgada) {
		this.fechaOtorgada = fechaOtorgada;
	}

	public LocalDateTime getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

}
