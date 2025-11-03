package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.DonanteDTO;
import ar.edu.unrn.seminario.dto.OrdenDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) throws DataEmptyException {
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email, rol);
		this.usuarioDao.create(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(),
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(), null));
		}
		return dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
		}
		return rolesDTO;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDTO;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean existeUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarRol(RolDTO rol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarContraseña(String usuario, String passWord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean autenticar(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdenDTO> obtenerOrdenes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarOrdenRetiro(OrdenRetiro orden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarOrdenRetiro(OrdenRetiroDTO orden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrdenDTO> obtenerOrdenesRetiro(List<OrdenDTO> ordenes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarOrdenPedido(OrdenPedido orden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrdenDTO> obtenerOrdenesPedido(List<OrdenDTO> ordenes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarDonante(Donante donante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DonanteDTO> obtenerDonantes(String userSolicitante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioDTO> obtenerUserDonantes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioDTO> obtenerUserVoluntarios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioDTO> obtenerUserAdministrador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarDonacion(Donacion donacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DonacionDTO> obtenerDonaciones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inicializarOrdenesRetiro(String codPedido) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer rol,
			boolean activo) throws DataEmptyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarUsuario1(String username, String password, String email, String nombre, Integer codigoRol)
			throws DataEmptyException {
		// TODO Auto-generated method stub
		
	}

}
