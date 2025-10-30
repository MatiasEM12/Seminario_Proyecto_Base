package ar.edu.unrn.seminario.dto;


public class DonanteDTO extends PersonaDTO{
	
	
	private String codUbicacion;
	private String username; 
	private String [] codOrdenesPedido;
	private String[]  codDonaciones; 
	
	public DonanteDTO(String nombre, String codigo, String apellido, String preferenciaContacto,String codUbicacion) {
		super(nombre, apellido, preferenciaContacto);
		this.codUbicacion=codUbicacion;
	
	}
	
	public DonanteDTO(String nombre, String apellido, String preferenciaContacto,String codUbicacion,String username, String[]codOrdenesPedido,
			String[] codDonaciones) {
		super(nombre, apellido, preferenciaContacto);
		this.codUbicacion=codUbicacion;
		this.username=username;
		this.codDonaciones=codDonaciones;
		this.codOrdenesPedido=codOrdenesPedido;
		
	}

	public String getCodUbicacion() {
		return codUbicacion;
	}

	public void setCodUbicacion(String codUbicacion) {
		this.codUbicacion = codUbicacion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getCodOrdenesPedido() {
		return codOrdenesPedido;
	}

	public void setCodOrdenesPedido(String[] codOrdenesPedido) {
		this.codOrdenesPedido = codOrdenesPedido;
	}

	public String[] getCodDonaciones() {
		return codDonaciones;
	}

	
	
	
	
}

