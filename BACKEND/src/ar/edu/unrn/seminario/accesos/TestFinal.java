package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.util.ArrayList;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
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
			BienDAOJDBC bienDAO = new BienDAOJDBC();
			DonacionDAOJDBC donacionDAO= new DonacionDAOJDBC();
			Bien_DonacionJDBC bienDonacionDAO = new Bien_DonacionJDBC();
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
			
			Usuario usuario_2 = new Usuario("pedro_Vol", "12345678", "Pedro", "pedro_Vol12@mail.com", rol2, true, null);//voluntario
			usuarioDAO.create(usuario_2);
			Usuario usuario_3 = new Usuario("ian_Don", "12121212", "Ian", "ian_Don12@mail.com", rol3, true, null);//donante
			usuarioDAO.create(usuario_3);
			
			Usuario usuario_4 = new Usuario("matias_Ben", "00010001", "Matias", "matias_Ben12@mail.com", rol4, true, null);//beneficiario
			usuarioDAO.create(usuario_4);
			
			//coordenada
			Coordenada.setContadorCoordenada(coordenadaDAO.obtenerCantidadCoordenadas());
			
			Coordenada coordenadaBeneficiario = new Coordenada(11.233,12.333);
			Coordenada coordenadaDonante=new Coordenada(23.333,12.3333);
			
			coordenadaDAO.create(coordenadaBeneficiario);
			coordenadaDAO.create(coordenadaDonante);
			
			//ubicacion
			Ubicacion.setContadorUbicacion(ubicacionDAO.obtenerCantidadUbicaciones());
			Ubicacion ubicacionBeneficiario = new Ubicacion("Este","San Juan","123",coordenadaBeneficiario);
			Ubicacion ubicacionDonante=new Ubicacion("Norte","Flores","456",coordenadaDonante);
			
			ubicacionDAO.create(ubicacionBeneficiario);
			ubicacionDAO.create(ubicacionDonante);
			
			//Beneficiario
			Beneficiario.setContadorDonante(beneficiarioDAO.obtenerCantidadBeneficiarios());
			Beneficiario beneficiarioTest = new Beneficiario( "Matias","Ben",LocalDate.of(2000, 1, 10),"11111111","matias_Ben12@mail.com",ubicacionBeneficiario,"matias_Ben");
			
			beneficiarioDAO.create(beneficiarioTest);
			
			
			//Donante
			Donante.setContadorDonante(donanteDAO.obtenerCantidadDonantes());
			
			Donante donanteTest = new Donante("Ian","Don",LocalDate.of(1999,3, 20),"22222222","ian_Don12@mail.com",ubicacionDonante,"ian_Don");
			
			donanteDAO.create(donanteTest);
			
			//Voluntario
			Voluntario.setContadorVoluntario(voluntarioDAO.obtenerCantidadVoluntarios());
			Voluntario voluntatioTest = new Voluntario("Pedro","Contrera",LocalDate.of(2003, 10, 21),"pedro_Vol12@mail.com","33333333","pedro_Vol");
			
			voluntarioDAO.create(voluntatioTest);
			
			
			//Bien
			Bien.setContadorBien(bienDAO.obtenerCantidadBienes());
			Bien alimentoTest = Bien(null,"Alimento",0.0,"Manteca","Manteca marca 'YYYY'",1,LocalDate.of(2026, 4, 11),0.0,null);
			Bien ropaTest = Bien(null,"Ropa",0.0,"Camisa","Camisa usada, con botones cambiados",2,null,0.0,"algodon");
			
			bienDAO.create(alimentoTest);
			bienDAO.create(ropaTest);
			
			ArrayList <Bien> bienesTest= new ArrayList();
			bienesTest.add(ropaTest);
			bienesTest.add(alimentoTest);
			//Donacion
			Donacion.setContadorDonacion(donacionDAO.obtenerCantidadDonaciones());
			
			Donacion donacionTest = new Donacion(LocalDate.now(),"Donacion de una camisa y una manteca",bienesTest,donanteTest,null);
			
			bienDonacionDAO.create(ropaTest.getCodigo(), donacionTest.getCodigo());
			bienDonacionDAO.create(alimentoTest.getCodigo(), donacionTest.getCodigo());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Bien Bien(Object object, String string, double d, String string2, String string3, int i,
			LocalDate of, double e, Object object2) {
		// TODO Auto-generated method stub
		return null;
	}
}
