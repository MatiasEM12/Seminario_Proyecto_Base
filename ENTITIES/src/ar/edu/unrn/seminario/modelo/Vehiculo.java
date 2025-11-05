package ar.edu.unrn.seminario.modelo;

public class Vehiculo {
	private int matricula;
	private String tipo;
	private String modelo;
	private double capacidadMaxCarga;
	private boolean disponibilidad;
	private String cronogramaMantenimiento;

	public Vehiculo(int matricula, String tipo, String modelo, double capacidadMaxCarga, boolean disponibilidad,
			String cronogramaMantenimiento) {
		super();
		this.matricula = matricula;
		this.tipo = tipo;
		this.modelo = modelo;
		this.capacidadMaxCarga = capacidadMaxCarga;
		this.disponibilidad = disponibilidad;
		this.cronogramaMantenimiento = cronogramaMantenimiento;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getCapacidadMaxCarga() {
		return capacidadMaxCarga;
	}

	public void setCapacidadMaxCarga(double capacidadMaxCarga) {
		this.capacidadMaxCarga = capacidadMaxCarga;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getCronogramaMantenimiento() {
		return cronogramaMantenimiento;
	}

	public void setCronogramaMantenimiento(String cronogramaMantenimiento) {
		this.cronogramaMantenimiento = cronogramaMantenimiento;
	}

}
