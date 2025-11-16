package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
public class TestAcceso {
	public static void main(String[] args) throws Exception {
		IApi api = new PersistenceApi();
		
		RolDao rolDao = new RolDAOJDBC();
		List<Rol> roles = rolDao.findAll();

		for (Rol rol : roles) {
			System.out.println(rol);
		}
		

		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		// cambiarlo para que recupere uno de los roles que estan en la base de datos
		Usuario usuario = new Usuario("ldifabio", "1234", "Lucas", "ldifabio@unrn.edu.ar", new Rol(1, "Admin"));
		usuarioDao.create(usuario);

		List<Usuario> usuarios = usuarioDao.findAll();
			for (Usuario u: usuarios) {
			System.out.println(u);
		}
			
		System.out.println(usuarioDao.find("ldifabio"));
		
		// Creamos un donante
		Ubicacion ubic = new Ubicacion( "San Martín 100", "Viedma","-40.8112", "-63.0005");
		DonanteDao donanteDAO = new DonanteDAOJDBC();
		Donante donante=new Donante( "Carlos",
                "González",
                LocalDate.of(1990, 1, 20),
                "33123456",
                "carlos@mail.com",
                ubic);
		
		//donanteDAO.create(donante);
		api.registrarDonante(donante);
		System.out.println("Donante registrado: " + donante.getCodigo());
		//Creamos Donacion con bienes
		ArrayList<Bien> bienes = new ArrayList<>();
		Bien manteca = new Bien("Manteca", "Alimento", 0.200, "Tregar", "Manteca sin sal", 2, LocalDate.now(), 0, "Alimento");
        Bien aceite = new Bien("Aceite", "Botella 1L", 1.00, "Cocinero", "Aceite de cocina", 1, LocalDate.now().plusMonths(6), 0, "ALIMENTO");
        bienes.add(aceite);
        bienes.add(manteca);
        Donacion donacion = new Donacion(
                LocalDate.now(),
                "Donación alimentos básicos",
                bienes,
                donante,
                null   // NO hay pedido vinculado todavía
        );
       //DonacionDAO donacionDAO=new DonacionDAOJDBC();
       
       System.out.println("Donación registrada: " + donacion.getCodigo());
       
       //Generamos una orden de pedido
       OrdenPedido pedido=new OrdenPedido(                            
               LocalDate.now(),true,
               "Pedido generado automáticamente",
               donante.getCodigo(),
               donacion.getCodigo()
               );
       donacion.setPedido(pedido);
       api.registrarDonacion(donacion);
       api.registrarOrdenPedido(pedido);
       System.out.println("OrdenPedido creada");
       
       //Generamos la orden Retiro
       OrdenRetiro retiro= new OrdenRetiro(LocalDate.now(),pedido,null);
       OrdenRetiroDTO retiroDTO=new OrdenRetiroDTO(retiro.getFechaEmision(),"Pendiente","",retiro.getCodigo(),pedido.getCodigo(),"22",null);
       System.out.println("OrdenRetiro creada");
       //Creamos Visita
       ArrayList<Visita> visitas=new ArrayList<>();
       Visita visita1=new Visita(LocalDate.of(2025,10, 05),"No estaba en casa","",retiro.getCodigo(),null,false);
       ArrayList<Bien> bienesRecolectados = new ArrayList<>();
       bienesRecolectados.add(aceite);
       Visita visita2=new Visita(LocalDate.of(2025,10, 05),"El retiro fue exitoso","",retiro.getCodigo(),bienesRecolectados,true);
       visitas.add(visita1);
       visitas.add(visita2);
       retiro.setVisitas(visitas);
     
       api.registrarOrdenRetiro(retiro);
       api.registrarVisita(visita1);
       api.registrarVisita(visita2);
	}
	

}
