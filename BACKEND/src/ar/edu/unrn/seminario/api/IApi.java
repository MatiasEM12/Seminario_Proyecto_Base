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
	void modificarContraseña(String usuario, String passWord);
	
	public Boolean autenticar(String username, String password);
	

	
	List<OrdenDTO> obtenerOrdenes();
	
	
	//OrdenRetiro
	
	public void registrarOrdenRetiro(OrdenRetiro orden) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException;

	ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro();

	
	//public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) ;
	
	//OrdenPedido 
	public void registrarOrdenPedido(OrdenPedidoDTO ordenPedido);
	ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido();

	//ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro);
	
	//Donante
	public void registrarDonante(Donante donante);
	List<DonanteDTO> obtenerDonantes(String userSolicitante);
	List<UsuarioDTO> obtenerUserDonantes();

	//Voluntario 
	public void registrarVoluntario(Voluntario voluntario);
	List<VoluntarioDTO> obtenerVoluntarios();
	List<UsuarioDTO> obtenerUserVoluntarios();
	//Administrador 
	List<UsuarioDTO> obtenerUserAdministrador();
	
	//Donacion
	public void registrarDonacion(Donacion donacion);
	ArrayList<DonacionDTO> obtenerDonaciones();
	
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita);
	public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) ;
	
	public void inicializarOrdenesRetiro(String codPedido) throws DataNullException;

	ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP);

	void guardarRol(Integer codigo, String descripcion, boolean estado) throws DataNullException;


	void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) throws DataEmptyException;
	
	//Visitas
	public void registrarVisita(Visita visita);
	public void registrarVisita(VisitaDTO visita);




	
	
}
