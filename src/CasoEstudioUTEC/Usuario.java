package CasoEstudioUTEC;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Usuario {

    private String id;
    private String ci;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String correo;
    private List<String> telefonos = new ArrayList<String>();
    private boolean estadoActivo = true;
    private LinkedList<Notificacion> listaNotificaciones = new LinkedList<Notificacion>();
    private List<Direccion> direcciones = new ArrayList<Direccion>();

    public Usuario(String id, String ci, String username, String password, String nombre, String apellido, String correo, String telefono, Direccion direccion) {
        this.id = id;
        this.ci = ci;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        telefonos.add(telefono);
        direcciones.add(direccion);
    }

    //Constructor para llamarlo en la clase Notificacion
    //Se va a usar para el constructor de Notificaciones, super()
    //Para usarlo en el futuro
//    public Usuario(String id,
//                   String nombre,
//                   String apellido,
//                   String correo) {
//        this.id = id;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.correo = correo;
//    }

    public String getId() {
        return id;
    } // no se realiza setter porque el id no puede cambiarse una vez creado

    public String getUsername() {
        return username;
    } // no se realiza setter porque el username no puede cambiarse una vez creado

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password= password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefonos() {
        return telefonos.toString();
    }

    public void addTelefono(String telefono) {
        telefonos.add(telefono);
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void addNotificacion(Notificacion notificacion) {
        listaNotificaciones.add(notificacion);
    }

    public LinkedList<Notificacion> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public String getCi() {
        return ci;
    }

    public void addDireccion(Direccion direccion) {
        direcciones.add(direccion);
    }

    // metodo toString del segundo constructor
    @Override
    public String toString() {
        return "Usuario: " + "id= '" + id + '\'' + "CI= '"+ci+"' , nombre= '" + nombre + '\'' + ", apellidos= '" + apellido + '\'' + ", correo= '" + correo + '\''+", Direcciones: '" + direcciones + '\'';
    }


}