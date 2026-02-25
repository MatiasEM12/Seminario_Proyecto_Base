package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.util.ArrayList;


import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.SolicitudBien;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class TestFinal {
	
	
	public static void main(String[] args) {
		try {
			
			
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
			InventarioDAO inventarioDAO= new InventarioDAOJDBC();
			SolicitudBienesDAO solicitudBienDAO = new SolicitudBienesJDBC();
			
		
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
			
			Usuario.setContadorUsuario(usuarioDAO.obtenerMaximoUsuarios());
			
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
			Coordenada.setContadorCoordenada(coordenadaDAO.obtenerMaximoCoordenadas());
			
			Coordenada coordenadaBeneficiario = new Coordenada(11.233,12.333);
			Coordenada coordenadaDonante=new Coordenada(23.333,12.3333);
			
			coordenadaDAO.create(coordenadaBeneficiario);
			coordenadaDAO.create(coordenadaDonante);
			
			//ubicacion
			Ubicacion.setContadorUbicacion(ubicacionDAO.obtenerMaximoUbicaciones());
			Ubicacion ubicacionBeneficiario = new Ubicacion("Este","SanJuan","123",coordenadaBeneficiario);
			Ubicacion ubicacionDonante=new Ubicacion("Norte","Flores","456",coordenadaDonante);
			
			ubicacionDAO.create(ubicacionBeneficiario);
			ubicacionDAO.create(ubicacionDonante);
			
			//Beneficiario
			Beneficiario.setContadorDonante(beneficiarioDAO.obtenerMaximoBeneficiarios());
			Beneficiario beneficiarioTest = new Beneficiario( "Matias","Ben",LocalDate.of(2000, 1, 10),"11111111","matias_Ben12@mail.com",ubicacionBeneficiario,"matias_Ben",1,0);
			
			beneficiarioDAO.create(beneficiarioTest);
			
			
			
			//Donante
			Donante.setContadorDonante(donanteDAO.obtenerMaximoDonantes());
			
			Donante donanteTest = new Donante("Ian","Don",LocalDate.of(1999,3, 20),"22222222","ian_Don12@mail.com",ubicacionDonante,"ian_Don");
			
			donanteDAO.create(donanteTest);
			
			//Voluntario
			Voluntario.setContadorVoluntario(voluntarioDAO.obtenerMaximoVoluntarios());
			Voluntario voluntatioTest = new Voluntario("Pedro","Contrera",LocalDate.of(2003, 10, 21),"pedro_Vol12@mail.com","33333333","pedro_Vol");
			
			voluntarioDAO.create(voluntatioTest);
			
			
			//Bien
			Bien.setContadorBien(bienDAO.obtenerMaximoBienes());
			Bien alimentoTest = new Bien(null,"Alimento",null,"Manteca","Manteca marca 'YYYY'",LocalDate.of(2027, 1, 1),null,null);
			Bien ropaTest = new Bien(null,"Ropa",null,"Camisa","Camisa usada, con botones cambiados",null,4.0,"algodon");
			
			bienDAO.create(alimentoTest);
			bienDAO.create(ropaTest);
			
			ArrayList <Bien> bienesTest= new ArrayList<>();
			bienesTest.add(ropaTest);
			bienesTest.add(alimentoTest);
			
			Donacion.setContadorDonacion(donacionDAO.obtenerMaximoDonaciones());
			
			Donacion donacionTest = new Donacion(LocalDate.now(),"Donacion de una camisa y una manteca",bienesTest,donanteTest,null);
			
			bienDonacionDAO.create(ropaTest.getCodigo(), donacionTest.getCodigo());
			bienDonacionDAO.create(alimentoTest.getCodigo(), donacionTest.getCodigo());
			
			donacionDAO.create(donacionTest);
			
			
			/*Como hicimos el sistema pensando en la perspectiva del ADM, representamos de la siguiente forma la creacion de la solicitud 
			 * de Bienes por parte del beneficiario.
			 * 		Decidimos pensar que desde la perspectiva del Benficiariom él podra elegir los bienes que se encuentran disponibles 
			 * en el inventario, tal como si fuese un "carro de compras" que seleccionara los bienes que se encuentran disponibles en la
			 * Organizacion. 
			 * */
			Bien alimentoTestSolicitud = new Bien(null,"Alimento",null,"Fideos","Fideos marca 'YYYY'",LocalDate.of(2026, 7, 12),null,null);
			Bien ropaTestSolicitud = new Bien(null,"Ropa",null,"Pantalon","Pantalon nuevo",null,1.0,"algodon");
			
			bienDAO.create(alimentoTestSolicitud);
			bienDAO.create(ropaTestSolicitud);
			inventarioDAO.create(alimentoTestSolicitud.getCodigo(), alimentoTestSolicitud.getTipo(), true);
			inventarioDAO.create(ropaTestSolicitud.getCodigo(), ropaTestSolicitud.getTipo(), true);
			
			ArrayList <Bien> bienesSeleccionados= new ArrayList<>();
			bienesSeleccionados.add(ropaTestSolicitud);
			bienesSeleccionados.add(alimentoTestSolicitud);
			
			SolicitudBien solicitud= new SolicitudBien(beneficiarioTest,bienesSeleccionados,Orden.EstadoOrden.PENDIENTE.toString());
			solicitudBienDAO.create(solicitud);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
