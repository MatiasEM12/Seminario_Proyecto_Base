package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.unrn.seminario.accesos.OrdenPedidoDao;
import ar.edu.unrn.seminario.accesos.OrdenRetiroDao;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.VisitaDao;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.DonanteDTO;
import ar.edu.unrn.seminario.dto.OrdenDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private OrdenRetiroDao ordenRetiroDao;
	private OrdenPedidoDao ordenPedidoDao;
	private VisitaDao visitaDao;
	private ArrayList<VisitaDao> visitasDao;
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
	public void registrarUsuario(String username, String password, String email, String nombre, Integer rol,
			boolean activo) throws DataEmptyException {
		Rol rolN = rolDao.find(rol);
		Usuario usuario = new Usuario(username, password, nombre, email, rolN,activo);
		this.usuarioDao.create(usuario);
		
	}



	@Override
	public List<UsuarioDTO> obtenerUsuarios() throws DataNullException {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(),
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado()));
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
		
		Usuario user= this.usuarioDao.find(username);
		this.usuarioDao.remove(user);
	}

	@Override
	public List<RolDTO> obtenerRoles() throws StateChangeException {
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
		}
		return rolesDTO;
	}

	@Override

	public List<RolDTO> obtenerRolesActivos() throws StateChangeException {
	    List<RolDTO> rolesActivos = new ArrayList<>();
	    for (Rol rol : rolDao.findAll()) {
	        if (rol.isActivo()) {
	            rolesActivos.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
	        }
	    }
	    return rolesActivos;
	}


	@Override
	public void guardarRol(Integer codigo, String nombre, boolean estado) throws DataNullException {
		Rol rol= new Rol(codigo,nombre,estado);
		this.rolDao.create(rol);
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
	public void guardarRol(RolDTO rol) throws DataNullException {
		Rol rolN = new Rol(rol.getCodigo(),rol.getNombre(),rol.getDescripcion(),rol.isActivo());
		this.rolDao.create(rolN);
		
		
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

	public void registrarOrdenRetiro(LocalDate fechaEmision, OrdenPedido pedido,
			ArrayList<Visita> visitas) {
		
		
	}

	public void registrarOrdenRetiro() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() {
		List<OrdenRetiro> ordenesRetiro = ordenRetiroDao.findAll();
		ArrayList<OrdenRetiroDTO> ordenesRetiroDTO = new ArrayList<>(0);
		for (OrdenRetiro ordenRetiro : ordenesRetiro) {
			ordenesRetiroDTO.add(new OrdenRetiroDTO(ordenRetiro.getFechaEmision(), ordenRetiro.getEstado(),
			null,ordenRetiro.getCodigo(),ordenRetiro.getPedido().getCodigo(),ordenRetiro.getVoluntario().getCodigo(), ordenRetiro.getCodVisitas()));
		}
		return  ordenesRetiroDTO;
	}

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
	public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) throws DataNullException {
		Rol rol= new Rol(codigo,nombre,descripcion,estado);
		this.rolDao.create(rol);
		
	}



	@Override
	public void registrarVoluntario(Voluntario voluntario) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<VoluntarioDTO> obtenerVoluntarios() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void registrarVisita(Visita visita) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void registrarVisita(VisitaDTO visita) {
		// TODO Auto-generated method stub
		
	}


}
