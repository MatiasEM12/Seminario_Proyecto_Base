package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Orden;
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
    private OrdenRetiroDao ordenRetiroDao;
    private OrdenPedidoDao ordenPedidoDao;
    private VisitaDAOJDBC visitaDao;
    private BienDAO bienDao;
    private Bien_VisitaDAO bienVisitaDao;
    private Bien_DonacionDAO bienDonacionDao;
    private DonacionDAO donacionDao;
    private VoluntarioDAOJDBC voluntarioDao;
    private DonanteDao donanteDao;
    private UbicacionDAO ubicacionDao;
    private CoordenadaDAO coordenadaDAO; 
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
    }
   
    // --- Usuario / Rol ---
    @Override
    public void registrarUsuario(String username, String password, String contacto, String nombre, Integer codigoRol)
            throws DataEmptyException, SQLException {
    	
    	Usuario.setContadorUsuario(  this.usuarioDao.obtenerCantidadUsuarios());
        Rol rol = rolDao.find(codigoRol);
        Usuario usuario = new Usuario(username, password, nombre, contacto, rol,false,null);
        this.usuarioDao.create(usuario);
    }

    @Override
    public void registrarUsuario(String username, String password, String email, String nombre, Integer rol, boolean activo)
            throws DataEmptyException, SQLException {
    	
    	Usuario.setContadorUsuario(  this.usuarioDao.obtenerCantidadUsuarios());
        Rol rolN = rolDao.find(rol);
        Usuario usuario = new Usuario(username, password, nombre, email, rolN, activo,null);
        this.usuarioDao.create(usuario);
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() throws DataNullException {
        List<UsuarioDTO> dtos = new ArrayList<>();
        List<Usuario> usuarios = usuarioDao.findAll();
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                dtos.add(this.toUsuarioDTO(u));
            }
        }
        return dtos;
    }

    @Override
    public UsuarioDTO obtenerUsuario(String username) {
        Usuario u = usuarioDao.find(username);
        if (u == null) return null;
        return new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getContacto(),
                u.getRol().getNombre(), u.isActivo(), u.obtenerEstado());
    }

    @Override
    public void eliminarUsuario(String username) {
        Usuario user = this.usuarioDao.find(username);
        if (user != null) this.usuarioDao.remove(user);
    }

    @Override
    public List<RolDTO> obtenerRoles() throws StateChangeException {
        List<Rol> roles = rolDao.findAll();
        List<RolDTO> rolesDTO = new ArrayList<>();
        for (Rol rol : roles) {
            rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
        }
        return rolesDTO;
    }

    @Override
    public List<RolDTO> obtenerRolesActivos() throws StateChangeException {
        return rolDao.findAll().stream().filter(Rol::isActivo)
                .map(r -> new RolDTO(r.getCodigo(), r.getNombre(), r.isActivo())).collect(Collectors.toList());
    }

    @Override
    
    //opcion 1
    
    public void guardarRol(Integer codigo, String nombre, boolean estado) throws DataNullException {
        Rol rol = new Rol(codigo, nombre, estado);
        this.rolDao.create(rol);
    }

    @Override
    public RolDTO obtenerRolPorCodigo(Integer codigo) {
        Rol rol = rolDao.find(codigo);
        if (rol == null) return null;
        return new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
    }


    @Override
    public void activarRol(Integer codigo) throws StateChangeException{
    	Rol rol=rolDao.find(codigo);
        if(rol== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        rol.activar();
        rolDao.update(rol);
    }

    @Override
    //funciona
    public void desactivarRol(Integer codigo) throws StateChangeException{
    	Rol rol=rolDao.find(codigo);
        if(rol== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        rol.desactivar();
        rolDao.update(rol);
    }

    @Override
    public void activarUsuario(String username) throws StateChangeException{
    	Usuario usuario=usuarioDao.find(username);
        if(usuario== null) {
        	throw new StateChangeException("no se pudo encontrar un usuario con ese nombre");
        }
        usuario.activar();
        usuarioDao.update(usuario);
    }

    @Override
    public void desactivarUsuario(String username) throws StateChangeException{
    	Usuario usuario=usuarioDao.find(username);
        if(usuario== null) {
        	throw new StateChangeException("no se pudo encontrar un rol con ese codigo");
        }
        usuario.desactivar();
        usuarioDao.update(usuario);
    }

    @Override
    public Boolean existeUsuario(String username) {
        return usuarioDao.find(username) != null;
    }

    @Override
    //opcion 2
    
    public void guardarRol(RolDTO rol) throws DataNullException {
        Rol rolN = new Rol(rol.getCodigo(), rol.getNombre(), rol.getDescripcion(), rol.isActivo());
        this.rolDao.create(rolN);
    }

    @Override
    public void modificarContraseña(String usuario, String passWord) throws DataNullException {
        if (usuario == null || usuario.trim().isEmpty()) throw new DataNullException("nombre de usuario vacío");
        if (passWord == null || passWord.trim().isEmpty()) {
            throw new DataNullException("La contraseña no puede estar vacía.");
        }
        Usuario us_contraseña = usuarioDao.find(usuario);
        if (us_contraseña == null) throw new DataNullException("No existe un usuario con el nombre: " + usuario);
        us_contraseña.setContrasena(passWord);
        usuarioDao.update(us_contraseña);
    }


    @Override
    public Boolean autenticar(String username, String password) throws DataNullException{
        if (username == null || username.trim().isEmpty()) throw new DataNullException("nombre de usuario vacío");
        if (password == null || password.trim().isEmpty()) {
            throw new DataNullException("La contraseña no puede estar vacía.");
        }
        Usuario us_autentificado = usuarioDao.find(username);
        if (us_autentificado == null) throw new DataNullException("No existe un usuario con el username: " + username);
        return us_autentificado.getContrasena().equals(password);
    }        // pendiente: delegar a usuarioDao.autenticar si existe

    // --- Órdenes ---
    @Override
    public List<OrdenDTO> obtenerOrdenes() {
        List<OrdenDTO> todas = new ArrayList<>();
        List<OrdenPedidoDTO> pedidos = obtenerOrdenesPedido();
        if (pedidos != null) todas.addAll(pedidos);
        List<OrdenRetiroDTO> retiros = obtenerOrdenesRetiro();
        if (retiros != null) todas.addAll(retiros);
        return todas;
    }

    @Override
    public ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() {
        ArrayList<OrdenPedidoDTO> ordenesDTO = new ArrayList<>();
        List<OrdenPedido> ordenes = ordenPedidoDao.findAll();
        if (ordenes == null) return ordenesDTO;
        for (OrdenPedido orden : ordenes) {
            
            String tipo = OrdenPedido.getTipo();
            ordenesDTO.add(new OrdenPedidoDTO(orden.getFechaEmision(), orden.getEstado().toString(), tipo,
                    orden.getCodigo(), orden.isCargaPesada(), orden.getObservaciones(), orden.getCodDonante(),
                    orden.getCodDonacion()));
        }
        return ordenesDTO;
    }

    @Override
    public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() {
        List<OrdenRetiro> ordenesRetiro = ordenRetiroDao.findAll();
        ArrayList<OrdenRetiroDTO> ordenesRetiroDTO = new ArrayList<>();
        if (ordenesRetiro == null) return ordenesRetiroDTO;

        for (OrdenRetiro ordenRetiro : ordenesRetiro) {
            String[] codVisitas = null;
            if (ordenRetiro.getCodVisitas() != null) {
                // si la cadena está separada por comas
                codVisitas = ordenRetiro.getCodVisitas();
            }

            String voluntarioCodigo = ordenRetiro.getVoluntario() != null
                    ? ordenRetiro.getVoluntario().getCodigo()
                    : null;

            ordenesRetiroDTO.add(new OrdenRetiroDTO(
                    ordenRetiro.getFechaEmision(),
                    ordenRetiro.getEstado().toString(),
                    null,
                    ordenRetiro.getCodigo(),
                    ordenRetiro.getPedido().getCodigo(),
                    voluntarioCodigo,
                    codVisitas));
        }

        return ordenesRetiroDTO;
    }
    
    @Override
    public void registrarOrdenRetiro1(OrdenRetiroDTO retiro)
            throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
    	
        // validaciones básicas
        if (retiro == null) {
            throw new DataNullException("OrdenRetiro DTO es nula");
        }

        // Buscar voluntario (puede ser null si no se asignó)
        Voluntario v = null;
        if (retiro.getCodVoluntario() != null && !retiro.getCodVoluntario().trim().isEmpty()) {
            v = voluntarioDao.find(retiro.getCodVoluntario());
            // opcional: lanzar excepción si el voluntario no existe
            // if (v == null) throw new DataNullException("Voluntario no encontrado: " + retiro.getCodVoluntario());
        }

        // Buscar pedido (obligatorio)
        if (retiro.getPedido() == null || retiro.getPedido().trim().isEmpty()) {
            throw new DataNullException("La orden retiro debe referenciar a una orden de pedido");
        }
        OrdenPedido pedido = ordenPedidoDao.find(retiro.getPedido());
        if (pedido == null) {
            throw new DataNullException("No existe la OrdenPedido: " + retiro.getPedido());
        }

        // Construir lista de Visitas a partir de los códigos (si vienen)
        ArrayList<Visita> visitas = new ArrayList<>();
        String[] codVisitasArr = retiro.getCodVisitas(); // OrdenRetiroDTO tiene String[] getCodVisitas()
        if (codVisitasArr != null) {
            for (String codVis : codVisitasArr) {
                if (codVis == null || codVis.trim().isEmpty()) continue;
                Visita vFound = visitaDao.find(codVis); // requiere que visitaDao tenga find(String)
                if (vFound != null) {
                    visitas.add(vFound);
                }
                // Si querés que falle cuando una visita referenciada no exista, descomenta:
                // else throw new DataNullException("Visita no encontrada: " + codVis);
            }
        }

        // Estado: si DTO trae null, poner PENDIENTE por defecto (string)
        String estado = Orden.EstadoOrden.PENDIENTE.toString();
        if (estado == null || estado.trim().isEmpty()) {
            estado = "PENDIENTE";
        }

        // constructor que acepta (String codigo, String estado, LocalDate fechaEmision, Voluntario voluntario, OrdenPedido ordenPedido, ArrayList<Visita> visitas)
        OrdenRetiro orden = new OrdenRetiro(
                retiro.getCodigo(),       // puede ser null -> el constructor crea su propio código si querés; si no, se setea directamente
                estado,
                retiro.getFechaEmision(),
                v,
                pedido,
                visitas
        );

        // Persistir
        ordenRetiroDao.create(orden);
    }

    private OrdenRetiroDTO toOrdenRetiroDTO(OrdenRetiro ordenRetiro) {
        if (ordenRetiro == null) {
            return null;
        }

        String[] codVisitas = ordenRetiro.getCodVisitas(); // ya devuelve String[]
        String codVoluntario = (ordenRetiro.getVoluntario() != null)
                ? ordenRetiro.getVoluntario().getCodigo()
                : null;
        String codPedido = (ordenRetiro.getPedido() != null)
                ? ordenRetiro.getPedido().getCodigo()
                : null;

        return new OrdenRetiroDTO(
                ordenRetiro.getFechaEmision(),
                ordenRetiro.getEstado() == null ? "Pendiente" : ordenRetiro.getEstado().toString(),
                OrdenRetiro.getTipo(),
                ordenRetiro.getCodigo(),
                codPedido,
                codVoluntario,
                codVisitas
        );
    }
    @Override
    public void inicializarOrdenesRetiro(String codPedido) throws DataNullException {
        if (codPedido == null || codPedido.trim().isEmpty()) throw new DataNullException("Código pedido vacío");
        OrdenPedido pedido = ordenPedidoDao.find(codPedido);
        if (pedido == null) throw new DataNullException("No existe OrdenPedido con código: " + codPedido);
   
        String codigoRetiro = "OR_" + codPedido;
        OrdenRetiro orden = new OrdenRetiro(codigoRetiro, "PENDIENTE", LocalDate.now(), null, pedido, new ArrayList<Visita>());
        ordenRetiroDao.create(orden);
    }

    // --- Donaciones / Donantes ---
	    @Override
	    public void registrarDonante(Donante donante) {
	        if (donante == null) return;
	        donanteDao.create(donante);
	    }

    @Override
    public List<DonanteDTO> obtenerDonantes(String userSolicitante) {
     
      
        return null;
    }
    
    @Override
    public List<DonanteDTO> obtenerDonantes() {
     
        List<Donante> list = donanteDao.findAll();
        if (list == null) return new ArrayList<>();
        return list.stream().filter(Objects::nonNull).map(d -> new DonanteDTO(d.getNombre(), d.getCodigo(), d.getApellido(), d.getContacto(), null,
                d.getUbicacion() != null ? d.getUbicacion().getCodigo() : null, null)).filter(Objects::nonNull).collect(Collectors.toList());
    }


    @Override
    public List<UsuarioDTO> obtenerUserDonantes() throws DataNullException {
       
    	 List<Usuario>donantes= this.usuarioDao.findAll();
         donantes=donantes.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Donante")).collect(Collectors.toList());
          List<UsuarioDTO>donantesDTO= donantes.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
        		  
        		return donantesDTO;
    }
    
    
    
    private UsuarioDTO toUsuarioDTO(Usuario user) {
    	
    	UsuarioDTO userDTO = new UsuarioDTO (user.getUsuario(),user.getContrasena(),user.getNombre(),user.getContacto(),user.getRolName(),user.isActivo(),user.getCodigo(),user.getEstado());
    	

    	return userDTO;
    }

    @Override
    public List<UsuarioDTO> obtenerUserVoluntarios() throws DataNullException {
           
          List<Usuario>voluntarios= this.usuarioDao.findAll();
         voluntarios=voluntarios.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Voluntario")).collect(Collectors.toList());
          
          List<UsuarioDTO>voluntariosDTO= voluntarios.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
        		  
        		return voluntariosDTO;
        
    }

    @Override
    public List<UsuarioDTO> obtenerUserAdministrador() throws DataNullException {
    	  List<Usuario>administradores= this.usuarioDao.findAll();
          administradores=administradores.stream().filter(o->o.getRol().getNombre().equalsIgnoreCase("Admin")).collect(Collectors.toList());
           List<UsuarioDTO>administradoresDTO= administradores.stream().map(usuario ->toUsuarioDTO(usuario)).collect(Collectors.toList());
         		  
         		return administradoresDTO;
         
    }

    @Override
    public void registrarDonacion(DonacionDTO don) throws DataNullException, DataDoubleException, DataEmptyException, DataObjectException, DataDateException {
    	
    	
    	 if (don == null) return;      
    	    Donacion donacion = this.toDonacion(don);   
    	    donacionDao.create(donacion);
    	    this.crearBienDonacion(donacion);
    }
    
    
    private Donacion toDonacion(DonacionDTO dto)
            throws DataNullException, DataDoubleException, DataEmptyException,
                   DataObjectException, DataDateException {

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
    public ArrayList<DonacionDTO> obtenerDonaciones() throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
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
    public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) {
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
    public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) {
        // delegamos a OrdenRetiro DAO: buscar la orden y mapear sus bienes
        ArrayList<BienDTO> resultado = new ArrayList<>();
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) return resultado;
        List<OrdenRetiro> ordenes = ordenRetiroDao.findAll();
        if (ordenes == null) return resultado;
        for (OrdenRetiro o : ordenes) {
            if (o != null && codOrdenRetiro.equalsIgnoreCase(o.getCodigo()) && o.getRecolectados() != null) {
                for (Bien b : o.getRecolectados()) resultado.add(toBienDTO(b));
                return resultado;
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
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
    public void registrarVoluntario(Voluntario voluntario) {
         voluntarioDao.create(voluntario);
    }
    @Override
    public List<VoluntarioDTO> obtenerVoluntarios() {
        return new ArrayList<>();
    }

    @Override
    public void registrarVisita(Visita visita) {
        visitaDao.create(visita);
    }   
    
    public void registrarOrdenPedido(OrdenPedido orden) {
    	ordenPedidoDao.create(orden);
    }
    public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException{
    	if (orden==null) {
    		throw new DataNullException("la orden de pedido invalida");
    	}
    	try {
    		Donante donante=null;
	        if (orden.getCodDonante() != null && !orden.getCodDonante().trim().isEmpty()) {
	            donante = donanteDao.find(orden.getCodDonante());
	        }
	        if (donante == null) {
                throw new DataNullException("No existe un donante con el codigo: "+orden.getCodDonante());
            }
    		OrdenPedido pedido= new OrdenPedido(
	                orden.getCodigo(),
	                orden.getFechaEmision(),
	                orden.getObservaciones(),
	                orden.isCargaPesada(),
	                donante.getCodigo()
    				);
            ordenPedidoDao.create(pedido);
    	}catch(Exception e) {
    		System.err.println("Error al registrar la orden de pedido: " + e.getMessage());
            e.printStackTrace();
    	}
    }

   


    @Override
    public void cargarVisita(VisitaDTO visitaDTO) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
        
    	Visita visita= toVisita(visitaDTO);
    
    	OrdenRetiro oR = this.ordenRetiroDao.find(visita.getCodOrdenRetiro());
    	
    	oR.agregarVisita(visita);
    	this.crearBienVisita(visita);
    	this.visitaDao.update(visita);
    	this.ordenRetiroDao.update(oR);
    	
    	
    
    	
    	
    	
    }
    private void crearBienDonacion(Donacion donacion) {
    	
    	ArrayList<Bien> bienes= donacion.getBienes();
    	
    	for(Bien b :  bienes) {
    		this.bienDonacionDao.create(b.getCodigo(), donacion.getCodigo());
    	}
    	
    }
    
    private void crearBienVisita(Visita visita) {
    	
    	ArrayList<Bien> bienes= visita.getBienesRecolectados();
    	
    	for(Bien b :  bienes) {
    		this.bienVisitaDao.create(b.getCodigo(), visita.getCodigo());
    	}
    	
    }
    private Visita toVisita(VisitaDTO visitaDTO) throws DataNullException, DataLengthException, DataDoubleException {
    	
    	Visita visita = new Visita (visitaDTO.getFechaVisita(), visitaDTO.getObservaciones(), visitaDTO.getTipo(), visitaDTO.getCodOrdenRetiro(),
    			 toBienesList(visitaDTO.getBienesRecolectados()),visitaDTO.isEsFinal());
    	
    	
    	return visita ;
    }
    
    private ArrayList<Bien> toBienesList(ArrayList<BienDTO> bienesDTO) throws DataNullException, DataDoubleException{
    	
    	ArrayList<Bien>bienes= new ArrayList<>();
    	
    	for(BienDTO dt : bienesDTO) {
    		
    		Bien bien = new Bien( dt.getCodigo(),dt.getTipo(),dt.getPeso(),dt.getNombre(),dt.getDescripcion(),dt.getNivelNecesidad(),dt.getFechaVencimiento(),dt.getTalle(),dt.getMaterial()  );
    		bienes.add(bien);
    	}
    	
    	return bienes;
    }
    
    public void registrarUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) return;
        coordenadaDAO.create(ubicacion.getCoordenada());  // crea o ignora si ya existe
        ubicacionDao.create(ubicacion);                   // crea o ignora si ya existe
    }


    //funciona es el unico guardado rol que entra porque aunque vos nunca toques la descripcion lo toma como que le invias un dato	@Override
	public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) throws DataNullException {
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

	@Override
	public void registrarOrdenRetiro(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void registrarOrdenRetiro(OrdenRetiro retiroO)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		// TODO Auto-generated method stub
		
	}
	private OrdenPedido toOrdenPedido(OrdenPedidoDTO dto) {
	    if (dto == null) return null;

	    try {
	        // Buscar el donante por código si existe
	        Donante donante = null;
	        if (dto.getCodDonante() != null && !dto.getCodDonante().trim().isEmpty()) {
	            donante = donanteDao.find(dto.getCodDonante());
	        }
//LocalDate fechaEmision, boolean cargaPesada,String observaciones, String codDonante, String codDonacion
	        // Crear el objeto del modelo
	        OrdenPedido pedido = new OrdenPedido(dto.getFechaEmision(),dto.isCargaPesada(),dto.getObservaciones(),dto.getCodDonante(),dto.getCodDonacion() );

	        return pedido;
	    } catch (Exception e) {
	        System.err.println("Error convirtiendo OrdenPedidoDTO a modelo: " + e.getMessage());
	        return null;
	    }
	}

	@Override
	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) {
	      OrdenRetiro orden =this.ordenRetiroDao.find(codOrdenRetiro);
	      
	     
	      return   toOrdenRetiroDTO(orden);
	}

	@Override
	public DonacionDTO obtenerDonacion(String ordenP) throws DataNullException {
	
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
	
	private ArrayList<BienDTO> listBienDTO(ArrayList<Bien> bienes){
		
		ArrayList<BienDTO> dtos= new ArrayList<>();
		
		for(Bien b : bienes) {
			
			BienDTO dt = toBienDTO(b);
			dtos.add(dt);
		}
		
		return dtos;
		
	}
	private ArrayList<Bien> listBien(ArrayList<BienDTO> bienesDTO) throws DataNullException, DataDoubleException{
		
		ArrayList<Bien> bienes= new ArrayList<>();
		
		for(BienDTO dt : bienesDTO) {
			
			Bien b = this.toBien(dt);
			bienes.add(b);
		}
		
		return bienes;
		
	}
	
	private Bien toBien(BienDTO bien) throws DataNullException, DataDoubleException {
		
		
		 return new Bien(
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

	@Override
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
		
		List<Donacion> donaciones= this.donacionDao.findAllPendiente(); 
		
		
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
	public BienDTO obtenerBien(String codigo) {
		Bien bien=this.bienDao.find(codigo);
		return this.toBienDTO(bien);
	}

}

