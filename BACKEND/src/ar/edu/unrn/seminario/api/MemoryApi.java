package ar.edu.unrn.seminario.api;

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
    
  
    private ArrayList<BienDTO> bienes = new ArrayList<>();
    private ArrayList<VisitaDTO> visitas = new ArrayList<>();
    private List<OrdenPedido> ordenes = new ArrayList<>();
    private List<Donacion> donaciones = new ArrayList<>();
    private List<OrdenRetiro> ordenesRetiro = new ArrayList<>();
    private List<Voluntario> voluntarios = new ArrayList<>();
    private List<Visita> visitass = new ArrayList<>();

    public MemoryApi() throws DataNullException, StateChangeException, DataEmptyException, DataObjectException, DataDateException {
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
    public void registrarUsuario(String username, String password, String email, String nombre, Integer rol,boolean activo) throws DataEmptyException, DataObjectException, DataNullException, DataDateException {

   
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
    public void modificarContraseña(String usuario, String passWord) {

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
        	System.out.print("aqui1");
        	bienes1.add(new Bien("","Alimento", 0.200,"Manteca", "Manteca sin sal", 2, LocalDate.now(), 0, ""));
        	Bien b2 = new Bien("","Ropa", 0.200,"Camisa","Camisa de ToyStory 23",1,null, 5.0,"algodon");
        	System.out.print("aqui1");
        	
        	bienes1.add(b2);
            // crear donante ejemplo si no existe
            Donante donante1 = donantesByUser.get("pedro_don");
            
            Donacion donacion1 = new Donacion(LocalDate.now(), "Entrega en sede central", bienes1, donante1,null,null);
            
            OrdenPedido ordenPedido =  new OrdenPedido(LocalDate.now(), true, "Entrega urgente", donante1.getCodigo(), donacion1.getCodigo());
            donacion1.setCodigo(ordenPedido.getCodigo());
            
            registrarDonacion(donacion1);
            registrarOrdenPedido(ordenPedido);
           //aqui esta el error
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al inicializar órdenes: " + e.getMessage());
        }
    }

  //pre-carfa OrdenRetiro
  	public void inicializarOrdenesRetiro(String codPedido) throws DataNullException {
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
  	
  	
    private void inicializarVisitas(OrdenRetiro retiro) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		  if (retiro == null) return;

		
	
		    // crear bienes ejemplo
		    ArrayList<Bien> bienesComida = new ArrayList<>();
		    Bien b1 = new Bien(null,"Alimento",0,"Manteca", "Manteca sin sal",0, LocalDate.now(),0,null );
		    Bien b2 = new Bien(null,"Ropa",0,"Camisa", "Camisa de ToyStory 23",0,null, 5.0, "algodon");
	
		  

		    bienesComida.add(b1);
		    ArrayList<Bien> bienesRopa = new ArrayList<>();
		    bienesRopa.add(b2);

		    // primera visita (fallida, sin bienes)
		    Visita v1 = new Visita(LocalDate.now(), "Primera visita el donante se encontraba ausente", "RETIRO", retiro.getCodigo(), new ArrayList<Bien>(),false);


		    v1.setEstado("fallida");

		    // segunda visita (completada) con bienes
		    ArrayList<Bien> bienesSegundaVisita = new ArrayList<>();
		    bienesSegundaVisita.addAll(bienesComida);
		    bienesSegundaVisita.addAll(bienesRopa);
		    retiro.agregarBien(b1);
		    retiro.agregarBien(b2);
		    Visita v2 = new Visita(LocalDate.now(), "Segunda visita retiro realizado correctamente", "RETIRO", "we", bienesSegundaVisita,true);
		    v2.setEstado("realizada");//

		    // asociar visitas a la orden (entidad)
		    ArrayList<Visita> visitasParaOrden = new ArrayList<>();
		    visitasParaOrden.add(v1);
		   visitasParaOrden.add(v2); //
		    retiro.setVisitas(visitasParaOrden);

		    // registrar bienes como DTOs para que obtenerBienesDeVisita los encuentre
		    for (Bien bien : retiro.getRecolectados()) {

		        // Verificamos que el bien no sea nulo antes de crear su DTO
		        if (bien != null) {

		            // Creamos el DTO del bien 
		            BienDTO dto = toBienDTO(bien);
		            // Verificamos si ya existe un bien con el mismo código en la lista 'bienes'
		            boolean existe = false;
		            for (BienDTO b : this.bienes) {
		                if (b.getCodigo().equalsIgnoreCase(dto.getCodigo())) {
		                    existe = true;
		                    break;
		                }
		            }

		            // Si el bien no existe todavía, lo agregamos a la lista
		            if (!existe) {
		                this.bienes.add(dto);
		            }
		        }
		    }

		    // crear y agregar VisitaDTOs para que ListadoVisitas las muestre
		    // v1 (sin bienes)
	/*	    VisitaDTO dtoV1 = new VisitaDTO(
		        v1.getCodigo(),
		        v1.getFechaVisita(),
		        v1.getObservaciones(),
		        v1.getTipo(),
		        retiro.getCodigo(),
		        new String[0], // no hay bienes
		        v1.getEstado()
		    );
		    this.visitas.add(dtoV1);

		    // v2 (con bienes -> usar los códigos de los BienDTOs)
		    // construir arreglo de códigos
		    String[] codigosBienes = bienesSegundaVisita.stream()
		        .filter(Objects::nonNull)
		        .map(Bien::getCodigo)
		        .toArray(String[]::new);

	  VisitaDTO dtoV2 = new VisitaDTO(
		        v2.getCodigo(),
		        v2.getFechaVisita(),
		        v2.getObservaciones(),
		        v2.getTipo(),
		        retiro.getCodigo(),
		        codigosBienes,
		        v2.getEstado()
		    );
		    this.visitas.add(dtoV2);

		    OrdenPedido pedido= (retiro.getPedido());
		    pedido.setEstado(EstadoOrden(this.visitas.get(this.visitas.size() - 1).getEstado()  ));
		    retiro.setEstado(EstadoOrden(this.visitas.get(this.visitas.size() - 1).getEstado()  ));
	   }

    private EstadoOrden EstadoOrden(String estado) {
    	
    	if(estado.equals(Orden.EstadoOrden.COMPLETADA.toString())) {
    		 return Orden.EstadoOrden.COMPLETADA;	
    	}else if(estado.equals(Orden.EstadoOrden.EN_PROCESO.toString())) {
    		 return Orden.EstadoOrden.EN_PROCESO;	
    	}else if(estado.equals(Orden.EstadoOrden.PENDIENTE.toString())) {
    	     return Orden.EstadoOrden.PENDIENTE;
    	}
    		return Orden.EstadoOrden.CANCELADA;	
  */  	
    
       
      
      
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

	public void registrarOrdenRetiro(OrdenRetiro oR) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
	       ordenesRetiro.add(oR);
	       //simula que cada ves que pongas una nueva orden de retiro aga su visita
	       inicializarVisitas(oR);
	}
	

    
    
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


	public void registrarOrdenRetiro(OrdenRetiroDTO orden) throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		
		OrdenRetiro oR= new OrdenRetiro(orden.getFechaEmision(),this.obtenerOrdenPedidoPorCodigo(orden.getPedido()),null);
		  ordenesRetiro.add(oR);
		  inicializarVisitas(oR);
		
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
			throws DataEmptyException {
		// TODO Auto-generated method stub
		
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
	
	public void inicializarVoluntarios() throws DataEmptyException, DataObjectException, DataNullException, DataDateException {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void completarOrdenRetiro(String codOrdenRetiro) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarOrdenRetiro1(OrdenRetiroDTO retiro)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarOrdenPedido(OrdenPedidoDTO orden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrdenRetiroDTO obtenerOrdenRetiro(String codOrdenRetiro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DonacionDTO> obtenerDonacionesPendientes() throws DataNullException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DonacionDTO obtenerDonacion(String ordenP) throws DataNullException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BienDTO obtenerBien(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cargarVisita(VisitaDTO visita)
			throws DataNullException, DataLengthException, DataDoubleException, StateChangeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarDonacion(DonacionDTO donacion) throws DataNullException, DataDoubleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarUbicacion(Ubicacion ubicacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DonanteDTO> obtenerDonantes() {
		// TODO Auto-generated method stub
		return null;
	}


	
}

