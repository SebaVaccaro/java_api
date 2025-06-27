package CasoEstudioUTEC;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Usuario {

    private String id;
    private String username;
    private String passwordHash;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private Rol rol;
    private boolean estadoActivo = true;
    private LinkedList<Notificacion> listaNotificaciones;

    public enum Rol {
        ADMINISTRADOR,
        ESTUDIANTE,
        PSICOPEDAGOGO,
        ANALISTA_EDUCATIVO,
        RESPONSABLE_EDUCATIVO,
        AREA_LEGAL,
        FUNCIONARIO,
        TUTOR
    }

    public Usuario(String id,
                   String username,
                   String passwordHash,
                   String nombre,
                   String apellido,
                   String correo,
                   String telefono,
                   String direccion,
                   Rol rol) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

    //Constructor para llamarlo en la clase Notificacion
    public Usuario(String id,
                   String nombre,
                   String apellido,
                   String correo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public String getId() {
        return id;
    } // no se reaiza setter porque el id no puede cambiarse una vez creado

    public String getUsername() {
        return username;
    } // no se reaiza setter porque el username no puede cambiarse una vez creado

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    // metodo toString del segundo constructor
    @Override
    public String toString() {
        return "Usuario: " +
                "id= '" + id + '\'' +
                ", nombres= '" + nombre + '\'' +
                ", apellidos= '" + apellido + '\'' +
                ", correo= '" + correo + '\'';
    }


}