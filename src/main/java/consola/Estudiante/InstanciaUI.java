package consola.Estudiante;

import facade.InstanciaFacade;
import modelo.Instancia;

import java.util.List;
import java.util.Scanner;

public class InstanciaUI {

    private final Scanner scanner = new Scanner(System.in);
    private final InstanciaFacade instanciaFacade = new InstanciaFacade();
    private final int idEstudiante; // Ahora tenemos un id asociado al estudiante

    public InstanciaUI(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void menuInstancias() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ INSTANCIAS ---");
            System.out.println("1. Listar mis instancias");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> listarMisInstancias();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarMisInstancias() {
        List<Instancia> instancias = instanciaFacade.obtenerPorEstudiante(idEstudiante);
        if (instancias.isEmpty()) {
            System.out.println("No tienes instancias registradas.");
        } else {
            System.out.println("\n--- Mis Instancias ---");
            instancias.forEach(i -> System.out.println(
                    "ID: " + i.getIdInstancia() +
                            " | Título: " + i.getTitulo() +
                            " | Tipo: " + i.getTipo() +
                            " | Fecha reunión: " + i.getFechaReunion() +
                            " | Funcionarios: " + i.getIdFuncionarios() +
                            " | Activa: " + i.isEstActivo()
            ));
        }
    }

    // --- Métodos auxiliares ---
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
