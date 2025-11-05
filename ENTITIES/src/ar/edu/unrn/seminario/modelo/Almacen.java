package ar.edu.unrn.seminario.modelo;

public class Almacen {

	private Bien bienes[];

	public Almacen(Bien[] bienes) {
		super();
		this.bienes = bienes;
	}

	public Bien[] getBienes() {
		return bienes;
	}

	public void setBienes(Bien[] bienes) {
		this.bienes = bienes;
	}

}
