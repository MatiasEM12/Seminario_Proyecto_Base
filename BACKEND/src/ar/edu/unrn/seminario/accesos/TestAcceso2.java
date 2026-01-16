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
			 
		}catch(Exception e) {
	   		e.printStackTrace();
	   	   	
	   		
	   	}

	}

}
