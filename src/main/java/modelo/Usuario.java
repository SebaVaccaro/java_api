package main.java.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase abstracta Usuario.
 * Nunca se instancia directamente; siempre es Estudiante o Funcionario.
 */
public abstract class Usuario {

    private int id;
    private String ci;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String correo;
    private List<String> telefonos = new ArrayList<>();
    private List<Direccion> direcciones = new ArrayList<>();
    private LinkedList<Notificacion> listaNotificaciones = new LinkedList<>();

    public Usuario(int id, String ci, String username, String password,String nombre, String apellido, String correo,String telefono, Direccion direccion) {
        this.id = id;
        this.ci = ci;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        if(telefono!=null){
            telefonos.add(telefono);
        }
        if(direccion!=null){
            direcciones.add(direccion);
        }
    }

    // Getters / setters principales
    public void setId(int id) { this.id = id; }


    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void addTelefono(String telefono) { telefonos.add(telefono); }
    public List<Direccion> getDirecciones() { return direcciones; }
    public void addDireccion(Direccion direccion) { direcciones.add(direccion); }
    public void addNotificacion(Notificacion n) { listaNotificaciones.add(n); }
    public LinkedList<Notificacion> getListaNotificaciones() { return listaNotificaciones; }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', ci='" + ci + "', username='" + username + "', nombre='" + nombre + "', apellido='" + apellido + "', correo='" + correo + "', telefonos=" + telefonos + "}";
    }
}
