package ar.edu.unrn.seminario.accesos;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class TestFinal {
	
	
	
	
	
	public static void main(String[] args) {
		try {
			IApi api = new PersistenceApi();
			
			RolDao rolDAO = new RolDAOJDBC();
			UsuarioDAOJDBC usuarioDAO = new UsuarioDAOJDBC();
			CoordenadaDAOJDBC coordenadaDAO= new CoordenadaDAOJDBC();
			UbicacionDAOJDBC ubicacionDAO= new UbicacionDAOJDBC();
			BeneficiarioDAOJDBC beneficiarioDAO= new BeneficiarioDAOJDBC();
			DonanteDAOJDBC donanteDAO=new DonanteDAOJDBC();
			VoluntarioDAOJDBC voluntarioDAO= new VoluntarioDAOJDBC();
			//roles
			//crea los roles base
			Rol rol1 = new Rol(1,"Admin", true);
			rolDAO.create(rol1);
			
			Rol rol2 = new Rol(2, "Voluntario", true);
			rolDAO.create(rol2);
			
			Rol rol3 = new Rol(3, "Donante", true);
			rolDAO.create(rol3);
			
			Rol rol4 = new Rol(4, "Beneficiario", true);
			rolDAO.create(rol4);
			
			//usuarios
			
			Usuario.setContadorUsuario(usuarioDAO.obtenerCantidadUsuarios());
			
			// se puede cambiar en el costructor en ves de pasarle el tipo de rol que lo busque por codigo, ej: Rol rolx = rolDAO.find(1);
			// o directamente en el costructor ("perry_AD","87654321","Jeff","perry_AD12@mail.com",rolDAO.find(1),true,null);
			
			Usuario usuario_1 = new Usuario("perry_AD", "87654321", "Jeff", "perry_AD12@mail.com", rol1, true, null);//admin
			usuarioDAO.create(usuario_1);
			
			Usuario usuario_2 = new Usuario("pedro_Vol", "12345678", "Pedro.P", "pedro_Vol12@mail.com", rol2, true, null);//voluntario
			usuarioDAO.create(usuario_2);
			Usuario usuario_3 = new Usuario("ian_Don", "12121212", "Ian.H", "ian_Don12@mail.com", rol3, true, null);//donante
			usuarioDAO.create(usuario_3);
			
			Usuario usuario_4 = new Usuario("matias_Ben", "00010001", "Matias.M", "matias_Ben12@mail.com", rol4, true, null);//beneficiario
			usuarioDAO.create(usuario_4);
			
			//coordenada
			Coordenada.setContadorCoordenada(coordenadaDAO.obtenerCantidadCoordenadas());
			
			//ubicacion
			Ubicacion.setContadorUbicacion(ubicacionDAO.obtenerCantidadUbicaciones());
			
			//Beneficiario
			Beneficiario.setContadorDonante(beneficiarioDAO.obtenerCantidadBeneficiarios());
			
			//Donante
			Donante.setContadorDonante(donanteDAO.obtenerCantidadDonantes());
			
			//Voluntario
			Voluntario.setContadorVoluntario(voluntarioDAO.obtenerCantidadVoluntarios());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
