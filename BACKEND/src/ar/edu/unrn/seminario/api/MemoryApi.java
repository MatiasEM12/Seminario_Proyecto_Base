package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;
import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.dto.*;


public class MemoryApi implements IApi {

    private List<Rol> roles = new ArrayList<>();


    
    //username-objeto
    private Map<String, Usuario> usuariosByUsername = new HashMap<>();
    private Map<String, Donante> donantesByUser = new HashMap<>();
    private Map<String, Voluntario> voluntariosByUser = new HashMap<>();
    private Map<String, Ubicacion> ubicacionesByCodigo = new HashMap<>();
    
  
    private ArrayList<BienDTO> bienes = new ArrayList<>();
    private ArrayList<VisitaDTO> visitas = new ArrayList<>();
    private List<OrdenPedido> ordenes = new ArrayList<>();
    private List<Donacion> donaciones = new ArrayList<>();
    private List<OrdenRetiro> ordenesRetiro = new ArrayList<>();
    private List<Voluntario> voluntarios = new ArrayList<>();
    private List<Visita> visitass = new ArrayList<>();

    public MemoryApi() throws DataNullException, StateChangeException, DataEmptyException, DataObjectException, DataDateException, DataLengthException {
        // datos iniciales
    	
    	
        this.roles.add(new Rol(1, "ADMIN"));
        
        /*
        this.roles.add(new Rol(2, "ESTUDIANTE"));
        this.roles.add(new Rol(3, "INVITADO"));*/
        // agregar roles necesarios para dominio
        
        this.roles.add(new Rol(2, "DONANTE"));
        this.roles.add(new Rol(3, "VOLUNTARIO"));
        //this.roles.add(new Rol(6, "BENEFICIARIO"));

        activarRol(1);
        activarRol(2);
        activarRol(3);
        //activarRol(6);

        inicializarUsuarios();
        inicializarOrdenesPedido();
        inicializarVoluntarios();
  
    }

    //USUARIOS 
    private void inicializarUsuarios()  {
        try {
        	
            registrarUsuario("admin", "1234", "admin@x.com", "Admin", 1,true);/*
            registrarUsuario("ldifabio", "4", "ldifabio@unrn.edu.ar", "Lucas", 2);
            registrarUsuario("bjgorosito", "1234", "bjgorosito@unrn.edu.ar", "Bruno", 3);*/
        	
        	
        	
            // crear ejemplo de donante / voluntario para pruebas
            registrarUsuario("pedro_don", "p", "pedro@x.com", "Pedro.C", 2,true);
            registrarUsuario("matias_don", "m", "matias@x.com", "Matias. M", 2,false);
            registrarUsuario("ian_don", "i", "ian@x.com", "Ian.H", 2,false);
            registrarUsuario("juan_vol", "v", "juan@x.com", "Juan Vol", 3,true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void registrarUsuario(String username, String password, String email, String nombre, Integer rol,boolean activo) throws DataEmptyException, DataObjectException, DataNullException, DataDateException, DataLengthException {

   
        if (!existeUsuario(username)) {
            Rol role = this.buscarRol(rol);
            Usuario usuario = new Usuario(username, password, nombre, email, role,activo,null);
            this.usuariosByUsername.put(username, usuario);
            
            // mapUsuarios.put(username, password); // eliminado

            // crear perfil básico según rol
            String rn = role.getNombre();
            if ("DONANTE".equalsIgnoreCase(rn)) {
            	Coordenada cor=new Coordenada(11111,11111);
            	Ubicacion ubicacion= new Ubicacion("lugar", "de", "prueba", cor);
                // atributos por defecto mínimos; se pueden completar luego con editar perfil
                Donante d = new Donante(nombre, "jose", LocalDate.now(), "34453544",username,ubicacion);
                donantesByUser.put(username, d);
            } else if ("VOLUNTARIO".equalsIgnoreCase(rn)) {
            	Voluntario v= new Voluntario(nombre, "pepe", LocalDate.now(),email,"34453544",username);
                voluntariosByUser.put(username, v);
            }
            
            
        }
    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u : this.usuariosByUsername.values()) {
            dtos.add(new UsuarioDTO(u.getUsuario(), null /*no enviar password*/, u.getNombre(), u.getContacto(),
                    u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(),u.getCodigo()));
        }
        return dtos;
    }

    @Override
    public UsuarioDTO obtenerUsuario(String username) {

        Usuario u = usuariosByUsername.get(username);
        if (u != null) {
            return 	new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getContacto(),
                    u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(),u.getCodigo());
        }
        return null;
    }

    @Override
    public void eliminarUsuario(String username) {

    	 this.usuariosByUsername.remove(username);
        // borrar perfiles asociados:
        donantesByUser.remove(username);
        voluntariosByUser.remove(username);

    }

    @Override
    public void activarUsuario(String usuario) throws StateChangeException {
        Usuario user = this.buscarUsuario(usuario);
        if (user != null) user.activar();
    }

    @Override
    public void desactivarUsuario(String usuario) throws StateChangeException {
        Usuario user = this.buscarUsuario(usuario);
        if (user != null) user.desactivar();
    }


    private Usuario buscarUsuario(String usuario) {
        return usuariosByUsername.get(usuario);
    }

    public Boolean existeUsuario(String username ) {
    
        return this.usuariosByUsername.containsKey(username);
    }

    
	@Override
	public List<UsuarioDTO> obtenerUserDonantes() {
		
		
	    List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u : this.usuariosByUsername.values()) {
        	Rol r=u.getRol();
        	if("DONANTE".equalsIgnoreCase(r.getNombre())){
        		
        		  dtos.add(new UsuarioDTO(u.getUsuario(), null /*no enviar password*/, u.getNombre(), u.getContacto(),
                          u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(),u.getCodigo()));
        	}
            
        }
        return dtos;
	}

	@Override
	public List<UsuarioDTO> obtenerUserVoluntarios() {
		  List<UsuarioDTO> dtos = new ArrayList<>();
	        for (Usuario u : this.usuariosByUsername.values()) {
	        	Rol r=u.getRol();
	        	if("VOLUNTARIO".equalsIgnoreCase(r.getNombre())){
	        		
	        		  dtos.add(new UsuarioDTO(u.getUsuario(), null /*no enviar password*/, u.getNombre(), u.getContacto(),
	                          u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(),u.getCodigo()));
	        	}
	            
	        }
	        return dtos;
	}

	@Override
	public List<UsuarioDTO> obtenerUserAdministrador() {
		  List<UsuarioDTO> dtos = new ArrayList<>();
	        for (Usuario u : this.usuariosByUsername.values()) {
	        	Rol r=u.getRol();
	        	if("ADMIN".equalsIgnoreCase(r.getNombre())){
	        		
	        		  dtos.add(new UsuarioDTO(u.getUsuario(), null /*no enviar password*/, u.getNombre(), u.getContacto(),
	                          u.getRol().getNombre(), u.isActivo(), u.obtenerEstado(),u.getCodigo()));
	        	}
	            
	        }
	        return dtos;
	}


    // ROLES
    @Override
    public List<RolDTO> obtenerRoles() {
        List<RolDTO> dtos = new ArrayList<>();
        for (Rol r : this.roles) {
            dtos.add(new RolDTO(r.getCodigo(), r.getNombre(), r.isActivo(), r.getDescripcion()));
        }
        return dtos;
    }

    @Override
    public List<RolDTO> obtenerRolesActivos() {
        List<RolDTO> dtos = new ArrayList<>();
        for (Rol r : this.roles) {
            if (r.isActivo())
                dtos.add(new RolDTO(r.getCodigo(), r.getNombre(), r.getDescripcion()));
        }
        return dtos;
    }

    @Override
    public void guardarRol(Integer codigo, String nombre, String descripcion, boolean estado) throws DataNullException {
        Rol rol = new Rol(codigo, nombre, descripcion, estado);
        this.roles.add(rol);
    }

    @Override
    public void guardarRol(RolDTO rol) throws DataNullException {
        Rol rolnew = new Rol(rol.getCodigo(), rol.getNombre(), rol.getDescripcion(), rol.isActivo());
        this.roles.add(rolnew);
    }

    @Override
    public RolDTO obtenerRolPorCodigo(Integer codigo) {
        for (Rol rol : this.roles) {
            if (codigo.equals(rol.getCodigo())) {
                return new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo(), rol.getDescripcion());
            }
        }
        return null;
    }

    @Override
    public void activarRol(Integer codigo) throws StateChangeException {
        Rol rol = this.buscarRol(codigo);
        if (rol != null) rol.activar();
    }

    @Override
    public void desactivarRol(Integer codigo) throws StateChangeException {
        Rol rol = this.buscarRol(codigo);
        if (rol != null) rol.desactivar();
    }

    private Rol buscarRol(Integer codigo) {
        for (Rol rol : roles) {
            if (rol.getCodigo().equals(codigo))
                return rol;
        }
        return null;
    }


    // API

    @Override
    public void modificarContraseña(String usuario, String passWord) throws DataEmptyException, DataNullException, DataLengthException {

        Usuario user = this.buscarUsuario(usuario);
        if (user != null) {
            user.setContrasena(passWord);
           
        }
    }



    @Override
    public Boolean autenticar(String username, String password) {
        if (username == null || password == null) return false;
        Usuario u = this.usuariosByUsername.get(username);
        if (u == null) return false;
        return password.equals(u.getContrasena());
    }

    
    
    
    // Pre-Carga Orden Pedido
    private void inicializarOrdenesPedido() {
        try {
   
        	ArrayList<Bien> bienes1 = new ArrayList<>();
        	bienes1.add(new Bien(null,"Alimento", 0.200,"Manteca", "Manteca sin sal", 2, LocalDate.now(), 0, null));
        	Bien b2 = new Bien(null,"Ropa", 0.200,"Camisa","Camisa de ToyStory 23",1,null, 5.0,"algodon");
        	
        	bienes1.add(b2);
            // crear donante ejemplo si no existe
            Donante donante1 = donantesByUser.get("pedro_don");
            
            Donacion donacion1 = new Donacion(LocalDate.now(), "Entrega en sede central", bienes1, donante1,null,null);
            
            OrdenPedido ordenPedido =  new OrdenPedido(LocalDate.now(), true, "Entrega urgente", donante1.getCodigo(), donacion1.getCodigo());
            donacion1.setPedido(ordenPedido);
            donacion1.setCodigo(ordenPedido.getCodigo());
            
            registrarDonacion(donacion1);
            registrarOrdenPedido(ordenPedido);
            //aqui esta el error
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al inicializar órdenes: " + e.getMessage());
        }
    }

  //pre-carfa OrdenRetiro
  	public void inicializarOrdenesRetiro(String codPedido) throws DataNullException, DataObjectException, DataListException, DataDateException, DataEmptyException {
  		if (codPedido == null || codPedido.trim().isEmpty()) {
  	        return;
  	    }

  	    OrdenRetiro ordenRetiro = null;
  	    // Intentar obtener una orden de retiro existente asociada al pedido.
  	    try {
  	        ordenRetiro = this.obtenerOrdenRetiroPorPedido(codPedido);
  	    } catch (RuntimeException e) {
  	        // Si no existe, la creamos a partir del OrdenPedido (si existe)
  	        try {
  	            OrdenPedido pedido = this.obtenerOrdenPedidoPorCodigo(codPedido);
  	            ordenRetiro = new OrdenRetiro(LocalDate.now(), pedido, null); 
  	            // registrar la nueva orden de retiro en la lista
  	            this.ordenesRetiro.add(ordenRetiro);
  	        } catch (RuntimeException ex2) {
  	            // No existe pedido con ese código: abortar inicialización
  	            System.out.println("No se encontró pedido para inicializar orden retiro: " + codPedido);
  	            return;
  	        }
  	    }

  	    
  	    // Asignar voluntario de ejemplo si no tiene
  	    try {
  	        if (ordenRetiro.getVoluntario() == null) {
  	            ordenRetiro.setVoluntario(this.obtenerVoluntarioPorUsername("juan_vol"));
  	        }
  	    } catch (Exception e) {
  	        // evitar fallos si no existe el voluntario
  	    }
  	    
  	}
  	
  	
  	private void inicializarVisitas(OrdenRetiro retiro) throws DataNullException, DataLengthException, StateChangeException, DataListException, DataDateException, DataEmptyException {
  	    if (retiro == null) {
  	        return;
  	    }
  	    Visita v1 = new Visita(LocalDate.now(),"El donante estaba ausente","RETIRO",retiro.getCodigo(),new ArrayList<>(),false);
  	    v1.setEstado("fallida");

  	    // Convertir bienes a DTO
  	    ArrayList<BienDTO> bienesDTOv1 = new ArrayList<>();
  	    VisitaDTO dto1 = new VisitaDTO(v1.getCodigo(),v1.getFechaVisita(),null
  	    		,retiro.getCodigo(),bienesDTOv1,v1.getObservaciones(),v1.getTipo(),false);

  	    this.visitas.add(dto1);

  	    // Segunda visita (exitosa, con bienes del retiro)
  	    ArrayList<BienDTO> bienesDTOv2 = new ArrayList<>();
  	    for (Bien b : retiro.getRecolectados()) {
  	        bienesDTOv2.add(toBienDTO(b));
  	    }

  	    Visita v2 = new Visita(LocalDate.now(),"Retiro realizado","RETIRO",retiro.getCodigo(),retiro.getRecolectados(),true);
  	    v2.setEstado("realizada");
  	    
  	    VisitaDTO dto2 = new VisitaDTO(v2.getCodigo(),v2.getFechaVisita(),null
  	    		,retiro.getCodigo(),bienesDTOv2,v2.getObservaciones(),v2.getTipo(),true);
  	    this.visitas.add(dto2);

  	    ArrayList<Visita> lista = new ArrayList<>();
  	    lista.add(v1);
  	    lista.add(v2);
  	    retiro.setVisitas(lista);
  	}
    
    // ORDEN PEDIDO

    public void registrarOrdenPedido(OrdenPedido orden) {
        ordenes.add(orden);
    }
    public ArrayList<OrdenPedidoDTO> obtenerOrdenesPedido() {
        ArrayList<OrdenPedidoDTO> ordenesDTO = new ArrayList<>();
        OrdenPedido orden;
        for (int i = 0; i < ordenes.size(); i++) {
            orden = ordenes.get(i);
            ordenesDTO.add( new OrdenPedidoDTO(orden.getFechaEmision(),orden.getEstado().toString(),OrdenPedido.getTipo(),orden.getCodigo(),orden.isCargaPesada(), orden.getObservaciones(),orden.getCodDonante(),orden.getCodDonacion()));
           
        }
        return ordenesDTO;
    }
   
	
    // OrdenRetiro

	

    
    
	@Override
	public ArrayList<OrdenRetiroDTO> obtenerOrdenesRetiro() {
		ArrayList<OrdenRetiroDTO> ordenesDTO = new ArrayList<>();
	    for (OrdenRetiro orden : ordenesRetiro) {
	        if (orden != null) {
	            ordenesDTO.add(new OrdenRetiroDTO(
	                orden.getFechaEmision(),
	                orden.getEstado().toString(),
	                OrdenRetiro.getTipo(),
	                orden.getCodigo(),
	                orden.getPedido() != null ? orden.getPedido().getCodigo() : null,
	                null,
	                orden.getCodVisitas()
	            ));
	        }
	    }
	    return ordenesDTO;
	}
	

    // Donacion

    public void registrarDonacion(Donacion donacion) {
        donaciones.add(donacion);
    }

    public ArrayList<DonacionDTO> obtenerDonaciones() {
        ArrayList<DonacionDTO> donacionesDTO = new ArrayList<>();
        Donacion donacion;

        for (int i = 0; i < donaciones.size(); i++) {
            donacion = donaciones.get(i);
            ArrayList<BienDTO> bienesDTO = new ArrayList<>();
            ArrayList<Bien> bienes = donacion.getBienes();
            if (bienes != null) {
                for (Bien bien : bienes) {
                    if (bien != null) {
                        bienesDTO.add(toBienDTO(bien));
                    }
                }
            }
            donacionesDTO.add(new DonacionDTO(donacion.getCodigo(), donacion.getFechaDonacion(), 
            		donacion.getObservacion(),bienesDTO,donacion.getDonante().getCodigo(),donacion.getPedido().getCodigo(),null));
        }
        return donacionesDTO;
    }

    // Donante: registro y obtención

    public void registrarDonante(Donante donante) {
    	
        if (donante != null && donante.getUsername() != null) {
            donantesByUser.put(donante.getUsername(), donante);
        }
    }

    /*
     * Método con autorización. ADMIN obtiene todos; DONANTE/Voluntario obtiene solo su propio DTO;
     * otros roles no están autorizados a obtener la lista de donantes completos.
     */
    public List<DonanteDTO> obtenerDonantes(String userSolicitante) {
    	
        Usuario u = usuariosByUsername.get(userSolicitante);
        if (u == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        
        String roleName = u.getRol().getNombre();

        if ("ADMIN".equalsIgnoreCase(roleName)) {
        	
            return donantesByUser.values().stream().map(this::toDonanteDTO).collect(Collectors.toList());
            
        } else if ("DONANTE".equalsIgnoreCase(roleName)) {
        	
            Donante d = donantesByUser.get(userSolicitante);
            if (d == null) { 
            	return Collections.emptyList();
            } else {
            	 return Arrays.asList(toDonanteDTO(d));
            }
            
        } else  {
            throw new RuntimeException("No autorizado para ver donantes");
        }
    }




    // Helpers DTO
    private DonanteDTO toDonanteDTO(Donante donante) {
        return new DonanteDTO(donante.getNombre(), donante.getCodigo(), donante.getApellido(),
                donante.getContacto(), null, donante.getUbicacion().getCodigo(), null);
    }
    
    private Bien toBien(BienDTO dto) throws DataNullException, DataDoubleException, StateChangeException, DataLengthException, DataDateException {
		if (dto == null) return null;
		return new Bien(
			dto.getCodigo(),
			dto.getTipo(),
			dto.getPeso(),
			dto.getNombre(),
			dto.getDescripcion(),
			dto.getNivelNecesidad(),
			dto.getFechaVencimiento(),
			dto.getTalle() != null ? dto.getTalle() : 0.0,
			dto.getMaterial()
		);
	}

	private Donante findDonanteByCodigo(String codigoDonante) {
		if (codigoDonante == null) return null;
		for (Donante d : donantesByUser.values()) {
			if (d != null && codigoDonante.equalsIgnoreCase(d.getCodigo())) return d;
		}
		return null;
	}

	private OrdenPedido findOrdenPedidoByCodigo(String codPedido) {
		if (codPedido == null) return null;
		for (OrdenPedido op : ordenes) {
			if (op != null && codPedido.equalsIgnoreCase(op.getCodigo())) return op;
		}
		return null;
	}

	private Voluntario findVoluntarioByCodigoOrUsername(String cod) {
		if (cod == null) return null;
		for (Voluntario v : voluntariosByUser.values()) {
			if (v == null) continue;
			try {
				if (cod.equalsIgnoreCase(v.getCodigo())) return v;
			} catch (Exception e) {
				// ignore
			}
			try {
				if (v.getUsername() != null && cod.equalsIgnoreCase(v.getUsername())) return v;
			} catch (Exception e) {
				// ignore
			}
		}
		return null;
	}

	private DonacionDTO toDonacionDTO(Donacion donacion) {
		if (donacion == null) return null;
		ArrayList<BienDTO> bienesDTO = new ArrayList<>();
		ArrayList<Bien> bs = donacion.getBienes();
		if (bs != null) {
			for (Bien b : bs) {
				if (b != null) bienesDTO.add(toBienDTO(b));
			}
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

	private Visita toVisita(VisitaDTO dto) throws DataNullException, DataLengthException, DataDoubleException, DataDateException, DataEmptyException, DataListException, StateChangeException {
		if (dto == null) return null;
		ArrayList<Bien> recolectados = new ArrayList<>();
		if (dto.getBienesRecolectados() != null) {
			for (BienDTO b : dto.getBienesRecolectados()) {
				if (b != null) {
					try {
						recolectados.add(toBien(b));
					} catch (Exception e) {
						// Si un bien no se puede crear, dejamos que falle la carga por consistencia
						throw e;
					}
				}
			}
		}
		// esFinal: si viene null, asumimos false
		boolean esFinal = dto.isEsFinal();
		Visita v = new Visita(dto.getFechaVisita(), dto.getObservaciones(), dto.getTipo(), dto.getCodOrdenRetiro(), recolectados, esFinal);
		if (dto.getCodigo() != null && !dto.getCodigo().trim().isEmpty()) {
			try { v.setCodigo(dto.getCodigo()); } catch (Exception e) { /* ignore */ }
		}
		return v;
	}

    //Orden

	@Override
	public List<OrdenDTO> obtenerOrdenes() {
		
		  // Lista que contendrá todas las órdenes (Pedido y Retiro)
	    List<OrdenDTO> todasOrdenes = new ArrayList<>();

	    // Agregar órdenes de pedido si existen
	    List<OrdenPedidoDTO> pedidos = obtenerOrdenesPedido();
	    if (pedidos != null && !pedidos.isEmpty()) {
	        todasOrdenes.addAll(pedidos);
	    }

	    // Agregar órdenes de retiro si existen
	    List<OrdenRetiroDTO> retiros = obtenerOrdenesRetiro();
	    if (retiros != null && !retiros.isEmpty()) {
	        todasOrdenes.addAll(retiros);
	    }

	    // Retorna la lista completa; puede estar vacía si no hay órdenes

		return todasOrdenes;
	}

	public Voluntario obtenerVoluntarioPorUsername(String username) {
	    if (username == null || username.isEmpty()) {
	        throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío");
	    }

	    Voluntario v = voluntariosByUser.get(username);
	    if (v == null) {
	        throw new RuntimeException("No se encontró voluntario con username: " + username);
	    }

	    return v;
	}


	public OrdenPedido obtenerOrdenPedidoPorCodigo(String codigo) {
	    if (codigo == null || codigo.isEmpty()) {
	        throw new IllegalArgumentException("El código no puede ser nulo o vacío");
	    }

	    for (OrdenPedido orden : ordenes) {
	        if (codigo.equals(orden.getCodigo())) {
	            return orden;
	        }
	    }

	    throw new RuntimeException("No se encontró OrdenPedido con código: " + codigo);
	}

	public String obtenerUsernameVoluntarioPorOrdenRetiro(String codOrdenRetiro) {
	    if (codOrdenRetiro == null || codOrdenRetiro.isEmpty()) {
	        throw new IllegalArgumentException("El código de la orden de retiro no puede ser nulo o vacío");
	    }

	    for (OrdenRetiro orden : ordenesRetiro) {
	        if (codOrdenRetiro.equals(orden.getCodigo())) {
	            Voluntario v = orden.getVoluntario();
	            if (v == null) {
	                throw new RuntimeException("La orden de retiro existe pero no tiene voluntario asignado: " + codOrdenRetiro);
	            }
	            
	            return v.getUsername();
	            
	        }
	    }

	    throw new RuntimeException("No se encontró OrdenRetiro con código: " + codOrdenRetiro);
	}
	
	public ArrayList<BienDTO> obtenerBienesDeVisita(String codVisita) {
	    if (codVisita == null || codVisita.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    // Buscar la visita 
	    VisitaDTO visitaEncontrada = visitas.stream()
	        .filter(Objects::nonNull)
	        .filter(v -> codVisita.equalsIgnoreCase(v.getCodigo()))
	        .findFirst()
	        .orElse(null);

	    if (visitaEncontrada == null || visitaEncontrada.getBienesRecolectados() == null) {
	        return new ArrayList<>();
	    }
	 
	   return visitaEncontrada.getBienesRecolectados();
	        
	}
	
	public String[] obtenerCodigosDeBien(BienDTO bien) {
	    if (bien == null || bien.getCodigo() == null) {
	        return new String[0]; // retorna array vacío si no hay bien o código
	    }

	    String[] cod = { bien.getCodigo() };
	    return cod;
	}
	public ArrayList<VisitaDTO> obtenerVisitas(String codOrdenRetiro){
	    ArrayList<VisitaDTO> resultado = new ArrayList<>();

	    if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
	        return resultado; // vacío si no se pasó código válido
	    }

	    for (VisitaDTO v : this.visitas) {
	        if (v == null) continue;
	        String cod = v.getCodOrdenRetiro();
	        if (cod != null && codOrdenRetiro.equalsIgnoreCase(cod)) {
	            resultado.add(v);
	        }
	    }

	    return resultado;
	}

	public ArrayList<BienDTO> obtenerBienesPorOrdenRetiro(String codOrdenRetiro) {
	    if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	   return ordenesRetiro.stream() // Convierte la lista de órdenes de retiro en un flujo (Stream)
	            .filter(Objects::nonNull) // Elimina las referencias nulas
	            .filter(o -> codOrdenRetiro.equalsIgnoreCase(o.getCodigo())) // Busca la orden con el código indicado (sin importar mayúsculas/minúsculas)
	            .findFirst() // Obtiene la primera coincidencia (devuelve un Optional<OrdenRetiro>)
	            .map(OrdenRetiro::getRecolectados) // Si existe, obtiene la lista de bienes recolectados (Optional<List<Bien>>)
	            .map(list -> list.stream() // Convierte esa lista en un Stream<Bien>
	                    .filter(Objects::nonNull) // Elimina los bienes nulos
	                    .map(this::toBienDTO) // Convierte cada Bien a BienDTO usando el método toBienDTO()
	                    .collect(Collectors.toCollection(ArrayList::new))) // Junta los resultados en un ArrayList<BienDTO>
	            .orElseGet(ArrayList::new); // Si no se encontró la orden o la lista es nula, devuelve una lista vacía
	}

	
	public OrdenRetiro obtenerOrdenRetiroPorPedido(String codPedido) {
	    if (codPedido == null || codPedido.trim().isEmpty()) {
	        throw new IllegalArgumentException("El código del pedido no puede ser nulo o vacío");
	    }

	    for (OrdenRetiro retiro : ordenesRetiro) {
	        if (retiro != null && retiro.getPedido() != null 
	            && codPedido.equalsIgnoreCase(retiro.getPedido().getCodigo())) {
	            return retiro;
	        }
	    }

	    throw new RuntimeException("No se encontró OrdenRetiro asociada al pedido con código: " + codPedido);
	}

	@Override
	
	public ArrayList<BienDTO> obtenerBienesPorOrdenPedido(String codOP) {
		 ArrayList<BienDTO> retirar = new ArrayList<>();
		   if (codOP == null || codOP.trim().isEmpty()) {
		        return retirar;
		    }

		    for (Donacion donacion : donaciones) {
		        if (donacion != null) {
		            String codPedidoDonacion = donacion.getCodigo();

		            if (codPedidoDonacion != null && codOP.equalsIgnoreCase(codPedidoDonacion)) {
		                ArrayList<Bien> bienesDonacion = donacion.getBienes();

		                if (bienesDonacion != null) {
		                    for (Bien bien : bienesDonacion) {
		                        if (bien != null) {
		                            BienDTO dto = toBienDTO(bien);
		                           
		                            
		                            retirar.add(dto);
		                        }
		                    }
		                }
		                // Si una orden tiene una sola donación, podés cortar acá
		                // break;
		            }
		        }
		    }

         return retirar; 
	}
	public ArrayList<BienDTO> obtenerBienesPorDonacion(String codDonacion) {
	    if (codDonacion == null || codDonacion.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    ArrayList<BienDTO> resultado = donaciones.stream() // Convierte la lista de donaciones en un flujo (Stream)
	            .filter(Objects::nonNull) // Filtra las donaciones que no sean nulas
	            .filter(d -> codDonacion.equalsIgnoreCase(d.getCodigo())) // Filtra la donación que coincida con el código dado (sin importar mayúsculas/minúsculas)
	            .findFirst() // Obtiene la primera donación que cumpla con la condición (devuelve un Optional<Donacion>)
	            .map(Donacion::getBienes) // Si existe, obtiene su lista de bienes (devuelve Optional<List<Bien>>)
	            .map(List::stream) // Convierte esa lista de bienes en un Stream 
	            .orElseGet(Stream::empty) // Si no se encontró la donación, devuelve un Stream vacío
	            .filter(Objects::nonNull) // Filtra los bienes no nulos
	            .map(bien -> toBienDTO(bien)) // Convierte cada Bien a un BienDTO mediante el método toBienDTO()
	            .collect(Collectors.toCollection(ArrayList::new)); // Recolecta el resultado en un ArrayList<BienDTO>

	    return resultado;
	}

	
	//HELPERS 
	
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

	@Override
	public void guardarRol(Integer codigo, String nombre, boolean estado) throws DataNullException {
		  Rol rol = new Rol(codigo, nombre, estado);
	        this.roles.add(rol);
		
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol)
			throws DataEmptyException, DataNullException, DataExistsException, DataObjectException, DataLengthException {
		
			// En MemoryApi no persistimos en BD: validamos y delegamos al método principal.
			if (username == null) {
				throw new DataNullException("username nulo");
			}
			if (existeUsuario(username)) {
				throw new DataExistsException("Ya existe el usuario: " + username);
			}
			// Por defecto, el usuario queda activo (igual que en una alta típica)
			try {
				registrarUsuario(username, password, email, nombre, codigoRol, true);
			} catch (DataDateException e) {
				// No debería ocurrir en el alta simple; re-lanzamos como objeto inválido
				throw new DataObjectException(e.getMessage());
			}
	}

	@Override
	public void registrarVoluntario(Voluntario voluntario) {
		voluntarios.add(voluntario);
		
	}

	@Override
	public List<VoluntarioDTO> obtenerVoluntarios() {
		ArrayList<VoluntarioDTO> voluntariosDTO = new ArrayList<>();
        Voluntario voluntario;
        for (int i = 0; i < voluntarios.size(); i++) {
            voluntario = voluntarios.get(i);
            voluntariosDTO.add( new VoluntarioDTO(voluntario.getNombre(),voluntario.getApellido(),voluntario.getContacto()
            		,voluntario.getDni(),voluntario.getFecha_nac(),voluntario.getCodigo(),voluntario.isDisponible()));
           
        }
        return voluntariosDTO;
       
	}
	public void registrarVisita(VisitaDTO visita) {
		visitas.add(visita);
	}
	public void registrarVisita(Visita visita) {
		visitass.add(visita);
	}
	
	public void inicializarVoluntarios() throws DataEmptyException, DataObjectException, DataNullException, DataDateException, DataLengthException {
		Voluntario v1=new Voluntario(
		        "Matias",               // nombre
		        "Mellado", LocalDate.now(),          // apellido
		        "WhatsApp","123456",            // preferencia de contacto
		        "matiM"            // username);
		        );
		
		registrarVoluntario(v1);
	}

	@Override
	public String obtenerEstadoOrdenPedido(String codOrdenPedido) {
		if (codOrdenPedido == null || codOrdenPedido.trim().isEmpty()) {
			return null;
		}
		for (OrdenPedido op : ordenes) {
			if (op != null && codOrdenPedido.equalsIgnoreCase(op.getCodigo())) {
				return op.getEstado() != null ? op.getEstado().toString() : EstadoOrden.PENDIENTE.toString();
			}
		}
		return null;
	}


	@Override
	public void completarOrdenRetiro(String codOrdenRetiro) throws Exception {
		if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
			throw new DataNullException("codigo de orden de retiro nulo/vacío");
		}
		OrdenRetiro target = null;
		for (OrdenRetiro or : ordenesRetiro) {
			if (or != null && codOrdenRetiro.equalsIgnoreCase(or.getCodigo())) {
				target = or;
				break;
			}
		}
		if (target == null) {
			throw new DAOException("No existe OrdenRetiro con código: " + codOrdenRetiro);
		}
		// Intentamos pasar a COMPLETADA respetando las reglas del modelo
		try {
			// Si está en PENDIENTE, primero la pasamos a EN_PROCESO
			if (target.getEstado() != null && target.getEstado().toString().equalsIgnoreCase(EstadoOrden.PENDIENTE.toString())) {
				target.ordenEstadoProceso();
			}
			target.ordenEstadoCompleta();
		} catch (StateChangeException e) {
			throw e;
		}
	}


	@Override
	public void registrarOrdenRetiro(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DataObjectException, DataListException, DataDateException, DataEmptyException {

		if (retiro == null) {
			throw new DataNullException("OrdenRetiroDTO es nula");
		}

		OrdenPedido pedido = findOrdenPedidoByCodigo(retiro.getPedido());
		if (pedido == null) {
			throw new DataNullException("No existe OrdenPedido con código: " + retiro.getPedido());
		}

		Voluntario voluntario = null;
		if (retiro.getCodVoluntario() != null && !retiro.getCodVoluntario().trim().isEmpty()) {
			voluntario = findVoluntarioByCodigoOrUsername(retiro.getCodVoluntario());
		}

		ArrayList<Visita> visitasOR = new ArrayList<>();
		if (retiro.getCodVisitas() != null) {
			for (String codV : retiro.getCodVisitas()) {
				if (codV == null) continue;
				for (Visita v : visitass) {
					if (v != null && codV.equalsIgnoreCase(v.getCodigo())) {
						visitasOR.add(v);
						break;
					}
				}
			}
		}

		String estado = (retiro.getEstado() != null) ? retiro.getEstado().toString() : EstadoOrden.PENDIENTE.toString();
		String codigo = retiro.getCodigo(); // si es null el modelo generará uno

		OrdenRetiro or = new OrdenRetiro(codigo, estado, retiro.getFechaEmision(), voluntario, pedido, visitasOR);

		// Evitar duplicados por código
		for (OrdenRetiro existente : ordenesRetiro) {
			if (existente != null && or.getCodigo() != null && or.getCodigo().equalsIgnoreCase(existente.getCodigo())) {
				return;
			}
		}
		ordenesRetiro.add(or);
	}


	@Override
	public void registrarOrdenPedido(OrdenPedidoDTO orden) throws DataNullException {
		if (orden == null) {
			throw new DataNullException("OrdenPedidoDTO inválida");
		}
		try {
			OrdenPedido op = new OrdenPedido(
				orden.getCodigo(),
				orden.getFechaEmision(),
				orden.getObservaciones(),
				orden.isCargaPesada(),
				orden.getCodDonante()
			);

			// Si el DTO trae estado, lo aplicamos
			if (orden.getEstado() != null) {
				EstadoOrden nuevo = orden.getEstado();
				if (nuevo != null) {
					op.setEstado(nuevo);
				}
			}

			ordenes.add(op);
		} catch (Exception e) {
			// En MemoryApi consolidamos como DataNullException para no cambiar la firma
			throw new DataNullException(e.getMessage());
		}
	}


	@Override
	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) throws DAOException {
		if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
			return null;
		}
		for (OrdenRetiro orden : ordenesRetiro) {
			if (orden != null && codOrdenRetiro.equalsIgnoreCase(orden.getCodigo())) {
				return new OrdenRetiroDTO(
					orden.getFechaEmision(),
					orden.getEstado() != null ? orden.getEstado().toString() : EstadoOrden.PENDIENTE.toString(),
					OrdenRetiro.getTipo(),
					orden.getCodigo(),
					orden.getPedido() != null ? orden.getPedido().getCodigo() : null,
					orden.getVoluntario() != null ? orden.getVoluntario().getCodigo() : null,
					orden.getCodVisitas()
				);
			}
		}
		return null;
	}


	@Override
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException {
		ArrayList<DonacionDTO> res = new ArrayList<>();
		for (Donacion d : donaciones) {
			if (d == null) continue;
			OrdenPedido p = d.getPedido();
			// Consideramos pendiente cuando el pedido no está COMPLETADA ni CANCELADA
			EstadoOrden eo = (p != null && p.getEstado() != null) ? p.getEstado() : EstadoOrden.PENDIENTE;
			if (eo != EstadoOrden.COMPLETADA && eo != EstadoOrden.CANCELADA) {
				DonacionDTO dto = toDonacionDTO(d);
				if (dto != null) res.add(dto);
			}
		}
		return res;
	}


	@Override
	public DonacionDTO obtenerDonacion(String ordenP) throws DataNullException {
		if (ordenP == null || ordenP.trim().isEmpty()) {
			throw new DataNullException("codigo de orden/pedido nulo/vacío");
		}
		for (Donacion d : donaciones) {
			if (d == null) continue;
			if (d.getPedido() != null && ordenP.equalsIgnoreCase(d.getPedido().getCodigo())) {
				return toDonacionDTO(d);
			}
		}
		return null;
	}


	@Override
	public BienDTO obtenerBien(String codigo) {
		for (BienDTO b : bienes) {
		    if (b.getCodigo().equals(codigo)) return b;
		}
		return null;
	}

	@Override
	public void cargarVisita(VisitaDTO visita)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException, DataDateException, DataEmptyException, DataListException, DataObjectException {

		if (visita == null) {
			throw new DataNullException("VisitaDTO es nula");
		}

		Visita v = toVisita(visita);

		// Guardar en listas
		visitass.add(v);
		visitas.add(visita);

		// Asociar a OrdenRetiro
		OrdenRetiro or = null;
		for (OrdenRetiro item : ordenesRetiro) {
			if (item != null && visita.getCodOrdenRetiro() != null
					&& visita.getCodOrdenRetiro().equalsIgnoreCase(item.getCodigo())) {
				or = item;
				break;
			}
		}

		if (or == null) {
			throw new DAOException("No existe OrdenRetiro con código: " + visita.getCodOrdenRetiro());
		}

		// Agregar visita a la orden
		or.agregarVisita(v);

		// Incorporar bienes recolectados a inventario (lista bienes DTO)
		if (visita.getBienesRecolectados() != null) {
			for (BienDTO b : visita.getBienesRecolectados()) {
				if (b != null) {
					// evitar duplicados por código
					boolean exists = false;
					for (BienDTO inv : bienes) {
						if (inv != null && inv.getCodigo() != null && inv.getCodigo().equalsIgnoreCase(b.getCodigo())) {
							exists = true;
							break;
						}
					}
					if (!exists) bienes.add(b);
				}
			}
		}

		// Si es visita final, intentamos marcar la OR como COMPLETADA (respetando reglas)
		if (visita.isEsFinal()) {
			try {
				if (or.getEstado() != null && or.getEstado().toString().equalsIgnoreCase(EstadoOrden.PENDIENTE.toString())) {
					or.ordenEstadoProceso();
				}
				or.ordenEstadoCompleta();
			} catch (StateChangeException e) {
				// no frenamos la carga, pero propagamos si se quiere manejar arriba
				throw e;
			}
		}
	}


	@Override
	public void registrarDonacion(DonacionDTO donacion)
			throws DataNullException, DataDoubleException, DataEmptyException, DataObjectException, DataDateException, DAOException, StateChangeException, DataLengthException, DataListException {

		if (donacion == null) {
			throw new DataNullException("DonacionDTO es nula");
		}

		Donante donante = findDonanteByCodigo(donacion.getCodDonante());
		if (donante == null) {
			throw new DataObjectException("No existe Donante con código: " + donacion.getCodDonante());
		}

		OrdenPedido pedido = findOrdenPedidoByCodigo(donacion.getCodPedido());
		if (pedido == null) {
			throw new DataObjectException("No existe OrdenPedido con código: " + donacion.getCodPedido());
		}

		ArrayList<Bien> bienesDom = new ArrayList<>();
		if (donacion.getBienes() != null) {
			for (BienDTO b : donacion.getBienes()) {
				if (b != null) {
					Bien bienDom = toBien(b);
					bienesDom.add(bienDom);

					// También lo registramos en el inventario (DTO) para búsquedas/listados
					boolean exists = false;
					for (BienDTO inv : bienes) {
						if (inv != null && inv.getCodigo() != null && inv.getCodigo().equalsIgnoreCase(b.getCodigo())) {
							exists = true;
							break;
						}
					}
					if (!exists) bienes.add(b);
				}
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

		donaciones.add(dom);
	}


	@Override
	public void registrarUbicacion(Ubicacion ubicacion) throws DAOException {
		if (ubicacion == null) return;
		// En memoria simplemente la registramos por código (si existe)
		try {
			if (ubicacion.getCodigo() != null) {
				ubicacionesByCodigo.put(ubicacion.getCodigo(), ubicacion);
			}
		} catch (Exception e) {
			// Si falla por algún motivo, no bloqueamos
		}
	}


	@Override
	public List<DonanteDTO> obtenerDonantes() throws DAOException {
		return donantesByUser.values().stream()
				.filter(Objects::nonNull)
				.map(this::toDonanteDTO)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}


	@Override
	public void eliminarBineInventario(String codigo) throws DataNullException, DAOException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new DataNullException("codigo de bien nulo/vacío");
		}
		bienes.removeIf(b -> b != null && codigo.equalsIgnoreCase(b.getCodigo()));
	}


	@Override
	public List<BienDTO> obtenerTodosLosBienes() throws DAOException {
		return new ArrayList<>(bienes);
	}


	@Override
	public List<BienDTO> obtenerBienesPorTipo(String tipo) throws DataNullException, DAOException {
		if (tipo == null || tipo.trim().isEmpty()) {
			throw new DataNullException("tipo nulo/vacío");
		}
		return bienes.stream()
				.filter(Objects::nonNull)
				.filter(b -> b.getTipo() != null && tipo.equalsIgnoreCase(b.getTipo()))
				.collect(Collectors.toList());
	}


	@Override
	public void InicializarContadores() {
		// En memoria: ajustamos contadores estáticos según lo actualmente cargado.
		try { Bien.setContadorBien(bienes != null ? bienes.size() : 0); } catch (Exception e) { }
		try { Coordenada.setContadorCoordenada(ubicacionesByCodigo != null ? ubicacionesByCodigo.size() : 0); } catch (Exception e) { }
		try { Donacion.setContadorDonacion(donaciones != null ? donaciones.size() : 0); } catch (Exception e) { }
		try { Donante.setContadorDonante(donantesByUser != null ? donantesByUser.size() : 0); } catch (Exception e) { }
		try { OrdenPedido.setContadorPedido(ordenes != null ? ordenes.size() : 0); } catch (Exception e) { }
		try { OrdenRetiro.setContadorOrdenRetiro(ordenesRetiro != null ? ordenesRetiro.size() : 0); } catch (Exception e) { }
	}


	@Override
	public void ModificarBienInventario(Bien bien) throws DAOException {
		if (bien == null) return;
		String codigo = bien.getCodigo();
		if (codigo == null) return;
		BienDTO dto = toBienDTO(bien);
		boolean replaced = false;
		for (int i = 0; i < bienes.size(); i++) {
			BienDTO b = bienes.get(i);
			if (b != null && codigo.equalsIgnoreCase(b.getCodigo())) {
				bienes.set(i, dto);
				replaced = true;
				break;
			}
		}
		if (!replaced) {
			bienes.add(dto);
		}
	}


	@Override
	public Bien ObtenerBien(String codigo) throws DataNullException, DAOException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new DataNullException("codigo nulo/vacío");
		}
		for (BienDTO b : bienes) {
			if (b != null && codigo.equalsIgnoreCase(b.getCodigo())) {
				try {
					return toBien(b);
				} catch (Exception e) {
					throw new DAOException(e.getMessage());
				}
			}
		}
		return null;
	}

	public void registrarOrdenRetiro(OrdenRetiro retiroO) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
	       ordenesRetiro.add(retiroO);
	       
	       //simula que cada ves que pongas una nueva orden de retiro aga su visita
	       try {
	           inicializarVisitas(retiroO);
	       } catch (Exception e) {
	           // si falla la carga de visitas de prueba, continuamos
	       }
	}

	@Override
	public void registrarOrdenRetiro1(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException, DAOException, DataObjectException, DataListException, DataDateException, DataEmptyException {

		if (retiro == null) {
			throw new DataNullException("OrdenRetiroDTO es nula");
		}

		OrdenPedido pedido = findOrdenPedidoByCodigo(retiro.getPedido());
		if (pedido == null) {
			throw new DataNullException("No existe OrdenPedido con código: " + retiro.getPedido());
		}

		Voluntario voluntario = null;
		if (retiro.getCodVoluntario() != null && !retiro.getCodVoluntario().trim().isEmpty()) {
			voluntario = findVoluntarioByCodigoOrUsername(retiro.getCodVoluntario());
		}

		ArrayList<Visita> visitasOR = new ArrayList<>();
		if (retiro.getCodVisitas() != null) {
			for (String codV : retiro.getCodVisitas()) {
				if (codV == null) continue;
				for (Visita v : visitass) {
					if (v != null && codV.equalsIgnoreCase(v.getCodigo())) {
						visitasOR.add(v);
						break;
					}
				}
			}
		}

		String estado = (retiro.getEstado() != null) ? retiro.getEstado().toString() : EstadoOrden.PENDIENTE.toString();
		String codigo = retiro.getCodigo(); // si es null el modelo generará uno

		OrdenRetiro or = new OrdenRetiro(codigo, estado, retiro.getFechaEmision(), voluntario, pedido, visitasOR);

		// Evitar duplicados por código
		for (OrdenRetiro existente : ordenesRetiro) {
			if (existente != null && or.getCodigo() != null && or.getCodigo().equalsIgnoreCase(existente.getCodigo())) {
				return;
			}
		}
		ordenesRetiro.add(or);
	}


	
}

