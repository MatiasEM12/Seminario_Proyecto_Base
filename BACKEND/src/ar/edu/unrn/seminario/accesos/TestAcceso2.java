package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.accesos.*;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class TestAcceso2 {

	public static void main(String[] args) {
		try {
			IApi api = new PersistenceApi();
			
			RolDao rolDAO = new RolDAOJDBC();
			UsuarioDAOJDBC usuarioDAO = new UsuarioDAOJDBC();
			CoordenadaDAOJDBC coordDAO = new CoordenadaDAOJDBC();
			UbicacionDAOJDBC ubiDAO = new UbicacionDAOJDBC();
			
			//TEST ROL
			/*
			System.out.println("=== CREAR ROL ===");
            Rol rol = new Rol(10, "ADMIN", true);
            rolDAO.create(rol);

            Rol rol2 = new Rol(20, "VOLUNTAIO", true);
            rolDAO.create(rol2);
            
            Rol rol3 = new Rol(30, "DONANTE", true);
            rolDAO.create(rol3);
            
          
            System.out.println("=== BUSCAR ROL ===");
            Rol buscado = rolDAO.find(10);
            System.out.println(buscado);

            System.out.println("=== ACTUALIZAR ROL ===");
            buscado.setNombre("ADMINISTRADOR");
            buscado.desactivar();
            rolDAO.update(buscado);

            System.out.println("=== LISTAR ROLES ===");
            List<Rol> roles = rolDAO.findAll();
            for (Rol r : roles) {
                System.out.println(r);
            }

            System.out.println("=== ELIMINAR ROL ===");
            rolDAO.remove(10);
            
            System.out.println("=== recuperar ROL ===");
			 Rol r = rolDAO.find(20);
			 System.out.println(r);
*/
			//TEST USER
			/*
			 System.out.println("===== TEST ROL =====");

	            Rol rol = new Rol(40, "OPERADOR", true);
	            rolDAO.create(rol);

	            Rol rolBD = rolDAO.find(20);
	            System.out.println("Rol encontrado: " + rolBD);

	            System.out.println("\n===== TEST USUARIO =====");

	            // sincronizar contador (opcional pero recomendado)
	            Usuario.setContadorUsuario(usuarioDAO.obtenerCantidadUsuarios());

	            Usuario usuario = new Usuario(
	                    "juan123",
	                    "1234",
	                    "Juan Perez",
	                    "juan@mail.com",
	                    rolBD,
	                    true,
	                    null   // genera código automático
	            );

	            usuarioDAO.create(usuario);

	            System.out.println("\n--- Buscar usuario ---");
	            Usuario buscado = usuarioDAO.find("juan123");
	            System.out.println(buscado);

	            System.out.println("\n--- Actualizar usuario ---");
	            buscado.setNombre("Juan Carlos Perez");
	            buscado.setContacto("juan.c@mail.com");
	            buscado.desactivar();
	            usuarioDAO.update(buscado);

	            System.out.println("\n--- Listar usuarios ---");
	            List<Usuario> usuarios = usuarioDAO.findAll();
	            for (Usuario u : usuarios) {
	                System.out.println(u);
	            }

	            System.out.println("\n--- Eliminar usuario ---");
	            usuarioDAO.remove(buscado);

	            System.out.println("\n--- Eliminar rol ---");
	            rolDAO.remove(rolBD); */
			
			/*
			System.out.println("\n===== TEST COORDENADA + UBICACION =====");
			 // 1️⃣ Crear coordenada
		    Coordenada coord = new Coordenada(-40.8135, -62.9967);
		    coordDAO.create(coord);

		    // 2️⃣ Crear ubicación
		    Ubicacion ubi = new Ubicacion(
		            "Centro",
		            "Barrio Norte",
		            "Av. Roca 123",
		            coord
		    );
		    ubiDAO.create(ubi);

		    // 3️⃣ Buscar ubicación
		    System.out.println("\n--- Buscar Ubicación ---");
		    Ubicacion buscada = ubiDAO.find(ubi.getCodigo());
		    System.out.println("Ubicacion encontrada: " + buscada.getCodigo());

		    // 4️⃣ Listar ubicaciones
		    System.out.println("\n--- Listar Ubicaciones ---");
		    List<Ubicacion> ubicaciones = ubiDAO.findAll();
		    for (Ubicacion u : ubicaciones) {
		        System.out.println(u.getCodigo() + " - " + u.getDireccion());
		    }

		    // 5️⃣ Eliminar ubicación (y coordenada si no se usa)
		    System.out.println("\n--- Eliminar Ubicación ---");
		    ubiDAO.remove(ubi);

			 */
			
			 DonanteDAOJDBC dao = new DonanteDAOJDBC();

	            // 1️⃣ Crear Ubicacion y Coordenada
	            Coordenada c = new Coordenada(-40.813, -62.996, "C00001");

	            Ubicacion u = new Ubicacion(
	                    "Zona Centro",
	                    "Barrio Norte",
	                    "Av. Roca 123",
	                    c
	            );

	            // 2️⃣ Crear Donante
	            Donante d = new Donante(
	                    "Juan",
	                    "Pérez",
	                    LocalDate.of(1990, 5, 20),
	                    "30123456",
	                    "2994123456",
	                    u,
	                    "juanperez"
	            );

	            // 3️⃣ INSERT
	            System.out.println("=== INSERT ===");
	            dao.create(d);

	            // 4️⃣ FIND
	            System.out.println("=== FIND ===");
	            Donante encontrado = dao.find(d.getCodigo());   //171
	            System.out.println(encontrado);

	            // 5️⃣ UPDATE
	            System.out.println("=== UPDATE ===");
	            encontrado.setUsername("juan_actualizado");
	            dao.update(encontrado);

	            Donante actualizado = dao.find(encontrado.getCodigo());
	            System.out.println(actualizado);

	            // 6️⃣ FIND ALL
	            System.out.println("=== FIND ALL ===");
	            List<Donante> lista = dao.findAll();
	            lista.forEach(System.out::println);

	            // 7️⃣ REMOVE
	            System.out.println("=== REMOVE ===");
	            dao.remove(actualizado.getCodigo());

	            Donante borrado = dao.find(actualizado.getCodigo());
	            System.out.println("Debe ser null -> " + borrado);
		}catch(Exception e) {
	   		e.printStackTrace();
	   	   	
	   		
	   	}

	}

}
