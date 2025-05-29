package CasoEstudioUTEC;

class Rol{
    private String id;
    private String nombre;
    private String descripcion;

    public Rol(String id,
               String nombre,
               String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Rol: " +
                "id= '" + id + '\'' +
                ", nombre= '" + nombre + '\'' +
                ", descripcion= '" + descripcion + '\'';
    }
}
