package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.*;

public interface IApi {
	
	
	
	//Usuario

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol,boolean activo)  throws DataEmptyException, DataObjectException, DataNullException, DataDateException ;
	
	 
	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);
	
	List<UsuarioDTO> obtenerUsuarios() throws DataNullException; // recuperar todos los usuarios

	void activarUsuario(String username) throws StateChangeException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username)  throws StateChangeException; // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	public Boolean existeUsuario(String username );
	
	
	//Rol
	List<RolDTO> obtenerRoles() throws StateChangeException;

	List<RolDTO> obtenerRolesActivos() throws StateChangeException;

	void guardarRol(Integer codigo,String nombre, String descripcion, boolean estado) throws DataNullException; // crear el objeto de dominio  Rol


	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo) throws StateChangeException; // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo) throws StateChangeException; // recuperar el objeto Rol, imp

	
	void guardarRol(RolDTO rol) throws DataNullException;

	
	// API
	void modificarContraseña(String usuario, String passWord) throws DataNullException;
	
	public Boolean autenticar(String username, String password) throws DataNullException;
	

	
	List<OrdenDTO> obtenerOrdenes();
	
	
	//OrdenRetiro
	
	public void registrarOrdenRetiro(OrdenRetiroDTO retiro) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException;

	ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro();

	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro);
	//public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) ;
	
	//OrdenPedido 

	ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido();

	ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro) throws DataNullException, DataLengthException;
	
	//Donante
	public void registrarDonante(Donante donante);
	List<DonanteDTO> obtenerDonantes(String userSolicitante);
	List<UsuarioDTO> obtenerUserDonantes() throws DataNullException;

	//Voluntario 
	public void registrarVoluntario(Voluntario voluntario);
	List<VoluntarioDTO> obtenerVoluntarios();
	List<UsuarioDTO> obtenerUserVoluntarios();
	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro);
	//Administrador 
	List<UsuarioDTO> obtenerUserAdministrador();
	
	//Donacion
	public void registrarDonacion(Donacion donacion);
	ArrayList<DonacionDTO> obtenerDonaciones() throws DataNullException;
	
	
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException;
	
	public DonacionDTO obtenerDonacion(String ordenP) throws DataNullException;
	
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita);
	public BienDTO obtenerBien (String codigo);
	public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) ;
	
	public void inicializarOrdenesRetiro(String codPedido) throws DataNullException;

	ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) throws DataNullException;

	void guardarRol(Integer codigo, String descripcion, boolean estado) throws DataNullException;


	void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) throws DataEmptyException;
	
	//Visitas
	public void registrarVisita(Visita visita);
	public void cargarVisita(VisitaDTO visita) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException;
	 public void registrarOrdenPedido(OrdenPedido orden)throws DataNullException ;
	 public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException ;

	public String obtenerEstadoOrdenPedido(String codOrdenPedido) ;


	void registrarOrdenRetiro(OrdenRetiro retiroO)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException;

	
	void completarOrdenRetiro(String codOrdenRetiro) throws Exception;


	void registrarOrdenRetiro1(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException;


	
}
