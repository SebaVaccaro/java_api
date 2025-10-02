package main.java.modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class InstanciaComun extends Instancia {

    private List<Funcionario> funcionarios = new ArrayList<>();

    public InstanciaComun(String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios) {
        super(titulo, tipo, fechaHora, descripcion, estudiante);
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void generarNotificacion(String id, Usuario destinatario, String asunto, String mensaje, LocalDateTime fechaEnvio) {
        Notificacion notificacion = new Notificacion(id, destinatario, asunto, mensaje, fechaEnvio);
        destinatario.addNotificacion(notificacion);
    }


}
