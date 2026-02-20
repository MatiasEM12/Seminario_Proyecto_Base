package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.unrn.seminario.accesos.BeneficiarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.BienDAO;
import ar.edu.unrn.seminario.accesos.BienDAOJDBC;
import ar.edu.unrn.seminario.accesos.Bien_DonacionDAO;
import ar.edu.unrn.seminario.accesos.Bien_DonacionJDBC;
import ar.edu.unrn.seminario.accesos.Bien_VisitaDAO;
import ar.edu.unrn.seminario.accesos.Bien_VisitaJDBC;
import ar.edu.unrn.seminario.accesos.CoordenadaDAO;
import ar.edu.unrn.seminario.accesos.CoordenadaDAOJDBC;
import ar.edu.unrn.seminario.accesos.DonacionDAO;
import ar.edu.unrn.seminario.accesos.DonacionDAOJDBC;
import ar.edu.unrn.seminario.accesos.DonanteDao;
import ar.edu.unrn.seminario.accesos.InventarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.OrdenEntregaDAOJDBC;
import ar.edu.unrn.seminario.accesos.DonanteDAOJDBC;
import ar.edu.unrn.seminario.accesos.OrdenPedidoDao;
import ar.edu.unrn.seminario.accesos.OrdenPedidoDAOJDBC;
import ar.edu.unrn.seminario.accesos.OrdenRetiroDao;
import ar.edu.unrn.seminario.accesos.OrdenRetiroDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.UbicacionDAO;
import ar.edu.unrn.seminario.accesos.UbicacionDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.VisitaDAOJDBC;
import ar.edu.unrn.seminario.accesos.VoluntarioDAOJDBC;
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
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataExistsException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

    private RolDAOJDBC rolDao;
    private UsuarioDAOJDBC usuarioDao;
    private OrdenRetiroDAOJDBC ordenRetiroDao;
    private OrdenPedidoDAOJDBC ordenPedidoDao;
    private VisitaDAOJDBC visitaDao;
    private BienDAO bienDao;
    private Bien_VisitaJDBC bienVisitaDao;
    private Bien_DonacionJDBC bienDonacionDao;
    private DonacionDAOJDBC donacionDao;
    private VoluntarioDAOJDBC voluntarioDao;
    private DonanteDAOJDBC donanteDao;
    private UbicacionDAOJDBC ubicacionDao;
    private CoordenadaDAOJDBC coordenadaDAO; 
    private InventarioDAOJDBC inventarioDAO;
    private BeneficiarioDAOJDBC beneficiarioDAO;
    private OrdenEntregaDAOJDBC ordenEntregaDAO;
    public PersistenceApi() {
        // inicializar DAOs JDBC
        this.rolDao = new RolDAOJDBC();
        this.usuarioDao = new UsuarioDAOJDBC();
        this.ordenPedidoDao = new OrdenPedidoDAOJDBC();
        this.ordenRetiroDao = new OrdenRetiroDAOJDBC();
        this.visitaDao = new VisitaDAOJDBC();
        this.bienDao = new BienDAOJDBC();
        this.bienVisitaDao = new Bien_VisitaJDBC();
        this.bienDonacionDao = new Bien_DonacionJDBC();
        this.donacionDao = new DonacionDAOJDBC();
        this.voluntarioDao = new VoluntarioDAOJDBC();
        this.donanteDao = new DonanteDAOJDBC();
        this.ubicacionDao=new UbicacionDAOJDBC();
        this.coordenadaDAO = new CoordenadaDAOJDBC();
        this.ubicacionDao  = new UbicacionDAOJDBC();
        this.inventarioDAO = new InventarioDAOJDBC();
        this.beneficiarioDAO= new BeneficiarioDAOJDBC();
        this.ordenEntregaDAO= new OrdenEntregaDAOJDBC();
    }
   
    //Iniciaizar
    public void InicializarContadores() throws SQLException {
    	//Beneficiario.setContadorDonante(0);
    	Bien.setContadorBien(bienDao.obtenerMaximoBienes());
    	Coordenada.setContadorCoordenada(coordenadaDAO.obtenerMaximoCoordenadas());
    	Donacion.setContadorDonacion(donacionDao.obtenerMaximoDonaciones());
    	Donante.setContadorDonante(donanteDao.obtenerMaximoDonantes());
    	//OrdenEntrega.setContadorCoordenada(0)
    	OrdenPedido.setContadorPedido(ordenPedidoDao.obtenerMaximoOrdenPedido());
    	OrdenRetiro.setContadorOrdenRetiro(ordenRetiroDao.obtenerMaximoOrdenRetiro());
    	Ubicacion.setContadorUbicacion(ubicacionDao.obtenerMaximoUbicaciones());
    	Usuario.setContadorUsuario(usuarioDao.obtenerMaximoUsuarios());
    	Voluntario.setContadorVoluntario(voluntarioDao.obtenerMaximoVoluntarios());
    	Visita.setContadorVisita(visitaDao.obtenerMaximoVisitas());
    }
    
    // --- Usuario / Rol ---
    @Override
    public void registrarUsuario(String username, String password, String contacto, String nombre, Integer codigoRol) 
    		throws SQLException, DAOException, DataEmptyException, DataNullException, DataObjectException, DataLengthException, DataExistsException{
    	
    	Usuario.setContadorUsuario(  this.usuarioDao.obtenerCantidadUsuarios());
        Rol rol = rolDao.find(codigoRol);
        Usuario usuario = new Usuario(username, password, nombre, contacto, rol,false,null);
		this.usuarioDao.create(usuario);
    }

    @Override
    public void registrarUsuario(String username, String password, String email, String nombre, Integer rol, boolean activo) 
    		throws SQLException, DAOException, DataEmptyException, DataNullException, DataObjectException, DataLengthException, DataExistsException{
    	Usuario.setContadorUsuario(  this.usuarioDao.obtenerCantidadUsuarios());
        Rol rolN = rolDao.find(rol);
        Usuario usuario = new Usuario(username, password, nombre, email, rolN, activo,null);
		this.usuarioDao.create(usuario);
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() throws DataNullException, DAOException, DataObjectException, DataLengthException {
        List<UsuarioDTO> dtos = new ArrayList<>();
        List<Usuario> usuarios = null;
		usuarios = usuarioDao.findAll();
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                dtos.add(this.toUsuarioDTO(u));
            }
        }
        return dtos;
    }

    @Override
    public UsuarioDTO obtenerUsuario(String username) throws DAOException {
        Usuario u = null;
        u = usuarioDao.find(username);
        if (u == null) return null;
        return new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getContacto(),
                u.getRol().getNombre(), u.isActivo(), u.obtenerEstado());
    }

    @Override
    public void eliminarUsuario(String username) throws DAOException {
        Usuario user = null;
		user = this.usuarioDao.find(username);
        if (user != null)
			this.usuarioDao.remove(user);
    }
    

    @Override
    public List<RolDTO> obtenerRoles() throws StateChangeException, DataNullException, DAOException {
        List<Rol> roles = null;
        roles = rolDao.findAll();
        List<RolDTO> rolesDTO = new ArrayList<>();
        for (Rol rol : roles) {
            rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
        }
        return rolesDTO;
    }

    @Override
    public List<RolDTO> obtenerRolesActivos() throws StateChangeException, DataNullException, DAOException {
        return rolDao.findAll().stream().filter(Rol::isActivo)
                .map(r -> new RolDTO(r.getCodigo(), r.getNombre(), r.isActivo())).collect(Collectors.toList());
    }

    @Override
    
    //opcion 1
    
    public void guardarRol(Integer codigo, String nombre, boolean estado) throws DataNullException, DAOException {
        Rol rol = new Rol(codigo, nombre, estado);
        this.rolDao.create(rol);
    }

    @Override
    public RolDTO obtenerRolPorCodigo(Integer codigo) throws DAOException {
        Rol rol = null;
		rol = rolDao.find(codigo);
        if (rol == null) return null;
        return new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
    }


    @Override
    public void activarRol(Integer codigo) throws StateChangeException, DAOException{
    	Rol rol = null;
		rol = rolDao.find(codigo);
        if(rol== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        rol.activar();
        rolDao.update(rol);
    }

    @Override
    //funciona
    public void desactivarRol(Integer codigo) throws StateChangeException, DAOException{
    	Rol rol = null;
		rol = rolDao.find(codigo);
        if(rol== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        rol.desactivar();
        rolDao.update(rol);
		
    }

    @Override
    public void activarUsuario(String username) throws StateChangeException, DAOException{
    	Usuario usuario = null;
		usuario = usuarioDao.find(username);
		
        if(usuario== null) {
        	throw new StateChangeException("no se pudo encontrar un usuario con ese nombre");
        }
        usuario.activar();
        usuarioDao.update(usuario);
    }

    @Override
    public void desactivarUsuario(String username) throws StateChangeException, DAOException{
    	Usuario usuario = null;
		usuario = usuarioDao.find(username);
        if(usuario== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        usuario.desactivar();
        usuarioDao.update(usuario);
    }

    @Override
    public Boolean existeUsuario(String username) throws DAOException {
        return usuarioDao.find(username) != null;
    }

    @Override
    //opcion 2
    
    public void guardarRol(RolDTO rol) throws DataNullException, DAOException {
        Rol rolN = new Rol(rol.getCodigo(), rol.getNombre(), rol.getDescripcion(), rol.isActivo());
        this.rolDao.create(rolN);
    }

    @Override
    public void modificarContraseña(String usuario, String passWord) throws DataNullException, DAOException, DataEmptyException, DataLengthException {
        if (usuario == null || usuario.trim().isEmpty()) throw new DataNullException("nombre de usuario vacío");
        if (passWord == null || passWord.trim().isEmpty()) {
            throw new DataNullException("La contraseña no puede estar vacía.");
        }
        Usuario us_contraseña = null;
		us_contraseña = usuarioDao.find(usuario);
		
        if (us_contraseña == null) throw new DataNullException("No existe un usuario con el nombre: " + usuario);
        us_contraseña.setContrasena(passWord);
        usuarioDao.update(us_contraseña);
		
    }


    @Override
    public Boolean autenticar(String username, String password) throws DataNullException, DAOException{
        if (username == null || username.trim().isEmpty()) throw new DataNullException("nombre de usuario vacío");
        if (password == null || password.trim().isEmpty()) {
            throw new DataNullException("La contraseña no puede estar vacía.");
        }
        Usuario us_autentificado = null;
		us_autentificado = usuarioDao.find(username);
		
        if (us_autentificado == null) throw new DataNullException("No existe un usuario con el username: " + username);
        return us_autentificado.getContrasena().equals(password);
    }        // pendiente: delegar a usuarioDao.autenticar si existe

    // --- Órdenes ---
    @Override

    public List<OrdenDTO> obtenerOrdenes() throws DAOException {

        List<OrdenDTO> resultado = new ArrayList<>();

        // ORDENES PEDIDO
        List<OrdenPedido> pedidos = ordenPedidoDao.findAll();
        if (pedidos != null) {
            for (OrdenPedido op : pedidos) {
                resultado.add(toOrdenPedidoDTO(op));
            }
        }

        // ORDENES RETIRO
        List<OrdenRetiro> retiros = ordenRetiroDao.findAll();
        if (retiros != null) {
            for (OrdenRetiro or : retiros) {
                resultado.add(toOrdenRetiroDTO(or));
            }
        }

        return resultado;
    }
    private OrdenPedidoDTO toOrdenPedidoDTO(OrdenPedido op) {

        if (op == null) return null;

        return new OrdenPedidoDTO(
            op.getFechaEmision(),
            op.getEstado().toString(),
            op.getCodigo(),
            op.isCargaPesada(),
            op.getObservaciones(),
            op.getCodDonacion()
        );
    }


    @Override
    public ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() throws DAOException {

        List<OrdenPedido> ordenes = ordenPedidoDao.findAll();
        ArrayList<OrdenPedidoDTO> dtos = new ArrayList<>();

        if (ordenes == null) return dtos;

        for (OrdenPedido orden : ordenes) {
            dtos.add(new OrdenPedidoDTO(
                orden.getFechaEmision(),
                orden.getEstado().toString(),
                orden.getCodigo(),
                orden.isCargaPesada(),
                orden.getObservaciones(),
                orden.getCodDonacion()
            ));
        }
        return dtos;
    }


    @Override
    public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() throws DAOException {

        List<OrdenRetiro> ordenes = ordenRetiroDao.findAll();
        ArrayList<OrdenRetiroDTO> dtos = new ArrayList<>();

        if (ordenes == null) return dtos;

        for (OrdenRetiro orden : ordenes) {
            OrdenRetiroDTO dto = toOrdenRetiroDTO(orden);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private OrdenRetiroDTO toOrdenRetiroDTO(OrdenRetiro or) {

        if (or == null) return null;

        String codPedido = (or.getPedido() != null)
                ? or.getPedido().getCodigo()
                : null;

        String codVoluntario = (or.getVoluntario() != null)
                ? or.getVoluntario().getCodigo()
                : null;

        String[] codVisitas = or.getCodVisitas(); // ya devuelve String[]

        return new OrdenRetiroDTO(
            or.getFechaEmision(),
            or.getEstado().toString(),
            or.getCodigo(),
            codPedido,
            codVoluntario,
            codVisitas
        );
    }


    @Override
    public void inicializarOrdenesRetiro(String codPedido) throws DataNullException, DAOException, DataObjectException, DataListException, DataDateException, DataEmptyException {
        if (codPedido == null || codPedido.trim().isEmpty()) throw new DataNullException("Código pedido vacío");
        OrdenPedido pedido = null;
		pedido = ordenPedidoDao.find(codPedido);
        if (pedido == null) throw new DataNullException("No existe OrdenPedido con código: " + codPedido);
   
        String codigoRetiro = "OR_" + codPedido;
        OrdenRetiro orden = new OrdenRetiro(codigoRetiro, "PENDIENTE", LocalDate.now(), null, pedido, new ArrayList<Visita>());
        ordenRetiroDao.create(orden);
    }

    // --- Donaciones / Donantes ---
	    @Override
	    public void registrarDonante(Donante donante) throws DAOException {
	        if (donante == null) return;
	        donanteDao.create(donante);
	    }

    @Override
    public List<DonanteDTO> obtenerDonantes(String userSolicitante) {
     
      
        return null;
    }
    
    @Override
    public List<DonanteDTO> obtenerDonantes() throws DAOException {
     
        List<Donante> list = donanteDao.findAll();
        if (list == null) return new ArrayList<>();
        return list.stream().filter(Objects::nonNull).map(d -> new DonanteDTO(d.getNombre(), d.getCodigo(), d.getApellido(), d.getContacto(), null,
                d.getUbicacion() != null ? d.getUbicacion().getCodigo() : null, null)).filter(Objects::nonNull).collect(Collectors.toList());
    }


    @Override
    public List<UsuarioDTO> obtenerUserDonantes() throws DataNullException, DAOException, DataObjectException, DataLengthException {
       
    	 List<Usuario> donantes = this.usuarioDao.findAll();
		
         donantes=donantes.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Donante")).collect(Collectors.toList());
          List<UsuarioDTO>donantesDTO= donantes.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
        		  
        		return donantesDTO;
    }
    
    
    
    private UsuarioDTO toUsuarioDTO(Usuario user) {
    	
    	UsuarioDTO userDTO = new UsuarioDTO (user.getUsuario(),user.getContrasena(),user.getNombre(),user.getContacto(),user.getRolName(),user.isActivo(),user.getCodigo(),user.getEstado());
    	

    	return userDTO;
    }

    @Override
    public List<UsuarioDTO> obtenerUserVoluntarios() throws DataNullException, DAOException, DataObjectException, DataLengthException {
           
          List<Usuario> voluntarios = this.usuarioDao.findAll();
		
         voluntarios=voluntarios.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Voluntario")).collect(Collectors.toList());
          
          List<UsuarioDTO>voluntariosDTO= voluntarios.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
        		  
        		return voluntariosDTO;
        
    }

    @Override
    public List<UsuarioDTO> obtenerUserAdministrador() throws DataNullException, DAOException, DataObjectException, DataLengthException {
    	  List<Usuario> administradores = this.usuarioDao.findAll();
		
          administradores=administradores.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Admin")).collect(Collectors.toList());
           List<UsuarioDTO>administradoresDTO= administradores.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
         		  
         		return administradoresDTO;
         
    }

    @Override
    public void registrarDonacion(DonacionDTO don) throws DataNullException, DataDoubleException, DataEmptyException, DataObjectException, DataDateException, DAOException, StateChangeException, DataLengthException, DataListException {
    	
    	
    	 if (don == null) return;      
    	    Donacion donacion = this.toDonacion(don);   
    	    donacionDao.create(donacion);
    	    this.cargarBienesDonacion(don.getBienes());
    	    this.crearBienDonacion(donacion);
    }
    
    
    private Donacion toDonacion(DonacionDTO dto)
            throws DataNullException, DataDoubleException, DataEmptyException,
                   DataObjectException, DataDateException, DAOException, StateChangeException, DataLengthException, DataListException {

        if (dto == null) {
            throw new DataNullException("DonacionDTO es null");
        }

        // Donante obligatorio
        Donante donante = donanteDao.find(dto.getCodDonante());
        if (donante == null) {
            throw new DataNullException("No se encontró Donante con código: " + dto.getCodDonante());
        }

        // Pedido opcional
        OrdenPedido pedido = null;
        if (dto.getCodPedido() != null && !dto.getCodPedido().trim().isEmpty()) {
            pedido = ordenPedidoDao.find(dto.getCodPedido());
            if (pedido == null) {
                throw new DataNullException("No se encontró OrdenPedido con código: " + dto.getCodPedido());
            }
        }

        // Construir entidad Donacion
        return new Donacion(
                dto.getFechaDonacion(),
                dto.getObservacion(),
                this.listBien(dto.getBienes()),
                donante,
                pedido,           // puede ser null
                dto.getCodigo()
        );
    }



    @Override
    public ArrayList<DonacionDTO> obtenerDonaciones() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException {
        List<Donacion> list = donacionDao.findAll();
        ArrayList<DonacionDTO> res = new ArrayList<>();
        if (list == null) return res;
        for (Donacion d : list) {
            res.add(toDonacionDTO (d));
        }
        return res;
    }

    // --- Bienes ---
    @Override
    public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) throws DataNullException, DAOException {
        ArrayList<BienDTO> resultado = new ArrayList<>();
        if (codVisita == null || codVisita.trim().isEmpty()) return resultado;
        ArrayList<Bien> bienes = bienDao.findBienVisita(codVisita);
		
        if (bienes == null) return resultado;
        for (Bien b : bienes) {
            resultado.add(toBienDTO(b));
        }
        return resultado;
    }

    @Override
    public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) throws DAOException, DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException {
      
    	
    	List<BienDTO> resultado = new ArrayList<>();

        if (codOrdenRetiro == null || codOrdenRetiro.isBlank()) {
            return resultado;
        }

        // 1️⃣ Traer visitas del retiro
        ArrayList<Visita> visitas =
            visitaDao.findAllOrdenRetiro(codOrdenRetiro);

        // 2️⃣ Recorrer bienes de cada visita
        for (Visita v : visitas) {
            if (v.getBienesRecolectados() != null) {
                for (Bien b : v.getBienesRecolectados()) {
                    resultado.add(toBienDTO(b));
                }
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException  {
        ArrayList<BienDTO> retirar = new ArrayList<>();
        if (codOP == null || codOP.trim().isEmpty()) return retirar;
        List<Donacion> donaciones = donacionDao.findAll();
        if (donaciones == null) return retirar;
        for (Donacion don : donaciones) {
            if (don != null && don.getPedido() != null && codOP.equalsIgnoreCase(don.getPedido().getCodigo())) {
                if (don.getBienes() != null) for (Bien b : don.getBienes()) retirar.add(toBienDTO(b));
            }
        }
        return retirar;
    }
    
    
    

    // --- helpers DTO ---
  public  BienDTO toBienDTO(Bien bien) {
        if (bien == null) return null;

        return new BienDTO(
            bien.getCodigo(),               // String codigo
            bien.getTipo(),                 // String tipo
            bien.getPeso(),                 // double peso
            bien.getNombre(),               // String nombre
            bien.getDescripcion(),          // String descripcion
            bien.getNivelNecesidad(),       // int nivelNecesidad
            bien.getFechaVencimiento(),     // LocalDate fechaVencimiento
            bien.getTalle(),                // Double talle
            bien.getMaterial()              // String material
        );
    }
    
    
    public String obtenerEstadoOrdenPedido(String codOrdenPedido) {
        if (codOrdenPedido == null || codOrdenPedido.trim().isEmpty()) {
            return null;
        }

        OrdenPedido orden = null;
        try {
            orden = ordenPedidoDao.find(codOrdenPedido);
        } catch (Exception e) {
            // Si el DAO lanza error (por ejemplo no existe el código)
            return null;
        }

        if (orden == null) {
            return null;
        }

        // Devuelve el estado como texto, consistente con los DTO
        if (orden.getEstado() != null) {
            return orden.getEstado().toString();
        }

        // Si no tiene estado asignado, devolvemos "Pendiente" como valor por defecto
        return "Pendiente";

    }
    private DonanteDTO toDonanteDTO(Donante donante) {
        if (donante == null) return null;
        return new DonanteDTO(donante.getNombre(), donante.getCodigo(), donante.getApellido(), donante.getContacto(),
                null, donante.getUbicacion() != null ? donante.getUbicacion().getCodigo() : null, null);
    }
    @Override
    public void registrarVoluntario(Voluntario voluntario) throws DAOException {
        voluntarioDao.create(voluntario);
    }
    @Override
    public List<VoluntarioDTO> obtenerVoluntarios() throws DAOException {
    	List<Voluntario> voluntarios= this.voluntarioDao.findAll();
    	
    	 return voluntarios.stream()
    	            .map(this::toVoluntarioDTO)
    	            .collect(Collectors.toList());
    }

    @Override
   
    public void registrarVisita(Visita visita) throws DAOException, DataNullException, DataLengthException {
        visitaDao.create(visita);
    }   
    
    public void registrarOrdenPedido(OrdenPedido orden) throws DAOException, DataObjectException {
    	ordenPedidoDao.create(orden);
    	this.actualizarDonacionConPedido(this.obtenerDonacion(orden.getCodDonacion()), orden);
    }
    public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException{
    	if (orden==null) {
    		throw new DataNullException("la orden de pedido invalida");
    	}
    	try {
    		
    		OrdenPedido pedido= new OrdenPedido(
	                orden.getCodigo(),
	                orden.getFechaEmision(),
	                orden.isCargaPesada(), 
	                orden.getObservaciones(),
	                orden.getCodDonacion()
    				);
            ordenPedidoDao.create(pedido);
            actualizarDonacionConPedido(this.obtenerDonacion(orden.getCodDonacion()), pedido);
    	}catch(Exception e) {
    		System.err.println("Error al registrar la orden de pedido: " + e.getMessage());
            e.printStackTrace();
    	}
    }

    
    public void actualizarDonacionConPedido(Donacion donacion, OrdenPedido pedido)
            throws DataObjectException {

        if (donacion == null) {
            throw new DataObjectException("No se encontró la donación para asociar el pedido");
        }

        donacion.setPedido(pedido);
        this.donacionDao.update(donacion);
    }

   
   public Donacion obtenerDonacion(String codDonacion) throws DataObjectException {
	   Donacion donacion = this.donacionDao.find(codDonacion);

	    if (donacion == null) {
	        throw new DataObjectException("Donación inexistente: " + codDonacion);
	    }
	    return donacion;
   }
   


    @Override
    public void cargarVisita(VisitaDTO visitaDTO) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException, DataDateException, DataEmptyException, DataListException, DataObjectException {
        
    	  	Visita visita = toVisita(visitaDTO);
    	  	OrdenRetiro oR = ordenRetiroDao.find(visita.getCodOrdenRetiro());
    	    if (oR.getCodigo()!= null) {
    	        
    	        ArrayList<BienDTO> bienesDTO=this.obtenerBienesPorOrdenPedido(oR.getPedido().getCodigo());
    	        oR.setBienesEsperados(this.listBien(bienesDTO));
    	        

    	    
    	        oR.agregarVisita(visita);
    	        
    	        
    	        visitaDao.create(visita);
        	    crearBienVisita(visita);
    	        ordenRetiroDao.update(oR);
    	        ordenPedidoDao.update(oR.getPedido());
    	        

    	       
    	    } else {
    	        OrdenEntrega oE = this.ordenEntregaDAO.find(visita.getCodOrdenEntrega());
    	        oE.agregarVisita(visita);
    	        this.ordenEntregaDAO.update(oE);
    	    }
    	

	        if (visita.tieneBienes()) {
	            for (Bien b : oR.getRecolectados()) {
	                registrarBienInventario(b.getCodigo(), b.getTipo(), true);
	            }
	        }
    	    
    }
  
    private void crearBienDonacion(Donacion donacion) throws DAOException {
    	
    	ArrayList<Bien> bienes= donacion.getBienes();
    	
    	for(Bien b :  bienes) {
    		this.bienDonacionDao.create(b.getCodigo(), donacion.getCodigo());
    	}
    	
    }
    
    private void crearBienVisita(Visita visita) throws DAOException {
    	
    	ArrayList<Bien> bienes= visita.getBienesRecolectados();
    	
    	for(Bien b :  bienes) {
    		this.bienVisitaDao.create(b.getCodigo(), visita.getCodigo());
    	}
    	
    }
    private Visita toVisita(VisitaDTO dto)
            throws DataNullException, DataLengthException, DataDateException,
                   DataEmptyException, DataListException, DataDoubleException, StateChangeException, DataObjectException {

    	Visita visita= new Visita(
    		dto.getCodigo(),
            dto.getFechaVisita(),
            dto.getObservaciones(),
            dto.getTipo(),
            dto.getCodOrden(),
            toBienesList(dto.getBienesRecolectados()),
            dto.isEsFinal(),dto.getEstado()
        );
    	return visita;
    }

    
    private ArrayList<Bien> toBienesList(ArrayList<BienDTO> bienesDTO) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException{
    	
    	ArrayList<Bien>bienes= new ArrayList<>();
    	
    	for(BienDTO dt : bienesDTO) {
    		
    		Bien bien = new Bien( dt.getCodigo(),dt.getTipo(),dt.getPeso(),dt.getNombre(),dt.getDescripcion(),dt.getFechaVencimiento(),dt.getTalle(),dt.getMaterial()  );
    		bienes.add(bien);
    	}
    	
    	return bienes;
    }
    
    public void registrarUbicacion(Ubicacion ubicacion) throws DAOException {
        if (ubicacion == null) return;
        coordenadaDAO.create(ubicacion.getCoordenada());
		// crea o ignora si ya existe
        ubicacionDao.create(ubicacion);
    }


    //funciona es el unico guardado rol que entra porque aunque vos nunca toques la descripcion lo toma como que le invias un dato	@Override
	public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) throws DataNullException, DAOException {
        Rol rol = new Rol(codigo, nombre, descripcion, estado);
        this.rolDao.create(rol);
		
		
		
	}

	@Override
	public ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}




	private OrdenPedido toOrdenPedido(OrdenPedidoDTO dto) {
	    if (dto == null) return null;

	    try {
	        // Buscar el donante por código si existe
	        Donante donante = null;
	       
//LocalDate fechaEmision, boolean cargaPesada,String observaciones, String codDonante, String codDonacion
	        // Crear el objeto del modelo
	        OrdenPedido pedido = new OrdenPedido(dto.getFechaEmision(),dto.isCargaPesada(),dto.getObservaciones(),dto.getCodDonacion() );

	        return pedido;
	    } catch (Exception e) {
	        System.err.println("Error convirtiendo OrdenPedidoDTO a modelo: " + e.getMessage());
	        return null;
	    }
	}

	@Override
	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) throws DAOException {
	      OrdenRetiro orden = this.ordenRetiroDao.find(codOrdenRetiro);
	      
	     
	      return   toOrdenRetiroDTO(orden);
	}

	@Override
	public DonacionDTO obtenerDonacionPorPedido(String ordenP) throws DataNullException, DAOException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException {
	
		Donacion donacion = this.donacionDao.findPorOrdenPedido(ordenP);
		
		
		return toDonacionDTO(donacion);
	}
	
	private DonacionDTO toDonacionDTO ( Donacion donacion) {
		
		  if (donacion == null) return null;

		    String codDonante = null;
		    String codPedido = null;

		    if (donacion.getDonante() != null) {
		        codDonante = donacion.getDonante().getCodigo();
		    } else {
		        // opcional: loguear para depuración
		        System.err.println("Aviso: Donacion " + donacion.getCodigo() + " sin Donante asociado.");
		    }

		    if (donacion.getPedido() != null) {
		        codPedido = donacion.getPedido().getCodigo();
		    } else {
		        // opcional: loguear
		        System.err.println("Aviso: Donacion " + donacion.getCodigo() + " sin OrdenPedido asociado.");
		    }

		    ArrayList<BienDTO> bienesDto = donacion.getBienes() != null ? listBienDTO(donacion.getBienes()) : new ArrayList<>();

		    return new DonacionDTO(
		            donacion.getCodigo(),
		            donacion.getFechaDonacion(),
		            donacion.getObservacion(),
		            bienesDto,
		            codDonante,
		            codPedido
		    );
	}
	
	private ArrayList<BienDTO> listBienDTO(List<Bien> bienes) {

	    ArrayList<BienDTO> dtos = new ArrayList<>();

	    for (Bien b : bienes) {
	        dtos.add(toBienDTO(b));
	    }

	    return dtos;
	}

	private ArrayList<Bien> listBien(List<BienDTO> bienesDTO)
	        throws DataNullException, DataDoubleException,
	               StateChangeException, DataLengthException, DataDateException {

	    ArrayList<Bien> bienes = new ArrayList<>();

	    for (BienDTO dt : bienesDTO) {
	        bienes.add(this.toBien(dt));
	    }

	    return bienes;
	}

	
	private Bien toBien(BienDTO bien) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException {
		
		
		 return new Bien(
	            bien.getCodigo(),               // String codigo
	            bien.getTipo(),                 // String tipo
	            bien.getPeso(),                 // double peso
	            bien.getNombre(),               // String nombre
	            bien.getDescripcion(),          // String descripcion
	            bien.getFechaVencimiento(),     // LocalDate fechaVencimiento
	            bien.getTalle(),                // Double talle
	            bien.getMaterial()              // String material
	        );
	}

	@Override
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException {
		
		List<Donacion> donaciones = this.donacionDao.findAllPendiente();
		
		
		return this.toDonacionDTO(donaciones) ;
	}
	
	private ArrayList<DonacionDTO> toDonacionDTO(List <Donacion> donaciones){
		
		ArrayList<DonacionDTO>dtos= new ArrayList<>();
		
		for (Donacion e : donaciones) {
			
			DonacionDTO dto = this.toDonacionDTO(e);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@Override
	public void completarOrdenRetiro(String codOrdenRetiro) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BienDTO obtenerBien(String codigo) throws DataNullException, DAOException {
		Bien bien = this.bienDao.find(codigo);
		return this.toBienDTO(bien);
	}
	
	





	
	public Bien ObtenerBien(String codigo) throws DataNullException, DAOException {
		return this.bienDao.find(codigo);
	}


	@Override
	public void registrarOrdenRetiro(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException,
			DataObjectException, DataListException, DataDateException, DataEmptyException {
		 // validaciones básicas
        if (retiro == null) {
            throw new DataNullException("OrdenRetiro DTO es nula");
        }

        // Buscar voluntario (puede ser null si no se asignó)
        Voluntario v = null;
        if (retiro.getCodVoluntario() != null && !retiro.getCodVoluntario().trim().isEmpty()) {
            v = voluntarioDao.find(retiro.getCodVoluntario());
           
            if (v == null) throw new DataNullException("Voluntario no encontrado: " + retiro.getCodVoluntario());
        }

        // Buscar pedido (obligatorio)
        if (retiro.getPedido() == null || retiro.getPedido().trim().isEmpty()) {
            throw new DataNullException("La orden retiro debe referenciar a una orden de pedido");
        }
        OrdenPedido pedido = null;
		pedido = ordenPedidoDao.find(retiro.getPedido());
        if (pedido == null) {
            throw new DataNullException("No existe la OrdenPedido: " + retiro.getPedido());
        }

        // Construir lista de Visitas a partir de los códigos (si vienen)
        ArrayList<Visita> visitas = new ArrayList<>();
        String[] codVisitasArr = retiro.getCodVisitas(); // OrdenRetiroDTO tiene String[] getCodVisitas()
        if (codVisitasArr != null) {
            for (String codVis : codVisitasArr) {
                if (codVis == null || codVis.trim().isEmpty()) continue;
                Visita vFound = null;
				vFound = visitaDao.find(codVis);
				// requiere que visitaDao tenga find(String)
                if (vFound != null) {
                    visitas.add(vFound);
                }
       
            }
        }

        // Estado: si DTO trae null, poner PENDIENTE por defecto (string)
        String estado = Orden.EstadoOrden.PENDIENTE.toString();
        if (estado == null || estado.trim().isEmpty()) {
            estado = "PENDIENTE";
        }

        // constructor que acepta (String codigo, String estado, LocalDate fechaEmision, Voluntario voluntario, OrdenPedido ordenPedido, ArrayList<Visita> visitas)
        OrdenRetiro orden = new OrdenRetiro(
                retiro.getCodigo(),       
                estado,
                retiro.getFechaEmision(),
                v,
                pedido,
                visitas
        );

        // Persistir
        ordenRetiroDao.create(orden);
        ordenPedidoDao.update(orden.getPedido());
	}

	public  VoluntarioDTO toVoluntarioDTO(Voluntario v) {
      
		if (v == null) return null;

        return new VoluntarioDTO(
            v.getNombre(),
            v.getApellido(),
            v.getContacto(),
            v.getDni(),
            v.getFecha_nac(),
            v.getCodigo(),
            v.getTarea(),
            v.isDisponible(),
            v.getUsername(),
            null
        );
    }
	
	
	//INVENTARIO

	@Override
	public List<BienDTO> obtenerBienesInventario() throws DAOException {
		
		List<Bien> bienes= this.inventarioDAO.findAll();
		ArrayList<BienDTO> bienesDTO= this.listBienDTO(bienes);
		return bienesDTO;
	}

	@Override
	public List<BienDTO> obtenerBienesTipoInventario(String tipo) throws DataNullException, DAOException {
		
		List<Bien> bienes= this.inventarioDAO.findAll();
		
		List<Bien> filtrados = bienes.stream()
		        .filter(b -> tipo.equalsIgnoreCase(b.getTipo()))
		        .collect(Collectors.toList());

		ArrayList<BienDTO> bienesDTO= this.listBienDTO(filtrados);
		return bienesDTO;
	}

	@Override
	public List<BienDTO> obtenerBienesDisponiblesInventario() throws DataNullException, DAOException {
		
		List<Bien> bienes= this.inventarioDAO.findBienesDisponibles();
		
	
		ArrayList<BienDTO> bienesDTO= this.listBienDTO(bienes);
		return bienesDTO;
	}

	@Override
	public List<BienDTO> obtenerBienesNoDisponiblesInventario() throws DataNullException, DAOException {
		List<Bien> bienes= this.inventarioDAO.findBienesNoDisponibles();
		ArrayList<BienDTO> bienesDTO= this.listBienDTO(bienes);
		return bienesDTO;
	}

	@Override
	public void eliminarBienInventario(String codBien) throws DAOException {

		this.inventarioDAO.remove(codBien);
		
	}

	@Override
	public void registrarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException {
		this.inventarioDAO.create(codBien, tipoBien, disponible);
		
	}

	@Override
	public void modificarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException {
		this.inventarioDAO.update(codBien, tipoBien, disponible);
		
	}

	@Override
	public void registrarBien(BienDTO bien, Boolean cargarEnInventario) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {
		Bien bienNuevo=null;
		bienNuevo= this.toBien(bien);
		this.bienDao.create(bienNuevo);
		
		if(cargarEnInventario==true) {
			this.registrarBienInventario(bienNuevo.getCodigo(), bienNuevo.getTipo(), true);
		}
	
		
	}

	@Override
	public void modificarBien(BienDTO bien) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {
		Bien bienMod=null;
		bienMod= this.toBien(bien);
		this.bienDao.update(bienMod);
		
	}

	@Override
	public void eliminarBien(BienDTO bien) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {
		Bien bienDelete=null;
		bienDelete= this.toBien(bien);
		this.bienDao.remove(bienDelete);
	}
	
	private void cargarBienesDonacion(ArrayList<BienDTO> bienes) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {
		
		for(BienDTO b : bienes) {
			this.registrarBien(b,false);
		}
		
	}

	@Override
	public DonacionDTO obtenerDonacionDTO(String codPedido)
	        throws DataNullException, DataEmptyException, DataObjectException,
	               DataDateException, DAOException, DataLengthException, DataListException {

	    if (codPedido == null) {
	        throw new DataNullException("El código de pedido no puede ser null");
	    }

	    if (codPedido.trim().isEmpty()) {
	        throw new DataEmptyException("El código de pedido no puede estar vacío");
	    }

	    ArrayList<DonacionDTO> donaciones = this.obtenerDonaciones();

	    if (donaciones == null || donaciones.isEmpty()) {
	        throw new DataListException("No existen donaciones registradas");
	    }

	    for (DonacionDTO dto : donaciones) {
	        if (dto != null && codPedido.equals(dto.getCodPedido())) {
	            return dto;
	        }
	    }

	    throw new DataObjectException(
	        "No se encontró una donación asociada al pedido: " + codPedido
	    );
	}


}

