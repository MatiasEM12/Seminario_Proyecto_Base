package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TestAcceso {

	public static void main(String[] args) {
		RolDao rolDao = new RolDAOJDBC();
		List<Rol> roles = rolDao.findAll();

		for (Rol rol : roles) {
			System.out.println(rol);
		}

		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
		try {
			Usuario usuario = new Usuario("ldifabio", "1234", "diego", "ldifabio@unrn.edu.ar", new Rol(1, ""));
			crearUsuario(usuarioDao,usuario);
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		usuarioDao.create(usuario);
		System.out.print("lista de nuevo usuario");
		List<Usuario> usuarios = usuarioDao.findAll();
			for (Usuario u: usuarios) {
			System.out.println(u.toString());
		}
			
//		System.out.println(usuarioDao.find("ldifabio"));
	}
	public static void crearUsuario(UsuarioDao usuario_A,Usuario usuario_B) {
		usuario_A.create(usuario_B);
		System.out.println("Usuario creado: " + usuario_B.getNombre());
	}
	public static void crearUsuario(RolDao rol_A,Rol rol_B) {
		rol_A.create(rol_B);
		System.out.println("Rol creado: " + rol_B.getNombre());
	}

}
