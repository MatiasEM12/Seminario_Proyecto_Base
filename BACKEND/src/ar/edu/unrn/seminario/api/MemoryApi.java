package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;



public class MemoryApi implements IApi {

    
    private Map<Integer, Rol> rolesByCodigo = new HashMap<>();

    // usuarios
    private Map<String, Usuario> usuariosByCodigo = new HashMap<>();
    private Map<String, Usuario> usuariosByUsername = new HashMap<>();

    // perfiles
    private Map<String, Donante> donantesByCodigo = new HashMap<>();
    private Map<String, Beneficiario> beneficiarioByCodigo = new HashMap<>();
    private Map<String, Voluntario> voluntariosByCodigo = new HashMap<>();

    // ubicaciones
    private Map<String, Ubicacion> ubicacionesByCodigo = new HashMap<>();

    // órdenes
    private Map<String, OrdenEntrega> entregasByCodigo = new HashMap<>();
    private Map<String, OrdenPedido> pedidosByCodigo = new HashMap<>();
    private Map<String, OrdenRetiro> retirosByCodigo = new HashMap<>();

    // bienes / visitas / donaciones / solicitudes
    private Map<String, Bien> bienByCodigo = new HashMap<>();
    private Map<String, Visita> visitasByCodigo = new HashMap<>();
    private Map<String, Donacion> donacionByCodigo = new HashMap<>();
    private Map<String, SolicitudBien> solicitudesByCodigo = new HashMap<>();


    // inventario contiene los Bien "presentes" en inventario
    private ArrayList<Bien> inventario = new ArrayList<>();
    // disponibilidad por código de Bien
    private Map<String, Boolean> inventarioDisponible = new HashMap<>();
    // tipoBien por código de Bien (tabla inventario tenía tipoBien)
    private Map<String, String> inventarioTipo = new HashMap<>();

    // =========================
    // Datos de prueba
    // =========================
    private SolicitudBien solicitudTest;
    private Donacion donacionTest;

    public MemoryApi() throws Exception {
        inicializarContadores();
        inicializarTest();
    }



    private void inicializarTest() throws Exception {

        rolesByCodigo.clear();
        usuariosByCodigo.clear();
        usuariosByUsername.clear();

        donantesByCodigo.clear();
        beneficiarioByCodigo.clear();
        voluntariosByCodigo.clear();

        ubicacionesByCodigo.clear();
        entregasByCodigo.clear();
        pedidosByCodigo.clear();
        retirosByCodigo.clear();

        bienByCodigo.clear();
        visitasByCodigo.clear();
        donacionByCodigo.clear();
        solicitudesByCodigo.clear();

        inventario.clear();
        inventarioDisponible.clear();
        inventarioTipo.clear();

        solicitudTest = null;
        donacionTest = null;

        // ===================== ROLES =====================
        Rol rol1 = new Rol(1, "Admin", true);
        Rol rol2 = new Rol(2, "Voluntario", true);
        Rol rol3 = new Rol(3, "Donante", true);
        Rol rol4 = new Rol(4, "Beneficiario", true);

        rolesByCodigo.put(rol1.getCodigo(), rol1);
        rolesByCodigo.put(rol2.getCodigo(), rol2);
        rolesByCodigo.put(rol3.getCodigo(), rol3);
        rolesByCodigo.put(rol4.getCodigo(), rol4);

        // ===================== USUARIOS =====================
        Usuario usuario_1 = new Usuario("perry_AD", "87654321", "Jeff", "perry_AD12@mail.com", rol1, true, null);
        Usuario usuario_2 = new Usuario("pedro_Vol", "12345678", "Pedro", "pedro_Vol12@mail.com", rol2, true, null);
        Usuario usuario_3 = new Usuario("ian_Don", "12121212", "Ian", "ian_Don12@mail.com", rol3, true, null);
        Usuario usuario_4 = new Usuario("matias_Ben", "00010001", "Matias", "matias_Ben12@mail.com", rol4, true, null);

        putUsuario(usuario_1);
        putUsuario(usuario_2);
        putUsuario(usuario_3);
        putUsuario(usuario_4);

        // ===================== COORDENADAS + UBICACIONES =====================
        Coordenada coordenadaBeneficiario = new Coordenada(11.233, 12.333);
        Coordenada coordenadaDonante = new Coordenada(23.333, 12.3333);

        Ubicacion ubicacionBeneficiario = new Ubicacion("Este", "SanJuan", "123", coordenadaBeneficiario);
        Ubicacion ubicacionDonante = new Ubicacion("Norte", "Flores", "456", coordenadaDonante);

        ubicacionesByCodigo.put(ubicacionBeneficiario.getCodigo(), ubicacionBeneficiario);
        ubicacionesByCodigo.put(ubicacionDonante.getCodigo(), ubicacionDonante);

        // ===================== BENEFICIARIO / DONANTE / VOLUNTARIO =====================
        Beneficiario beneficiarioTest = new Beneficiario(
                "Matias", "Ben",
                LocalDate.of(2000, 1, 10),
                "11111111",
                "matias_Ben12@mail.com",
                ubicacionBeneficiario,
                "matias_Ben",
                1,
                0
        );

        Donante donanteTest = new Donante(
                "Ian", "Don",
                LocalDate.of(1999, 3, 20),
                "22222222",
                "ian_Don12@mail.com",
                ubicacionDonante,
                "ian_Don"
        );

        Voluntario voluntarioTest = new Voluntario(
                "Pedro", "Contrera",
                LocalDate.of(2003, 10, 21),
                "pedro_Vol12@mail.com",
                "33333333",
                "pedro_Vol"
        );

        beneficiarioByCodigo.put(beneficiarioTest.getCodigo(), beneficiarioTest);
        donantesByCodigo.put(donanteTest.getCodigo(), donanteTest);
        voluntariosByCodigo.put(voluntarioTest.getCodigo(), voluntarioTest);

        // ===================== BIENES + DONACION =====================
        Bien alimentoTest = new Bien(null, "Alimento", null, "Manteca", "Manteca marca 'YYYY'",
                LocalDate.of(2027, 1, 1), null, null);

        Bien ropaTest = new Bien(null, "Ropa", null, "Camisa", "Camisa usada, con botones cambiados",
                null, 4.0, "algodon");

        bienByCodigo.put(alimentoTest.getCodigo(), alimentoTest);
        bienByCodigo.put(ropaTest.getCodigo(), ropaTest);

        ArrayList<Bien> bienesDonacion = new ArrayList<>();
        bienesDonacion.add(ropaTest);
        bienesDonacion.add(alimentoTest);

        donacionTest = new Donacion(
                LocalDate.now(),
                "Donacion de una camisa y una manteca",
                bienesDonacion,
                donanteTest,
                null
        );
        donacionByCodigo.put(donacionTest.getCodigo(), donacionTest);

        // ===================== BIENES PARA SOLICITUD + INVENTARIO =====================
        Bien alimentoTestSolicitud = new Bien(null, "Alimento", null, "Fideos", "Fideos marca 'YYYY'",
                LocalDate.of(2026, 7, 12), null, null);

        Bien ropaTestSolicitud = new Bien(null, "Ropa", null, "Pantalon", "Pantalon nuevo",
                null, 1.0, "algodon");

        bienByCodigo.put(alimentoTestSolicitud.getCodigo(), alimentoTestSolicitud);
        bienByCodigo.put(ropaTestSolicitud.getCodigo(), ropaTestSolicitud);

        // inventario inicial (disponibles)
        registrarBienInventario(alimentoTestSolicitud.getCodigo(), alimentoTestSolicitud.getTipo(), true);
        registrarBienInventario(ropaTestSolicitud.getCodigo(), ropaTestSolicitud.getTipo(), true);

        ArrayList<Bien> bienesSeleccionados = new ArrayList<>();
        bienesSeleccionados.add(ropaTestSolicitud);
        bienesSeleccionados.add(alimentoTestSolicitud);

        solicitudTest = new SolicitudBien(
                beneficiarioTest.getCodigo(),
                bienesSeleccionados,
                EstadoOrden.PENDIENTE.toString()
        );
        solicitudesByCodigo.put(solicitudTest.getCodigo(), solicitudTest);
    }

    private void inicializarContadores() {
        // contadores en 0: entorno memoria
        Beneficiario.setContadorDonante(0);
        Bien.setContadorBien(0);
        Coordenada.setContadorCoordenada(0);
        Donacion.setContadorDonacion(0);
        Donante.setContadorDonante(0);
        OrdenEntrega.setContadorCoordenada(0);
        OrdenPedido.setContadorPedido(0);
        OrdenRetiro.setContadorOrdenRetiro(0);
        Ubicacion.setContadorUbicacion(0);
        Usuario.setContadorUsuario(0);
        Voluntario.setContadorVoluntario(0);
        Visita.setContadorVisita(0);
        SolicitudBien.setContadorSolicitud(0);
    }


    @Override
    public void registrarUsuario(String username, String password, String email, String nombre, Integer rol, boolean activo)
            throws DataEmptyException, DataObjectException, DataNullException, DataDateException, DataLengthException, DataIntException {

        if (username == null) throw new DataNullException("username nulo");
        if (password == null) throw new DataNullException("password nulo");
        if (email == null) throw new DataNullException("email nulo");
        if (nombre == null) throw new DataNullException("nombre nulo");
        if (rol == null) throw new DataNullException("rol nulo");

        if (existeUsuario(username)) {
           
            throw new DataObjectException("Ya existe el usuario: " + username);
        }

        Rol role = buscarRol(rol);
        if (role == null) throw new DataObjectException("No existe rol con código: " + rol);

        Usuario usuario = new Usuario(username, password, nombre, email, role, activo, null);
        putUsuario(usuario);

        // crear perfil básico según rol (mínimos cambios)
        String rn = role.getNombre();
        if ("DONANTE".equalsIgnoreCase(rn)) {
            Coordenada cor = new Coordenada(11111, 11111);
            Ubicacion ubicacion = new Ubicacion("lugar", "de", "prueba", cor);
            Donante d = new Donante(nombre, "jose", LocalDate.now(), "34453544", email, ubicacion, username);
            donantesByCodigo.put(d.getCodigo(), d);
        } else if ("VOLUNTARIO".equalsIgnoreCase(rn)) {
            Voluntario v = new Voluntario(nombre, "pepe", LocalDate.now(), email, "34453544", username);
            voluntariosByCodigo.put(v.getCodigo(), v);
        } else if ("BENEFICIARIO".equalsIgnoreCase(rn)) {
            Coordenada cor = new Coordenada(11111, 11111);
            Ubicacion ubicacion = new Ubicacion("lugar", "de", "prueba", cor);
            Beneficiario b = new Beneficiario(nombre, "ape", LocalDate.now(), "00000000", email, ubicacion, username, 0, 0);
            beneficiarioByCodigo.put(b.getCodigo(), b);
        }
    }

    @Override
    public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol)
            throws DataEmptyException, DataNullException, DataExistsException, DataObjectException, DataLengthException, DataIntException {

        if (username == null) throw new DataNullException("username nulo");
        if (existeUsuario(username)) throw new DataExistsException("Ya existe el usuario: " + username);

        try {
            registrarUsuario(username, password, email, nombre, codigoRol, true);
        } catch (DataDateException e) {
            throw new DataObjectException(e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuariosByUsername.values().stream()
                .filter(Objects::nonNull)
                .map(this::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerUsuario(String username) {
        Usuario u = usuariosByUsername.get(username);
        return (u == null) ? null : toUsuarioDTOFull(u);
    }

    @Override
    public void eliminarUsuario(String username) {

        // Eliminar de usuarios por username
        Usuario usuario = usuariosByUsername.remove(username);

        // Si existe, eliminar también por código
        if (usuario != null) {
            usuariosByCodigo.remove(usuario.getCodigo());
        }

        // Eliminar perfiles asociados
        donantesByCodigo.values()
                .removeIf(d -> username.equalsIgnoreCase(d.getUsername()));

        voluntariosByCodigo.values()
                .removeIf(v -> username.equalsIgnoreCase(v.getUsername()));

        beneficiarioByCodigo.values()
                .removeIf(b -> username.equalsIgnoreCase(b.getUsername()));
    }
    @Override
    public void activarUsuario(String usuario) throws StateChangeException {
        Usuario user = usuariosByUsername.get(usuario);
        if (user != null) user.activar();
    }

    @Override
    public void desactivarUsuario(String usuario) throws StateChangeException {
        Usuario user = usuariosByUsername.get(usuario);
        if (user != null) user.desactivar();
    }

    @Override
    public void modificarContraseña(String usuario, String passWord)throws DataEmptyException, DataNullException, DataLengthException {
        if (usuario == null || usuario.trim().isEmpty()) throw new DataNullException("usuario vacío");
        if (passWord == null || passWord.trim().isEmpty()) throw new DataEmptyException("contraseña vacía");
        Usuario user = usuariosByUsername.get(usuario);
        if (user != null) user.setContrasena(passWord);
    }

    @Override
    public Boolean autenticar(String username, String password) {
        if (username == null || password == null) return false;
        Usuario u = usuariosByUsername.get(username);
        if (u == null) return false;
        return password.equals(u.getContrasena());
    }

    @Override
    public Boolean existeUsuario(String username) {
        return username != null && usuariosByUsername.containsKey(username);
    }

    @Override
    public List<UsuarioDTO> obtenerUserDonantes() {
        return usuariosByUsername.values().stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getRol() != null && "DONANTE".equalsIgnoreCase(u.getRol().getNombre()))
                .map(this::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> obtenerUserVoluntarios() {
        return usuariosByUsername.values().stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getRol() != null && "VOLUNTARIO".equalsIgnoreCase(u.getRol().getNombre()))
                .map(this::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> obtenerUserAdministrador() {
        return usuariosByUsername.values().stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getRol() != null && "ADMIN".equalsIgnoreCase(u.getRol().getNombre()))
                .map(this::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    // ROLES

    @Override
    public List<RolDTO> obtenerRoles() {
        return rolesByCodigo.values().stream()
                .filter(Objects::nonNull)
                .map(r -> new RolDTO(r.getCodigo(), r.getNombre(), r.isActivo(), r.getDescripcion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RolDTO> obtenerRolesActivos() {
        return rolesByCodigo.values().stream()
                .filter(Objects::nonNull)
                .filter(Rol::isActivo)
                .map(r -> new RolDTO(r.getCodigo(), r.getNombre(), r.getDescripcion()))
                .collect(Collectors.toList());
    }

    @Override
    public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) throws DataNullException {
        if (codigo == null) throw new DataNullException("codigo rol null");
        Rol rol = new Rol(codigo, nombre, descripcion, estado);
        rolesByCodigo.put(codigo, rol);
    }

    @Override
    public void guardarRol(Integer codigo, String nombre, boolean estado) throws DataNullException {
        if (codigo == null) throw new DataNullException("codigo rol null");
        Rol rol = new Rol(codigo, nombre, estado);
        rolesByCodigo.put(codigo, rol);
    }

    @Override
    public void guardarRol(RolDTO rol) throws DataNullException {
        if (rol == null) throw new DataNullException("rol null");
        Rol rolnew = new Rol(rol.getCodigo(), rol.getNombre(), rol.getDescripcion(), rol.isActivo());
        rolesByCodigo.put(rolnew.getCodigo(), rolnew);
    }

    @Override
    public RolDTO obtenerRolPorCodigo(Integer codigo) {
        Rol rol = rolesByCodigo.get(codigo);
        if (rol == null) return null;
        return new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo(), rol.getDescripcion());
    }

    @Override
    public void activarRol(Integer codigo) throws StateChangeException {
        Rol rol = rolesByCodigo.get(codigo);
        if (rol != null) rol.activar();
    }

    @Override
    public void desactivarRol(Integer codigo) throws StateChangeException {
        Rol rol = rolesByCodigo.get(codigo);
        if (rol != null) rol.desactivar();
    }

  

    @Override
    public List<OrdenDTO> obtenerOrdenes() {
        List<OrdenDTO> todas = new ArrayList<>();

        for (OrdenPedido op : pedidosByCodigo.values()) {
            OrdenPedidoDTO dto = toOrdenPedidoDTO(op);
            if (dto != null) todas.add(dto);
        }

        for (OrdenRetiro or : retirosByCodigo.values()) {
            OrdenRetiroDTO dto = toOrdenRetiroDTO(or);
            if (dto != null) todas.add(dto);
        }

        for (OrdenEntrega oe : entregasByCodigo.values()) {
            OrdenEntregaDTO dto = toOrdenEntregaDTO(oe);
            if (dto != null) todas.add(dto);
        }

        return todas;
    }

    @Override
    public ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() {
        ArrayList<OrdenPedidoDTO> res = new ArrayList<>();
        for (OrdenPedido op : pedidosByCodigo.values()) {
            OrdenPedidoDTO dto = toOrdenPedidoDTO(op);
            if (dto != null) res.add(dto);
        }
        return res;
    }

    @Override
    public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() {
        ArrayList<OrdenRetiroDTO> res = new ArrayList<>();
        for (OrdenRetiro or : retirosByCodigo.values()) {
            OrdenRetiroDTO dto = toOrdenRetiroDTO(or);
            if (dto != null) res.add(dto);
        }
        return res;
    }

    @Override
    public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException {
 
    	
    	if (orden == null) throw new DataNullException("OrdenPedidoDTO inválida");

        try {
            OrdenPedido op = new OrdenPedido(
            	     orden.getCodigo(),
 	                orden.getFechaEmision(),
 	                orden.isCargaPesada(), 
 	                orden.getObservaciones(),
 	                orden.getCodDonacion()
            );
    
            pedidosByCodigo.put(op.getCodigo(), op);

          
            Donacion d = donacionByCodigo.get(orden.getCodDonacion());
            if (d != null) {
                d.setPedido(op);
                donacionByCodigo.put(d.getCodigo(), d);
            }

        } catch (Exception e) {
            throw new DataNullException(e.getMessage());
        }
    }

    @Override
    public void inicializarOrdenesRetiro(String codPedido)
            throws DataNullException, DAOException, DataObjectException, DataListException, DataDateException, DataEmptyException, StateChangeException {

        if (codPedido == null || codPedido.trim().isEmpty()) throw new DataNullException("Código pedido vacío");

        OrdenPedido pedido = pedidosByCodigo.get(codPedido);
        if (pedido == null) throw new DataObjectException("No existe OrdenPedido con código: " + codPedido);

        // crea una OR "por defecto"
        OrdenRetiro or = new OrdenRetiro(null, EstadoOrden.PENDIENTE.toString(), LocalDate.now(), null, pedido, new ArrayList<>());
        retirosByCodigo.put(or.getCodigo(), or);

        // opcional: pasar pedido a EN_PROCESO si corresponde
        try {
            if (pedido.getEstado() == EstadoOrden.PENDIENTE) {
                pedido.setEstado(EstadoOrden.EN_PROCESO);
            }
        } catch (Exception ignored) { }
    }

    @Override
    public void completarOrdenRetiro(String codOrdenRetiro) throws Exception {
    	// TODO Auto-generated method stub
		
    }

    
    @Override
    public void registrarOrdenRetiro(OrdenRetiroDTO retiro)
            throws DataNullException, DataLengthException, DataDoubleException, StateChangeException,
                   DataObjectException, DataListException, DataDateException, DataEmptyException {
  	
        if (retiro == null) throw new DataNullException("OrdenRetiroDTO es nula");

        Voluntario voluntario = null;
        if (retiro.getCodVoluntario() != null && !retiro.getCodVoluntario().trim().isEmpty()) {
            voluntario =findVoluntarioByCodigoOrUsername(retiro.getCodVoluntario());
           
            if (voluntario == null) throw new DataNullException("Voluntario no encontrado: " + retiro.getCodVoluntario());
        }

        
        
        // Buscar pedido (obligatorio)
        if (retiro.getPedido() == null || retiro.getPedido().trim().isEmpty()) {
            throw new DataNullException("La orden retiro debe referenciar a una orden de pedido");
        }
        
    
        OrdenPedido pedido = pedidosByCodigo.get(retiro.getPedido());
        if (pedido == null) throw new DataObjectException("No existe OrdenPedido con código: " + retiro.getPedido());

        // Construir lista de Visitas a partir de los códigos (si vienen)
   
        ArrayList<Visita> visitas = new ArrayList<>();
        String[] codVisitasArr = retiro.getCodVisitas();

        if (codVisitasArr != null) {
            for (String codVis : codVisitasArr) {

                if (codVis != null && !codVis.trim().isEmpty()) {

                    Visita vFound = visitasByCodigo.get(codVis);

                    if (vFound != null) {
                        visitas.add(vFound);
                    }
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
                voluntario,
                pedido,
                visitas
        );
        retirosByCodigo.put(orden.getCodigo(), orden);

        // Persistir
       
       pedido.setEstado(Orden.EstadoOrden.EN_PROCESO);
       pedidosByCodigo.put(pedido.getCodigo(), pedido);
    }

    @Override
    public void registrarOrdenRetiro1(OrdenRetiroDTO retiro)
            throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException,
                   DataObjectException, DataListException, DataDateException, DataEmptyException {
        
        registrarOrdenRetiro(retiro);
    }

    @Override
    public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) throws DAOException {
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) return null;
        OrdenRetiro or = retirosByCodigo.get(codOrdenRetiro);
        return (or == null) ? null : toOrdenRetiroDTO(or);
    }

    @Override
    public OrdenEntregaDTO obtenerOrdenEntrega(String codEntrega) throws DAOException {
        if (codEntrega == null || codEntrega.trim().isEmpty()) return null;
        OrdenEntrega oe = entregasByCodigo.get(codEntrega);
        return (oe == null) ? null : toOrdenEntregaDTO(oe);
    }

    @Override
    public void registrarOrdenEntrega(OrdenEntregaDTO ordenEntrega)
            throws DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException,
                   DataDoubleException, StateChangeException, DataObjectException, DAOException {

        if (ordenEntrega == null) throw new DataNullException("OrdenEntregaDTO null");

        OrdenEntrega oe = toOrdenEntrega(ordenEntrega);

        // persistir en memoria
        entregasByCodigo.put(oe.getCodigo(), oe);

        // al crear OE: solicitud pasa a EN_PROCESO (igual que PersistenceApi)
        if (oe.getSolicitud() != null) {
            SolicitudBien sb = solicitudesByCodigo.get(oe.getSolicitud().getCodigo());
            if (sb != null) {
                sb.setEstado(EstadoOrden.EN_PROCESO.toString());
            }
        }
    }

    // =========================================================
    // DONACIONES / DONANTES / VOLUNTARIOS / UBICACIÓN
    // =========================================================

    @Override
    public void registrarDonante(Donante donante) {
        if (donante == null) return;
        donantesByCodigo.put(donante.getCodigo(), donante);
    }

    @Override
    public List<DonanteDTO> obtenerDonantes() throws DAOException {
        return donantesByCodigo.values().stream()
                .filter(Objects::nonNull)
                .map(this::toDonanteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonanteDTO> obtenerDonantes(String userSolicitante) {
        Usuario u = usuariosByUsername.get(userSolicitante);
        if (u == null) throw new RuntimeException("Usuario no autenticado");

        String roleName = (u.getRol() != null) ? u.getRol().getNombre() : "";

        if ("ADMIN".equalsIgnoreCase(roleName)) {
            return donantesByCodigo.values().stream().map(this::toDonanteDTO).collect(Collectors.toList());
        } else if ("DONANTE".equalsIgnoreCase(roleName)) {
            Donante d = findDonanteByUsername(userSolicitante);
            if (d == null) return Collections.emptyList();
            return Arrays.asList(toDonanteDTO(d));
        } else {
            throw new RuntimeException("No autorizado para ver donantes");
        }
    }

    @Override
    public void registrarVoluntario(Voluntario voluntario) {
        if (voluntario == null) return;
        voluntariosByCodigo.put(voluntario.getCodigo(), voluntario);
    }

    @Override
    public List<VoluntarioDTO> obtenerVoluntarios() {
        return voluntariosByCodigo.values().stream()
                .filter(Objects::nonNull)
                .map(this::toVoluntarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarUbicacion(Ubicacion ubicacion) throws DAOException {
        if (ubicacion == null) return;
        if (ubicacion.getCodigo() != null) ubicacionesByCodigo.put(ubicacion.getCodigo(), ubicacion);
    }

    // Donaciones (DTO)
    @Override
    public void registrarDonacion(DonacionDTO donacion)
            throws DataNullException, DataDoubleException, DataEmptyException, DataObjectException,
                   DataDateException, DAOException, StateChangeException, DataLengthException, DataListException {

        if (donacion == null) throw new DataNullException("DonacionDTO null");

        Donante donante = donantesByCodigo.get(donacion.getCodDonante());
        if (donante == null) throw new DataObjectException("No existe Donante con código: " + donacion.getCodDonante());

        OrdenPedido pedido = null;
        if (donacion.getCodPedido() != null && !donacion.getCodPedido().trim().isEmpty()) {
            pedido = pedidosByCodigo.get(donacion.getCodPedido());
            if (pedido == null) throw new DataObjectException("No existe OrdenPedido con código: " + donacion.getCodPedido());
        }

        ArrayList<Bien> bienesDom = new ArrayList<>();
        if (donacion.getBienes() != null) {
            for (BienDTO b : donacion.getBienes()) {
                if (b == null) continue;
                Bien bienDom = toBien(b);
                bienesDom.add(bienDom);
                bienByCodigo.put(bienDom.getCodigo(), bienDom);
            }
        }

        Donacion dom = new Donacion(
                donacion.getFechaDonacion(),
                donacion.getObservacion(),
                bienesDom,
                donante,
                pedido,
                donacion.getCodigo()
        );

        donacionByCodigo.put(dom.getCodigo(), dom);
    }

    @Override
    public ArrayList<DonacionDTO> obtenerDonaciones()
            throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException {

        ArrayList<DonacionDTO> res = new ArrayList<>();
        for (Donacion d : donacionByCodigo.values()) {
            DonacionDTO dto = toDonacionDTO(d);
            if (dto != null) res.add(dto);
        }
        return res;
    }

    @Override
    public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException {
        ArrayList<DonacionDTO> res = new ArrayList<>();
        for (Donacion d : donacionByCodigo.values()) {
            if (d == null) continue;
            OrdenPedido p = d.getPedido();
            EstadoOrden eo = (p != null && p.getEstado() != null) ? p.getEstado() : EstadoOrden.PENDIENTE;
            if (eo != EstadoOrden.COMPLETADA && eo != EstadoOrden.CANCELADA) {
                DonacionDTO dto = toDonacionDTO(d);
                if (dto != null) res.add(dto);
            }
        }
        return res;
    }

    @Override
    public DonacionDTO obtenerDonacionPorPedido(String ordenP)
            throws DataNullException, DAOException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException {

        if (ordenP == null || ordenP.trim().isEmpty()) throw new DataNullException("codPedido vacío");

        for (Donacion d : donacionByCodigo.values()) {
            if (d == null) continue;
            if (d.getPedido() != null && ordenP.equalsIgnoreCase(d.getPedido().getCodigo())) {
                return toDonacionDTO(d);
            }
        }
        return null;
    }

    @Override
    public DonacionDTO obtenerDonacionDTO(String codPedido)
            throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException {

        if (codPedido == null) throw new DataNullException("codPedido null");
        if (codPedido.trim().isEmpty()) throw new DataEmptyException("codPedido vacío");

        DonacionDTO dto = obtenerDonacionPorPedido(codPedido);
        if (dto == null) throw new DataObjectException("No se encontró donación asociada al pedido: " + codPedido);

        return dto;
    }

    // =========================================================
    // VISITAS
    // =========================================================

    @Override
    public ArrayList<VisitaDTO> obtenerVisitas(String codOrden) {
        ArrayList<VisitaDTO> resultado = new ArrayList<>();
        if (codOrden == null || codOrden.trim().isEmpty()) return resultado;

        for (Visita v : visitasByCodigo.values()) {
            if (v == null) continue;
            String cod = (v.getCodOrdenEntrega() != null) ? v.getCodOrdenEntrega() : v.getCodOrdenRetiro();
            if (cod != null && codOrden.equalsIgnoreCase(cod)) {
                resultado.add(toVisitaDTO(v));
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) {
        if (codVisita == null || codVisita.trim().isEmpty()) return new ArrayList<>();
        Visita v = visitasByCodigo.get(codVisita);
        if (v == null || v.getBienesRecolectados() == null) return new ArrayList<>();
        return v.getBienesRecolectados().stream()
                .filter(Objects::nonNull)
                .map(this::toBienDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void cargarVisita(VisitaDTO visita)
            throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException,
                   DataDateException, DataEmptyException, DataListException, DataObjectException {

        // Router: si es OR -> retiro; si no -> entrega
        if (visita == null) throw new DataNullException("VisitaDTO null");

        // Si tu OrdenRetiro tiene helper esRetiro(cod): lo usamos, sino fallback por prefijo
        boolean esRetiro = false;
        try {
            esRetiro = OrdenRetiro.esRetiro(visita.getCodOrden());
        } catch (Exception e) {
            String c = visita.getCodOrden();
            esRetiro = (c != null && c.toUpperCase().startsWith("OR"));
        }

        if (esRetiro) {
            cargarVisitaRetiro(visita);
        } else {
            cargarVisitaEntrega(visita);
        }
    }

    @Override
    public void cargarVisitaRetiro(VisitaDTO visita)
            throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException,
                   DataDateException, DataEmptyException, DataListException, DataObjectException {

    		Visita v = toVisita(visita);
    	  	OrdenRetiro oR = retirosByCodigo.get(v.getRetiro());
    	    if (oR.getCodigo()!= null) {
    	        
    	        ArrayList<BienDTO> bienesDTO=this.obtenerBienesPorOrdenPedido(oR.getPedido().getCodigo());
    	        oR.setBienesEsperados(this.listBien(bienesDTO));
    	        

    	    
    	        oR.agregarVisita(v);
    	        
    	        
    	        visitasByCodigo.put(v.getCodigo(), v);
    	      
    	        retirosByCodigo.put(oR.getCodigo(), oR);
    	        pedidosByCodigo.put(oR.getPedido().getCodigo(), oR.getPedido());
    
    	        

    	       
    	    } 
    	

	        if (v.tieneBienes()) {
	            for (Bien b : v.getBienesRecolectados()) {
	                registrarBienInventario(b.getCodigo(), b.getTipo(), true);
	            }
	        }

     
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
    @Override
    public void cargarVisitaEntrega(VisitaDTO visita)
            throws DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException,
                   DataDoubleException, StateChangeException, DataObjectException, DAOException {
    	Visita v = this.toVisita(visita);
		OrdenEntrega entrega = entregasByCodigo.get(v.getCodOrdenEntrega());
		
		  if (entrega!= null) {
  	        

  	    
  	        entrega.agregarVisita(v);
  	        
  	        visitasByCodigo.put(v.getCodigo(), v);
  	        entregasByCodigo.put(entrega.getCodigo(), entrega);
  	        solicitudesByCodigo.put(entrega.getSolicitud().getCodigo(), entrega.getSolicitud());

  	       
  	    } 
		  
		 // si la visita entregó bienes: quedan NO disponibles en inventario
		  if (v.tieneBienes()) {
		        for (Bien b : v.getBienesRecolectados()) {
		            modificarBienInventario(b.getCodigo(), b.getTipo(), false);
		        }
		    }
	   
	   //si la entrega finalizo y hay bienes que no fueron entregados, re reincorporan nuevamente en el inventario
		  if (entrega.getEstado() == Orden.EstadoOrden.COMPLETADA) {
		        for (Bien b : entrega.obtenerBienesFaltantes(
		                entrega.getEntregados(),
		                entrega.getSolicitud().getBienesSolicitados()
		        )) {
		            modificarBienInventario(b.getCodigo(), b.getTipo(), true);
		        }
		    } 
    }

    // =========================================================
    // BIENES / INVENTARIO
    // =========================================================

    @Override
    public BienDTO obtenerBien(String codigo) {
        if (codigo == null) return null;
        Bien b = bienByCodigo.get(codigo);
        return (b == null) ? null : toBienDTO(b);
    }

    @Override
    public Bien ObtenerBien(String codigo) throws DataNullException, DAOException {
        if (codigo == null || codigo.trim().isEmpty()) throw new DataNullException("codigo vacío");
        return bienByCodigo.get(codigo);
    }

    @Override
    public List<BienDTO> obtenerTodosLosBienes() throws DAOException {
        return bienByCodigo.values().stream()
                .filter(Objects::nonNull)
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BienDTO> obtenerBienesPorTipo(String tipo) throws DataNullException, DAOException {
        if (tipo == null || tipo.trim().isEmpty()) throw new DataNullException("tipo vacío");
        return bienByCodigo.values().stream()
                .filter(Objects::nonNull)
                .filter(b -> b.getTipo() != null && tipo.equalsIgnoreCase(b.getTipo()))
                .filter(this::bienNoVencido) // “no contempla vencidos, solo válidos”
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void registrarBien(BienDTO bien, Boolean cargarEnInventario)
            throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {

        if (bien == null) throw new DataNullException("BienDTO null");
        Bien dom = toBien(bien);

        bienByCodigo.put(dom.getCodigo(), dom);

        if (Boolean.TRUE.equals(cargarEnInventario)) {
            registrarBienInventario(dom.getCodigo(), dom.getTipo(), true);
        }
    }

    @Override
    public void modificarBien(BienDTO bien)
            throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {

        if (bien == null) throw new DataNullException("BienDTO null");
        Bien dom = toBien(bien);
        bienByCodigo.put(dom.getCodigo(), dom);

        // si está en inventario, mantenemos tipo actualizado (disponible se conserva)
        if (inventarioTipo.containsKey(dom.getCodigo())) {
            inventarioTipo.put(dom.getCodigo(), dom.getTipo());
        }
    }

    @Override
    public void eliminarBien(BienDTO bien)
            throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException, DAOException {

        if (bien == null) throw new DataNullException("BienDTO null");

        String cod = bien.getCodigo();
        if (cod == null || cod.trim().isEmpty()) throw new DataNullException("codigo bien vacío");

        bienByCodigo.remove(cod);
        // también de inventario
        eliminarBienInventario(cod);
    }

    // INVENTARIO

    @Override
    public List<BienDTO> obtenerBienesInventario() throws DAOException {
        // devuelve todos los bienes del inventario (sin mirar disponible)
        return inventario.stream()
                .filter(Objects::nonNull)
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BienDTO> obtenerBienesTipoInventario(String tipo) throws DataNullException, DAOException {
        if (tipo == null || tipo.trim().isEmpty()) throw new DataNullException("tipo vacío");

        return inventario.stream()
                .filter(Objects::nonNull)
                .filter(b -> b.getTipo() != null && tipo.equalsIgnoreCase(b.getTipo()))
                .filter(this::bienNoVencido)
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BienDTO> obtenerBienesDisponiblesInventario() throws DataNullException, DAOException {
        return inventario.stream()
                .filter(Objects::nonNull)
                .filter(b -> Boolean.TRUE.equals(inventarioDisponible.get(b.getCodigo())))
                .filter(this::bienNoVencido)
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BienDTO> obtenerBienesNoDisponiblesInventario() throws DataNullException, DAOException {
        return inventario.stream()
                .filter(Objects::nonNull)
                .filter(b -> Boolean.FALSE.equals(inventarioDisponible.get(b.getCodigo())))
                .filter(this::bienNoVencido)
                .map(this::toBienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarBienInventario(String codBien) throws DAOException {
        if (codBien == null) return;
        inventario.removeIf(b -> b != null && codBien.equalsIgnoreCase(b.getCodigo()));
        inventarioDisponible.remove(codBien);
        inventarioTipo.remove(codBien);
    }

    // compat: en tu clase tenías también "eliminarBineInventario" con typo
    @Override
    public void eliminarBineInventario(String codigo) throws DataNullException, DAOException {
        if (codigo == null || codigo.trim().isEmpty()) throw new DataNullException("codigo vacío");
        eliminarBienInventario(codigo);
    }

    @Override
    public void registrarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException {
        if (codBien == null) return;

        Bien b = bienByCodigo.get(codBien);
        if (b == null) {
            // si no existe en bienes, inventario no puede referenciarlo
            // (en BD esto sería FK). En memoria: no hacemos nada o tiramos.
            throw new DAOException("No existe Bien con código: " + codBien);
        }

        // evitar duplicado en inventario
        boolean exists = inventario.stream().anyMatch(x -> x != null && codBien.equalsIgnoreCase(x.getCodigo()));
        if (!exists) inventario.add(b);

        inventarioDisponible.put(codBien, disponible);
        inventarioTipo.put(codBien, tipoBien != null ? tipoBien : b.getTipo());
    }

    @Override
    public void modificarBienInventario(String codBien, String tipoBien, boolean disponible) throws DAOException {
        if (codBien == null) return;

        Bien b = bienByCodigo.get(codBien);
        if (b == null) throw new DAOException("No existe Bien con código: " + codBien);

        // si no existe en inventario, lo agrega (mínimos cambios)
        boolean exists = inventario.stream().anyMatch(x -> x != null && codBien.equalsIgnoreCase(x.getCodigo()));
        if (!exists) inventario.add(b);

        inventarioDisponible.put(codBien, disponible);
        inventarioTipo.put(codBien, tipoBien != null ? tipoBien : b.getTipo());
    }

    // =========================================================
    // ORDEN ENTREGA: bienes entregados
    // =========================================================

    @Override
    public List<BienDTO> obtenerBienesPorOrdenEntrega(String codigo) throws DAOException {
        OrdenEntrega oe = entregasByCodigo.get(codigo);
        if (oe == null) return new ArrayList<>();
        if (oe.getEntregados() == null) return new ArrayList<>();
        return oe.getEntregados().stream().filter(Objects::nonNull).map(this::toBienDTO).collect(Collectors.toList());
    }

    @Override
    public ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP)
            throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException {

        ArrayList<BienDTO> retirar = new ArrayList<>();
        if (codOP == null || codOP.trim().isEmpty()) return retirar;

        for (Donacion d : donacionByCodigo.values()) {
            if (d == null) continue;
            if (d.getPedido() != null && codOP.equalsIgnoreCase(d.getPedido().getCodigo())) {
                if (d.getBienes() != null) {
                    for (Bien b : d.getBienes()) {
                        if (b != null) retirar.add(toBienDTO(b));
                    }
                }
            }
        }
        return retirar;
    }

    @Override
    public List<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro)
            throws DAOException, DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException {

        ArrayList<BienDTO> resultado = new ArrayList<>();
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) return resultado;

        // bienes de visitas del retiro
        for (Visita v : visitasByCodigo.values()) {
            if (v == null) continue;
            if (v.getCodOrdenRetiro() != null && codOrdenRetiro.equalsIgnoreCase(v.getCodOrdenRetiro())) {
                if (v.getBienesRecolectados() != null) {
                    for (Bien b : v.getBienesRecolectados()) {
                        if (b != null) resultado.add(toBienDTO(b));
                    }
                }
            }
        }
        return resultado;
    }

    // =========================================================
    // SOLICITUDES / BENEFICIARIO
    // =========================================================

    @Override
    public ArrayList<SolicitudBienDTO> obtenerSolicitudesPendientes()
            throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException {

        ArrayList<SolicitudBienDTO> res = new ArrayList<>();
        for (SolicitudBien sb : solicitudesByCodigo.values()) {
            if (sb == null) continue;
            if ("Pendiente".equalsIgnoreCase(sb.getEstado()) || EstadoOrden.PENDIENTE.toString().equalsIgnoreCase(sb.getEstado())) {
                res.add(toSolicitudBienDTO(sb));
            }
        }
        return res;
    }

    @Override
    public BeneficiarioDTO obtenerBeneficiarioDTO(String beneficiario)
            throws DAOException, DataLengthException, DataIntException, DataListException {

        if (beneficiario == null) return null;

        Beneficiario b = beneficiarioByCodigo.get(beneficiario);
        if (b == null) {
            // también puede venir username; intentamos por username
            b = beneficiarioByCodigo.values().stream()
                    .filter(Objects::nonNull)
                    .filter(x -> beneficiario.equalsIgnoreCase(x.getUsername()))
                    .findFirst()
                    .orElse(null);
        }
        return (b == null) ? null : toBeneficiarioDTO(b);
    }

    @Override
    public boolean verificarDisponiblilidad(ArrayList<BienDTO> bienesSolicitados) {
        try {
            if (bienesSolicitados == null) return true;
            for (BienDTO b : bienesSolicitados) {
                if (b == null || b.getCodigo() == null) return false;
                Boolean disp = inventarioDisponible.get(b.getCodigo());
                if (disp == null || !disp) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================
    // USERNAME VOLUNTARIO
    // =========================================================

    @Override
    public String obtenerUsernameVoluntario(String codVoluntario) throws DAOException {
        if (codVoluntario == null) return null;
        Voluntario v = voluntariosByCodigo.get(codVoluntario);
        if (v == null) return null;
        return v.getUsername();
    }

    @Override
    public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) {
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) return null;
        OrdenRetiro or = retirosByCodigo.get(codOrdenRetiro);
        if (or == null || or.getVoluntario() == null) return null;
        return or.getVoluntario().getUsername();
    }

    // =========================================================
    // Inicializar contadores (compat con interface)
    // =========================================================

    @Override
    public void InicializarContadores() {
        // en memoria: tomamos tamaño para “arrancar arriba”
        try { Bien.setContadorBien(bienByCodigo.size()); } catch (Exception ignored) { }
        try { Donacion.setContadorDonacion(donacionByCodigo.size()); } catch (Exception ignored) { }
        try { Donante.setContadorDonante(donantesByCodigo.size()); } catch (Exception ignored) { }
        try { OrdenPedido.setContadorPedido(pedidosByCodigo.size()); } catch (Exception ignored) { }
        try { OrdenRetiro.setContadorOrdenRetiro(retirosByCodigo.size()); } catch (Exception ignored) { }
        try { Visita.setContadorVisita(visitasByCodigo.size()); } catch (Exception ignored) { }
        try { SolicitudBien.setContadorSolicitud(solicitudesByCodigo.size()); } catch (Exception ignored) { }
    }

    // =========================================================
    // “ModificarBienInventario” (la tuya con mayúscula)
    // =========================================================
    @Override
    public void ModificarBienInventario(Bien bien) throws DAOException {
        if (bien == null || bien.getCodigo() == null) return;
        bienByCodigo.put(bien.getCodigo(), bien);

        // si está en inventario, mantenemos
        if (inventarioTipo.containsKey(bien.getCodigo())) {
            inventarioTipo.put(bien.getCodigo(), bien.getTipo());
        }
    }

    // =========================================================
    // Estado pedido
    // =========================================================
    @Override
    public String obtenerEstadoOrdenPedido(String codOrdenPedido) {
        if (codOrdenPedido == null || codOrdenPedido.trim().isEmpty()) return null;
        OrdenPedido op = pedidosByCodigo.get(codOrdenPedido);
        if (op == null) return null;
        return (op.getEstado() != null) ? op.getEstado().toString() : EstadoOrden.PENDIENTE.toString();
    }

    // =========================================================
    // Helpers privados (DTO / Entidad)
    // =========================================================

    private void putUsuario(Usuario u) {
        if (u == null) return;
        if (u.getCodigo() != null) usuariosByCodigo.put(u.getCodigo(), u);
        if (u.getUsuario() != null) usuariosByUsername.put(u.getUsuario(), u);
    }

    private Rol buscarRol(Integer codigo) {
        return rolesByCodigo.get(codigo);
    }

    private UsuarioDTO toUsuarioDTO(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(
                u.getUsuario(),
                null, // no enviar password
                u.getNombre(),
                u.getContacto(),
                u.getRol() != null ? u.getRol().getNombre() : null,
                u.isActivo(),
                u.obtenerEstado(),
                u.getCodigo()
        );
    }

    private UsuarioDTO toUsuarioDTOFull(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(
                u.getUsuario(),
                u.getContrasena(),
                u.getNombre(),
                u.getContacto(),
                u.getRol() != null ? u.getRol().getNombre() : null,
                u.isActivo(),
                u.obtenerEstado(),
                u.getCodigo()
        );
    }

    private DonanteDTO toDonanteDTO(Donante d) {
        if (d == null) return null;
        return new DonanteDTO(
                d.getNombre(),
                d.getCodigo(),
                d.getApellido(),
                d.getContacto(),
                null,
                d.getUbicacion() != null ? d.getUbicacion().getCodigo() : null,
                null
        );
    }

    private VoluntarioDTO toVoluntarioDTO(Voluntario v) {
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
                v.getUsername()
        );
    }

    public BienDTO toBienDTO(Bien bien) {
        if (bien == null) return null;
        return new BienDTO(
                bien.getCodigo(),
                bien.getTipo(),
                bien.getPeso(),
                bien.getNombre(),
                bien.getDescripcion(),
                bien.getNivelNecesidad(),
                bien.getFechaVencimiento(),
                bien.getTalle(),
                bien.getMaterial()
        );
    }

    private Bien toBien(BienDTO dto)
            throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException {

        if (dto == null) return null;
        return new Bien(
                dto.getCodigo(),
                dto.getTipo(),
                dto.getPeso(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaVencimiento(),
                dto.getTalle(),
                dto.getMaterial()
        );
    }

    private Donante findDonanteByUsername(String username) {
        if (username == null) return null;
        return donantesByCodigo.values().stream()
                .filter(Objects::nonNull)
                .filter(d -> username.equalsIgnoreCase(d.getUsername()))
                .findFirst()
                .orElse(null);
    }

    private Voluntario findVoluntarioByCodigoOrUsername(String cod) {
        if (cod == null) return null;
        for (Voluntario v : voluntariosByCodigo.values()) {
            if (v == null) continue;
            if (cod.equalsIgnoreCase(v.getCodigo())) return v;
            if (v.getUsername() != null && cod.equalsIgnoreCase(v.getUsername())) return v;
        }
        return null;
    }

    private DonacionDTO toDonacionDTO(Donacion donacion) {
        if (donacion == null) return null;

        ArrayList<BienDTO> bienesDTO = new ArrayList<>();
        ArrayList<Bien> bs = donacion.getBienes();
        if (bs != null) {
            for (Bien b : bs) if (b != null) bienesDTO.add(toBienDTO(b));
        }

        return new DonacionDTO(
                donacion.getCodigo(),
                donacion.getFechaDonacion(),
                donacion.getObservacion(),
                bienesDTO,
                donacion.getDonante() != null ? donacion.getDonante().getCodigo() : null,
                donacion.getPedido() != null ? donacion.getPedido().getCodigo() : null,
                null
        );
    }

    private Visita toVisita(VisitaDTO dto)
            throws DataNullException, DataLengthException, DataDoubleException, DataDateException,
                   DataEmptyException, DataListException, StateChangeException {

        if (dto == null) return null;

        ArrayList<Bien> recolectados = new ArrayList<>();
        if (dto.getBienesRecolectados() != null) {
            for (BienDTO b : dto.getBienesRecolectados()) {
                if (b == null) continue;
                recolectados.add(toBien(b));
            }
        }

        boolean esFinal = dto.isEsFinal();
        // constructor equivalente al que usás en persistence (código puede venir)
        Visita v = new Visita(
                dto.getCodigo(),
                dto.getFechaVisita(),
                dto.getObservaciones(),
                dto.getTipo(),
                dto.getCodOrden(),
                recolectados,
                esFinal,
                dto.getEstado()
        );
        return v;
    }

    private VisitaDTO toVisitaDTO(Visita visita) {
        if (visita == null) return null;

        ArrayList<BienDTO> bienesDTO = new ArrayList<>();
        if (visita.getBienesRecolectados() != null) {
            for (Bien b : visita.getBienesRecolectados()) {
                if (b != null) bienesDTO.add(toBienDTO(b));
            }
        }

        String codOrden = visita.getCodOrdenEntrega() != null ? visita.getCodOrdenEntrega() : visita.getCodOrdenRetiro();

        return new VisitaDTO(
                visita.getCodigo(),
                visita.getFechaVisita(),
                null,
                codOrden,
                bienesDTO,
                visita.getObservaciones(),
                visita.getTipo(),
                visita.isEsFinal(),
                visita.getEstado()
        );
    }

    private OrdenPedidoDTO toOrdenPedidoDTO(OrdenPedido op) {
        if (op == null) return null;
        return new OrdenPedidoDTO(
                op.getFechaEmision(),
                op.getEstado() != null ? op.getEstado().toString() : EstadoOrden.PENDIENTE.toString(),
                op.getCodigo(),
                op.isCargaPesada(),
                op.getObservaciones(),
                op.getCodDonacion()
        );
    }

    private OrdenRetiroDTO toOrdenRetiroDTO(OrdenRetiro or) {
        if (or == null) return null;

        String codPedido = (or.getPedido() != null) ? or.getPedido().getCodigo() : null;
        String codVol = (or.getVoluntario() != null) ? or.getVoluntario().getCodigo() : null;
        String[] codVisitas = or.getCodVisitas();

        return new OrdenRetiroDTO(
                or.getFechaEmision(),
                or.getEstado() != null ? or.getEstado().toString() : EstadoOrden.PENDIENTE.toString(),
                or.getCodigo(),
                codPedido,
                codVol,
                codVisitas
        );
    }

    private OrdenEntregaDTO toOrdenEntregaDTO(OrdenEntrega orden) {
        if (orden == null) return null;

        ArrayList<VisitaDTO> visitasDTO = new ArrayList<>();
        if (orden.getVisitas() != null) {
            for (Visita v : orden.getVisitas()) visitasDTO.add(toVisitaDTO(v));
        }

        ArrayList<BienDTO> entregadosDTO = new ArrayList<>();
        if (orden.getEntregados() != null) {
            for (Bien b : orden.getEntregados()) entregadosDTO.add(toBienDTO(b));
        }

        SolicitudBienDTO solicitudDTO = (orden.getSolicitud() != null) ? toSolicitudBienDTO(orden.getSolicitud()) : null;
        BeneficiarioDTO beneficiarioDTO = (orden.getBeneficiario() != null) ? toBeneficiarioDTO(orden.getBeneficiario()) : null;
        VoluntarioDTO voluntarioDTO = (orden.getVoluntario() != null) ? toVoluntarioDTO(orden.getVoluntario()) : null;

        return new OrdenEntregaDTO(
                orden.getFechaEmision(),
                orden.getEstadoString(),
                orden.getCodigo(),
                orden.getFechaHoraProgramada(),
                visitasDTO,
                entregadosDTO,
                solicitudDTO,
                beneficiarioDTO,
                voluntarioDTO
        );
    }

    private OrdenEntrega toOrdenEntrega(OrdenEntregaDTO dto)
            throws DataNullException, DataLengthException, DataDateException, DataEmptyException, DataListException,
                   DataDoubleException, StateChangeException, DataObjectException, DAOException {

        ArrayList<Visita> visitas = new ArrayList<>();
        if (dto.getVisitas() != null) {
            for (VisitaDTO vDTO : dto.getVisitas()) {
                visitas.add(toVisita(vDTO));
            }
        }

        Beneficiario beneficiario = null;
        if (dto.getBeneficiario() != null) {
            beneficiario = toBeneficiario(dto.getBeneficiario());
        }

        Voluntario voluntario = null;
        if (dto.getVoluntario() != null) {
            voluntario = toVoluntario(dto.getVoluntario());
        }

        SolicitudBien solicitud = null;
        if (dto.getSolicitud() != null) {
            solicitud = toSolicitudBien(dto.getSolicitud());
        }

        return new OrdenEntrega(
                dto.getFechaEmision(),
                dto.getEstado().toString(),
                dto.getCodigo(),
                dto.getFechaHoraProgramada(),
                visitas,
                solicitud,
                beneficiario,
                voluntario
        );
    }

    private SolicitudBienDTO toSolicitudBienDTO(SolicitudBien solicitud) {
        if (solicitud == null) return null;

        ArrayList<BienDTO> bienesDTO = new ArrayList<>();
        if (solicitud.getBienesSolicitados() != null) {
            for (Bien b : solicitud.getBienesSolicitados()) {
                if (b != null) bienesDTO.add(toBienDTO(b));
            }
        }

        return new SolicitudBienDTO(
                solicitud.getCodigo(),
                solicitud.getBeneficiario(),
                bienesDTO,
                solicitud.getEstado()
        );
    }

    private BeneficiarioDTO toBeneficiarioDTO(Beneficiario beneficiario) {
        if (beneficiario == null) return null;

        UbicacionDTO ubicacionDTO = (beneficiario.getUbicacion() != null) ? toUbicacionDTO(beneficiario.getUbicacion()) : null;

        return new BeneficiarioDTO(
                beneficiario.getNombre(),
                beneficiario.getApellido(),
                beneficiario.getFecha_nac(),
                beneficiario.getDni(),
                beneficiario.getContacto(),
                ubicacionDTO,
                beneficiario.getCodigo(),
                beneficiario.getUsername(),
                beneficiario.getCantAcargo(),
                beneficiario.getPrioridad()
        );
    }

    private UbicacionDTO toUbicacionDTO(Ubicacion ubicacion) {
        if (ubicacion == null) return null;

        CoordenadaDTO coord = null;
        if (ubicacion.getCoordenada() != null) {
            coord = new CoordenadaDTO(
                    ubicacion.getCoordenada().getLatitud(),
                    ubicacion.getCoordenada().getLongitud(),
                    ubicacion.getCoordenada().getCodigo()
            );
        }

        return new UbicacionDTO(
                ubicacion.getCodigo(),
                ubicacion.getZona(),
                ubicacion.getBarrio(),
                ubicacion.getDireccion(),
                coord
        );
    }

    private Beneficiario toBeneficiario(BeneficiarioDTO dto) {
        if (dto == null) return null;

        Ubicacion ubicacion = (dto.getUbicacion() != null) ? toUbicacion(dto.getUbicacion()) : null;

        try {
            // en memoria: no cargamos entregas/solicitudes pesadas si no están
            return new Beneficiario(
                    dto.getNombre(),
                    dto.getApellido(),
                    dto.getFecha_nac(),
                    dto.getDni(),
                    dto.getContacto(),
                    ubicacion,
                    dto.getUsername(),
                    dto.getCantAcargo(),
                    dto.getPrioridad()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo BeneficiarioDTO a Beneficiario", e);
        }
    }

    private SolicitudBien toSolicitudBien(SolicitudBienDTO dto)
            throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException {

        if (dto == null) return null;

        ArrayList<Bien> bienes = new ArrayList<>();
        if (dto.getBienesSolicitados() != null) {
            for (BienDTO bDTO : dto.getBienesSolicitados()) {
                if (bDTO != null) bienes.add(toBien(bDTO));
            }
        }

        return new SolicitudBien(
                dto.getCodigo(),
                dto.getBeneficiario(),
                bienes,
                dto.getEstado()
        );
    }

    private Ubicacion toUbicacion(UbicacionDTO dto) {
        if (dto == null) return null;
        try {
            CoordenadaDTO c = dto.getCoordenada();
            Coordenada coord = (c != null) ? new Coordenada(c.getLatitud(), c.getLongitud(), c.getCodigo()) : null;
            return new Ubicacion(dto.getZona(), dto.getBarrio(), dto.getDireccion(), coord);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo UbicacionDTO a Ubicacion", e);
        }
    }

    private Voluntario toVoluntario(VoluntarioDTO dto)
            throws DataEmptyException, DataObjectException, DataNullException, DataDateException, DataLengthException, DataListException, DAOException {

        if (dto == null) throw new DataNullException("VoluntarioDTO null");

        Voluntario v = new Voluntario(
                dto.getNombre(),
                dto.getApellido(),
                dto.getFecha_nac(),
                dto.getContacto(),
                dto.getDni(),
                dto.getUsername(),
                dto.getCodigo(),
                new ArrayList<>()
        );
        v.setDisponible(dto.isDisponible());
        return v;
    }

    private boolean bienNoVencido(Bien b) {
        if (b == null) return false;
        LocalDate fv = b.getFechaVencimiento();
        if (fv == null) return true; // ropa/mueble/etc sin vencimiento
        return !fv.isBefore(LocalDate.now());
    }

	@Override
	public void registrarVisita(Visita visita) throws DAOException, DataNullException, DataLengthException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarOrdenPedido(OrdenPedido orden) throws DataNullException, DAOException, DataObjectException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String obtenerUserVoluntario(String codOrden) throws DAOException {
		   OrdenRetiro OR=null;
		   OrdenEntrega OE=null;
		   String codVoluntario=null;
			if(OrdenRetiro.esRetiro(codOrden)) {
				OR = retirosByCodigo.get(codOrden);
				codVoluntario=OR.getVoluntario().getUsername();
			}else {
				OE= entregasByCodigo.get(codOrden);
				codVoluntario=OE.getVoluntario().getUsername();
				
			}
			
			return codVoluntario;
			
		}
}



