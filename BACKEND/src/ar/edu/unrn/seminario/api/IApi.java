package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.*;

public interface IApi {
	
	
	  public void InicializarContadores() throws SQLException;
	//Usuario

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol,boolean activo)  throws DataEmptyException, DataObjectException, DataNullException, DataDateException, SQLException, DAOException, DataExistsException, DataLengthException ;
	
	 
	UsuarioDTO obtenerUsuario(String username) throws DAOException;

	void eliminarUsuario(String username) throws DAOException;
	
	List<UsuarioDTO> obtenerUsuarios() throws DataNullException, DAOException, DataObjectException, DataLengthException; // recuperar todos los usuarios

	void activarUsuario(String username) throws StateChangeException, DAOException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username)  throws StateChangeException, DAOException; // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	public Boolean existeUsuario(String username ) throws DAOException;
	
	
	//Rol
	List<RolDTO> obtenerRoles() throws StateChangeException, DAOException, DataNullException;

	List<RolDTO> obtenerRolesActivos() throws StateChangeException, DataNullException, DAOException;

	void guardarRol(Integer codigo,String nombre, String descripcion, boolean estado) throws DataNullException, DAOException; // crear el objeto de dominio  Rol


	RolDTO obtenerRolPorCodigo(Integer codigo) throws DAOException; // recuperar el rol almacenado

	void activarRol(Integer codigo) throws StateChangeException, DAOException; // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo) throws StateChangeException, DAOException; // recuperar el objeto Rol, imp

	
	void guardarRol(RolDTO rol) throws DataNullException, DAOException;

	
	// API
	void modificarContraseña(String usuario, String passWord) throws DataNullException, DAOException, DataEmptyException, DataLengthException;
	
	public Boolean autenticar(String username, String password) throws DataNullException, DAOException;
	

	
	List<OrdenDTO> obtenerOrdenes() throws DAOException;
	
	
	//OrdenRetiro
	
	public void registrarOrdenRetiro(OrdenRetiroDTO retiro) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DataObjectException, DataListException, DataDateException, DataEmptyException, DAOException;

	ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() throws DAOException;

	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) throws DAOException;
	//public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) ;
	
	//OrdenPedido 

	ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() throws DAOException;

	ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro) throws DataNullException, DataLengthException, DAOException, DataDateException, DataEmptyException, DataListException;
	
	//Donante
	public void registrarDonante(Donante donante) throws DAOException;
	List<DonanteDTO> obtenerDonantes(String userSolicitante);
	List<UsuarioDTO> obtenerUserDonantes() throws DataNullException, DAOException, DataObjectException, DataLengthException;

	//Voluntario 
	public void registrarVoluntario(Voluntario voluntario) throws DAOException;
	List<VoluntarioDTO> obtenerVoluntarios() throws DAOException;
	List<UsuarioDTO> obtenerUserVoluntarios() throws DataNullException, DAOException, DataObjectException, DataLengthException;
	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro);
	//Administrador 
	List<UsuarioDTO> obtenerUserAdministrador() throws DataNullException, DAOException, DataObjectException, DataLengthException;
	
	//Donacion
	public void registrarDonacion(DonacionDTO donacion) throws DataNullException, DataDoubleException, DataEmptyException, DataObjectException, DataDateException, DAOException, StateChangeException, DataLengthException, DataListException;
	ArrayList<DonacionDTO> obtenerDonaciones() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;
	
	
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;
	
	public DonacionDTO obtenerDonacionPorPedido(String ordenP) throws DataNullException, DAOException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException;
	
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) throws DataNullException, DAOException;
	public BienDTO obtenerBien (String codigo) throws DataNullException, DAOException;
	public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) throws DAOException, DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException ;
	
	public void inicializarOrdenesRetiro(String codPedido) throws DataNullException, DAOException, DataObjectException, DataListException, DataDateException, DataEmptyException;

	ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;

	void guardarRol(Integer codigo, String descripcion, boolean estado) throws DataNullException, DAOException;

	void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) throws DataEmptyException, SQLException, DAOException, DataExistsException, DataNullException, DataObjectException, DataLengthException;

	//Visitas
	public void registrarVisita(Visita visita) throws DAOException, DataNullException, DataLengthException;
	public void cargarVisita(VisitaDTO visita) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException, DataDateException, DataEmptyException, DataListException, DataObjectException;
	public void registrarOrdenPedido(OrdenPedido orden)throws DataNullException, DAOException, DataObjectException ;
	public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException ;

	public String obtenerEstadoOrdenPedido(String codOrdenPedido) ;


	void completarOrdenRetiro(String codOrdenRetiro) throws Exception;

    public DonacionDTO obtenerDonacionDTO(String codPedido) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;

	public void registrarUbicacion(Ubicacion ubicacion) throws DAOException;


	List<DonanteDTO> obtenerDonantes() throws DAOException;
	 public  BienDTO toBienDTO(Bien bien);


	 
	 
	 	public List<BienDTO> obtenerBienesInventario() throws DAOException;
		
		public List<BienDTO> obtenerBienesTipoInventario(String tipo) throws DataNullException, DAOException;
		
		public List<BienDTO> obtenerBienesDisponiblesInventario() throws DataNullException, DAOException;
		
		public List<BienDTO> obtenerBienesNoDisponiblesInventario() throws DataNullException, DAOException;
		
		public void eliminarBienInventario(String codBien) throws DAOException ;
		
		public void registrarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException;
		
		public void modificarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException;
		
		
	
	
	public void registrarBien(BienDTO bien,Boolean cargarEnInventario) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException;
	
	public void modificarBien(BienDTO bien) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException;
	
	public void eliminarBien(BienDTO bien) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException;
	
	
	public OrdenEntregaDTO obtenerOrdenEntrega(String codEntrega) throws DAOException;

	public String obtenerUsernameVoluntario(String codVoluntario) throws DAOException;

	public List<BienDTO> obtenerBienesPorOrdenEntrega(String codigo) throws DAOException;
	
	
}
