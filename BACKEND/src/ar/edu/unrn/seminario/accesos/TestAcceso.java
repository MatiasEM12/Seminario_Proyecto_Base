package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
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

	try (Connection conn = ConnectionManager.getConnection()) {
	           
		System.out.println("......prueba de contenido Usuarios y Roles......");
		
	
		for (Rol rol : roles) {
			System.out.println(rol);
		}
		

		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		
		List<Usuario> usuarios = usuarioDao.findAll();
			for (Usuario u: usuarios) {
			System.out.println(u);
		}
			
	System.out.println("......prueba inserccion para la simulacion......");
	
		Coordenada coordenada= new Coordenada(3.454,5.234);
		Ubicacion ubicacion= new Ubicacion ( "Zona_Prueba","Barrio_Prueba","234_dir_prueba",coordenada); 
		api.registrarUbicacion(ubicacion);
		
		api.registrarUsuario("user_prueba", "1223", "prueba@gmail.com", "Nombre_Prueba",3, true);
		Donante donantePrueba = new Donante("Nombre_Prueba","Apellido_Prueba",LocalDate.of(2000, 20, 2),"11111222","prueba@gmail.com",ubicacion,"user_prueba");
		api.registrarDonante(donantePrueba);
		
		ArrayList<Bien> bienes1 = new ArrayList<>();
    	bienes1.add(new Bien(null,"Alimento",0.200,"Manteca","Manteca sin sal", 1, LocalDate.of(2026, 1, 17), 0,null));
    	bienes1.add( new Bien(null,"Ropa",0.2, "Camisa","Camisa de ToyStory 23",2,null, 5.0,"algodon"));

    	Donacion donacionPrueba= new Donacion(LocalDate.now(),"Un alimento y Una camisa para donar",bienes1,donantePrueba,null,null); 
    	
    	DonacionDTO donacionPruebaDTO= new DonacionDTO(donacionPrueba.getFechaDonacion(),
    			donacionPrueba.getObservacion(),
    			biendto,
    			
    			);
    	api.registrarDonacion(donacionPruebaDTO);
    	
    	//tring codigo, LocalDate fechaDonacion, String observacion, ArrayList<BienDTO> bienes,
		//String codDonante, String codPedido, String codRetiro
   	}
 }

}
