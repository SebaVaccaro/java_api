import java.util.Scanner;

public class MenuInicial {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("=== MENÚ PRINCIPAL DE FACADES ===");
            System.out.println("1. ArchivoAdjunto");
            System.out.println("2. Carrera");
            System.out.println("3. Ciudad");
            System.out.println("4. Direccion");
            System.out.println("5. Estudiante");
            System.out.println("6. Funcionario");
            System.out.println("7. Grupo");
            System.out.println("8. Incidencia");
            System.out.println("9. InformeFinal");
            System.out.println("10. InstanciaComun");
            System.out.println("11. Instancia");
            System.out.println("12. ITR");
            System.out.println("13. Notificacion");
            System.out.println("14. Observacion");
            System.out.println("15. PartInstancia");
            System.out.println("16. PartSeguimiento");
            System.out.println("17. Pertenece");
            System.out.println("18. Recibe");
            System.out.println("19. Rol");
            System.out.println("20. Seguimiento");
            System.out.println("21. TeleITR");
            System.out.println("22. TeleUsuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione un facade: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (opcion) {
                case 0: System.out.println("Saliendo..."); break;
                case 1: System.out.println("Has seleccionado ArchivoAdjunto"); break;
                case 2: System.out.println("Has seleccionado Carrera"); break;
                case 3: System.out.println("Has seleccionado Ciudad"); break;
                case 4: System.out.println("Has seleccionado Direccion"); break;
                case 5: System.out.println("Has seleccionado Estudiante"); break;
                case 6: System.out.println("Has seleccionado Funcionario"); break;
                case 7: System.out.println("Has seleccionado Grupo"); break;
                case 8: System.out.println("Has seleccionado Incidencia"); break;
                case 9: System.out.println("Has seleccionado InformeFinal"); break;
                case 10: System.out.println("Has seleccionado InstanciaComun"); break;
                case 11: System.out.println("Has seleccionado Instancia"); break;
                case 12: System.out.println("Has seleccionado ITR"); break;
                case 13: System.out.println("Has seleccionado Notificacion"); break;
                case 14: System.out.println("Has seleccionado Observacion"); break;
                case 15: System.out.println("Has seleccionado PartInstancia"); break;
                case 16: System.out.println("Has seleccionado PartSeguimiento"); break;
                case 17: System.out.println("Has seleccionado Pertenece"); break;
                case 18: System.out.println("Has seleccionado Recibe"); break;
                case 19: System.out.println("Has seleccionado Rol"); break;
                case 20: System.out.println("Has seleccionado Seguimiento"); break;
                case 21: System.out.println("Has seleccionado TeleITR"); break;
                case 22: System.out.println("Has seleccionado TeleUsuario"); break;
                default: System.out.println("Opción inválida."); break;
            }

            System.out.println();
        }

        scanner.close();
    }
}
