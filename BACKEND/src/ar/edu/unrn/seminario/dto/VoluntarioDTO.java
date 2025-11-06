package ar.edu.unrn.seminario.dto;

public class VoluntarioDTO {

    private final String codigo;
    private final String nombre;
    private final String apellido;
    private final String preferenciaContacto;
    private final String tarea;
    private final boolean disponible;
    private final String username;

    public VoluntarioDTO(String codigo, String nombre, String apellido, String preferenciaContacto,
                         String tarea, boolean disponible, String username) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.preferenciaContacto = preferenciaContacto;
        this.tarea = tarea;
        this.disponible = disponible;
        this.username = username;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPreferenciaContacto() {
        return preferenciaContacto;
    }

    public String getTarea() {
        return tarea;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        // Esto permite que el comboBox muestre el nombre y apellido del voluntario
        return nombre + " " + apellido;
    }
}