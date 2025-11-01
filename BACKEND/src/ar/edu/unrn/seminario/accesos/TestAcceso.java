package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TestAcceso {

	public static void main(String[] args) {
		RolDao rolDao = new RolDAOJDBC();
		List<Rol> roles = rolDao.findAll();
		//sfsd

		for (Rol rol : roles) {
			System.out.println(rol);
		}

		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
//		Usuario usuario = new Usuario("ldifabio", "1234", "Lucas", "ldifabio@unrn.edu.ar", new Rol(1, ""));
//		usuarioDao.create(usuario);
		
//		List<Usuario> usuarios = usuarioDao.findAll();
//			for (Usuario u: usuarios) {
//			System.out.println(u);
//		}
			
//		System.out.println(usuarioDao.find("ldifabio"));
		
	}
	public static void crearUsuario(UsuarioDao usuario_A,Usuario usuario_B) {
		usuario_A.create(usuario_B);
		System.out.println("Usuario creado: " + usuario_B.getNombre());
	}
	public static void crearUsuario(RolDao rol_A,Rol rol_B) {
		rol_A.create(rol_B);
		System.out.println("Usuario creado: " + rol_B.getNombre());
	}
}
