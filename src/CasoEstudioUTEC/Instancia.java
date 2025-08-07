package CasoEstudioUTEC;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Instancia {
    private String id;
    private String titulo;
    private String tipo;
    private LocalDateTime fechaHora;
    private String descripcion;
    private Estudiante estudiante;
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    private boolean estadoActivo = true;


    public Instancia(String id,String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.estudiante = estudiante;
        this.funcionarios = funcionarios;
        LocalDateTime fechaHoy = LocalDateTime.now();
        generarNotificacion(id, estudiante, tipo, descripcion, fechaHoy);
        for (Funcionario funcionario : funcionarios) {
            generarNotificacion(id, funcionario, tipo, descripcion, fechaHoy);
            /*
            Este id no puede ser el mismo para ambos objetos, esto es un simple ejemplo forzado para que el código no presente errores,
            ya que el constructor de Notificación pide un parámetro "id", le pasamos el mismo de Instancia pero sabemos que no pueden repetirse.
            Es la manera que encontramos de representar este proceso donde se genera una notificación automáticamente para cada Usuario participante de
            dicha Instancia.
             */
        }
    }


    public String getId() {
        return this.id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDateTime getFechaHora() {
        return this.fechaHora;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void generarNotificacion(String id, Usuario destinatario, String asunto, String mensaje, LocalDateTime fechaEnvio) {
        Notificacion notificacion = new Notificacion(id, destinatario, asunto, mensaje, fechaEnvio);
        destinatario.addNotificacion(notificacion);
    }

    @Override
    public String toString() {
        return "Instancia{" + "id='" + id + '\'' + ", tipo='" + tipo + '\'' + ", fechaHora=" + fechaHora + ", descripcion='" + descripcion + '\'' + ", funcionarios=" + funcionarios + '}';
    }
}

