package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.*;

public interface IApi {
	
	
	
	//Usuario

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol)  throws DataEmptyException ;
	 
	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);
	
	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username) throws StateChangeException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username)  throws StateChangeException; // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	public Boolean existeUsuario(String username );
	
	
	//Rol
	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo,String nombre, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	
	void guardarRol(RolDTO rol);

	
	// API
	void modificarContraseña(String usuario, String passWord);
	
	public Boolean autenticar(String username, String password);
	
	
	
	//Orden
	
	List<OrdenDTO> obtenerOrdenes();
	
	
	//OrdenRetiro
	
	public void registrarOrdenRetiro(OrdenRetiro orden);
	public void registrarOrdenRetiro(OrdenRetiroDTO orden);
	ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro();

	
	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) ;
	
	//OrdenPedido 
	public void registrarOrdenPedido(OrdenPedido orden);
	ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido();

	ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro);
	
	//Donante
	public void registrarDonante(Donante donante);
	List<DonanteDTO> obtenerDonantes(String userSolicitante);
	List<UsuarioDTO> obtenerUserDonantes();

	//Voluntario 
	List<UsuarioDTO> obtenerUserVoluntarios();
	//Administrador 
	List<UsuarioDTO> obtenerUserAdministrador();
	
	//Donacion
	public void registrarDonacion(Donacion donacion);
	ArrayList<DonacionDTO> obtenerDonaciones();
	
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita);
	public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) ;
	
	public void inicializarOrdenesRetiro(String codPedido);

	ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP);

}