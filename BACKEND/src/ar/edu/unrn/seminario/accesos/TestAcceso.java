package ar.edu.unrn.seminario.accesos;

import java.util.List;


import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Ubicacion;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TestAcceso {

    public static void main(String[] args) {
    	
    	
        // --- 1) Consulta SQL para eliminar rol con codigo = 45
        System.out.println("SQL para eliminar rol con codigo 45:");
        System.out.println("DELETE FROM roles WHERE codigo = 45;");
        System.out.println();

        //  con el DAO (si RolDAOJDBC tiene remove(Integer)):
        RolDao rolDao = new RolDAOJDBC();
        try {
            System.out.println("Intentando eliminar rol 45 via DAO...");
            rolDao.remove(45); // usa la implementación de RolDAOJDBC
        } catch (Exception e) {
            System.err.println("Error eliminando rol 45: " + e.getMessage());
        }
        System.out.println();

        // --- 2) Simulación con Donantes
        DonanteDao donanteDao = new DonanteDAOJDBC();

        // 2.a) recuperar por consola los donantes actuales
        System.out.println("Donantes actuales:");
        List<Donante> antes = donanteDao.findAll();
        for (Donante d : antes) {
            System.out.println(d);
        }
        System.out.println("Total antes: " + antes.size());
        System.out.println();

        // 2.b) agregar un Donante genérico de prueba
        System.out.println("Insertando donante de prueba...");
       
        Ubicacion ubicacionPrueba = new Ubicacion("UB007","ZONA-TEST", "BarrioTest", "Calle Falsa 123");
        Donante donantePrueba = new Donante("NombreTest", "ApellidoTest", "test@x.com", ubicacionPrueba, "usertest"); 
       
     
        donanteDao.create(donantePrueba);
        System.out.println("Insertado: " + donantePrueba.getCodigo());
        System.out.println();

        // 2.c) recuperar todos los donantes y mostrarlos por consola
        System.out.println("Donantes después de insertar el de prueba:");
        List<Donante> despues = donanteDao.findAll();
        for (Donante d : despues) {
            System.out.println(d);
        }
        System.out.println("Total despues: " + despues.size());
        System.out.println();

        // 2.d) eliminar de la base de datos el donante de prueba
        System.out.println("Eliminando donante de prueba...");
        // intentamos eliminar por codigo y username (según implementacion DAO)
        try {
            donanteDao.remove(donantePrueba); // usa remove(Donante) si está implementado
            //  eliminar username o codigo:
            // donanteDao.remove(donantePrueba.getUsername());
        } catch (Exception e) {
            System.err.println("Error al eliminar donante de prueba: " + e.getMessage());
        }

        // verificar eliminación
        System.out.println("Donantes finales (verificacion):");
        List<Donante> finalList = donanteDao.findAll();
        for (Donante d : finalList) {
            System.out.println(d);
        }
        System.out.println("Total final: " + finalList.size());
    }

    // Métodos auxiliares :
    public static void crearUsuario(UsuarioDao usuario_A, Usuario usuario_B) {
        usuario_A.create(usuario_B);
    }

    public static void crearRol(RolDao rolDao, Rol rol) {
        rolDao.create(rol);
    }
}
