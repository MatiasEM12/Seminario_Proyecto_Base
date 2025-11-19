package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.DonanteDTO;
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
	DonanteDao donanteDao=new DonanteDAOJDBC();
		
	try {
		IApi api = new PersistenceApi();
		
		RolDao rolDao = new RolDAOJDBC();
		List<Rol> roles = rolDao.findAll();

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

			try {
			    // 1) Coordenada y Ubicacion de prueba
			    Coordenada coordenada = new Coordenada(3.454, 5.234, "C00004");
			    Ubicacion ubicacion = new Ubicacion("Zona_Prueba", "Barrio_Prueba", "234_dir_prueba", coordenada);

			    // Registrar ubicación (internamente insertará coordenada/ubicación o las reutilizará)
			    api.registrarUbicacion(ubicacion);

			    // 2) Usuario para el donante de prueba
			    api.registrarUsuario(
			            "user_prueba",
			            "1223",
			            "prueba@gmail.com",
			            "Nombre_Prueba",
			            3,       // código de rol DONANTE
			            true
			    );

			    // 3) Crear Donante con contador actualizado
			    Donante.setContadorDonante(donanteDao.obtenerCantidadUsuarios());
			    Donante donantePrueba = new Donante(
			            "Nombre_Prueba",
			            "Apellido_Prueba",
			            LocalDate.of(2000, 2, 20),
			            "11111222",
			            "prueba@gmail.com",
			            ubicacion,
			            "user_prueba"
			    );

			    api.registrarDonante(donantePrueba);
			    System.out.println("Donante de prueba registrado con codigo = " + donantePrueba.getCodigo());

			    // 4) Bienes de prueba (entidades)
			    ArrayList<Bien> bienes1 = new ArrayList<>();
			    bienes1.add(new Bien(
			            null,
			            "Alimento",
			            0.200,
			            "Manteca",
			            "Manteca sin sal",
			            1,
			            LocalDate.of(2026, 1, 17),
			            0.0,
			            null
			    ));
			    bienes1.add(new Bien(
			            null,
			            "Ropa",
			            0.2,
			            "Camisa",
			            "Camisa de ToyStory 23",
			            2,
			            null,
			            5.0,
			            "algodon"
			    ));

			    // 5) Convertir Bien -> BienDTO usando la API
			    ArrayList<BienDTO> bienesDTO = new ArrayList<>();
			    try {
			        bienesDTO = bienes1.stream()
			                .map(b -> api.toBienDTO(b))
			                .collect(Collectors.toCollection(ArrayList::new));
			        System.out.println("Bienes convertidos a DTO: cantidad=" + bienesDTO.size());
			    } catch (Exception e) {
			        System.out.println("ERROR al convertir bienes a DTO: " + e.getMessage());
			        e.printStackTrace();
			    }

			    // 6) Armar DonacionDTO usando directamente el código del Donante recién creado
			    DonacionDTO donacionPruebaDTO = new DonacionDTO(
			            null,                           // codigo de donación (lo asigna la API)
			            LocalDate.now(),
			            "Un alimento y Una camisa para donar",
			            bienesDTO,
			            donantePrueba.getCodigo(),      // codDonante
			            null                            // codPedido = null en la simulación
			    );

			    // 7) Registrar la Donación
			    try {
			        api.registrarDonacion(donacionPruebaDTO);
			        System.out.println("Donación registrada OK para donanteCodigo=" + donantePrueba.getCodigo());
			    } catch (Exception e) {
			        System.out.println("ERROR al registrar donacion: " + e.getMessage());
			        e.printStackTrace();
			    }

			} catch (Exception e) {
			    e.printStackTrace();
			}



   	}catch(Exception e) {
   		e.printStackTrace();
   	
	
   	}
}
}
