package main;


import modelo.Estudiante;
import modelo.Usuario;
import service.UsuarioService;
import service.EstudianteServicio;
import modelo.Funcionario;
import service.FuncionarioServicio;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioService usuarioService;
        Usuario usuarioActual = null;

        try {
            usuarioService = new UsuarioService();
        } catch (Exception e) {
            System.out.println("❌ Error al iniciar servicios: " + e.getMessage());
            return;
        }

        System.out.println("=================================");
        System.out.println("   Bienvenido a SIENEP (UTEC)   ");
        System.out.println("=================================\n");

        while (true) {
            // -------- LOGIN --------
            while (true) {
                System.out.print("Username: ");
                String username = sc.nextLine().trim();
                System.out.print("Contraseña: ");
                String password = sc.nextLine().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    System.out.println("❌ Debe ingresar username y contraseña.\n");
                    continue;
                }

                try {
                    usuarioActual = usuarioService.login(username, password);
                    if (usuarioActual != null) break;
                    System.out.println("Usuario no encontrado o contraseña incorrecta.\n");
                } catch (Exception e) {
                    System.out.println("Error login: " + e.getMessage());
                }
            }

            System.out.println("\n✅ Bienvenido, " + usuarioActual.getTipo() + ": " + usuarioActual.getCorreo());

            // -------- MENÚ SEGÚN USUARIO --------
            boolean cerrarSesion;
            if (usuarioActual instanceof Estudiante) {
                cerrarSesion = mostrarMenuEstudiante(sc, (Estudiante) usuarioActual);
            } else {
                cerrarSesion = mostrarMenuFuncionario(sc, (Funcionario) usuarioActual);
            }

            if (cerrarSesion) {
                System.out.println("🔒 Sesión cerrada. Volviendo al login...\n");
                usuarioActual = null;
                continue;
            } else {
                System.out.println("👋 Saliendo del sistema SIENEP...");
                break;
            }
        }
        sc.close();
    }

    private static boolean mostrarMenuEstudiante(Scanner sc, Estudiante est) {
        EstudianteServicio estService;
        try { estService = new EstudianteServicio(); }
        catch (Exception e) { throw new RuntimeException(e); }

        while (true) {
            System.out.println("\n---- Menú Estudiante ----");
            System.out.println("1. Consultar datos personales");
            System.out.println("2. Consultar estado del estudiante");
            System.out.println("3. Cerrar sesión");
            System.out.println("4. Salir del sistema");

            String opcion = sc.nextLine();
            switch (opcion) {
                case "1" -> {
                    try {
                        Estudiante e = estService.obtenerPorId(est.getId());
                        System.out.println("📄 Datos personales: " + e.getNombre() + " " + e.getApellido() +
                                ", Username: " + e.getUsername() + ", Correo: " + e.getCorreo());
                    } catch (Exception ex) {
                        System.out.println("Error al consultar: " + ex.getMessage());
                    }
                }
                case "2" -> {
                    try {
                        String estado = estService.calcularEstadoEstudiante(est.getId());
                        System.out.println("📊 Estado del estudiante: " + estado);
                    } catch (Exception ex) {
                        System.out.println("Error al consultar seguimientos: " + ex.getMessage());
                    }
                }
                case "3" -> { return true; }
                case "4" -> { return false; }
                default -> System.out.println("❌ Opción inválida.");
            }
        }
    }

    private static boolean mostrarMenuFuncionario(Scanner sc, Funcionario fun) {
        FuncionarioServicio funService;
        try { funService = new FuncionarioServicio(); }
        catch (Exception e) { throw new RuntimeException(e); }

        while (true) {
            System.out.println("\n---- Menú Funcionario (" + fun.getRol() + ") ----");
            int num = 1;
            Map<Integer, String> opciones = new HashMap<>();

            // Opciones según permisos
            if (funService.puedeCrearUsuario(fun)) {
                System.out.println(num + ". Crear estudiante");
                opciones.put(num++, "CREAR_ESTUDIANTE");

                System.out.println(num + ". Crear funcionario");
                opciones.put(num++, "CREAR_FUNCIONARIO");
            }
            if (funService.puedeModificar(fun)) {
                System.out.println(num + ". Modificar usuario");
                opciones.put(num++, "MODIFICAR");
            }
            if (funService.puedeConsultarConfidencial(fun)) {
                System.out.println(num + ". Consultar datos confidenciales");
                opciones.put(num++, "CONFIDENCIAL");
            }

            System.out.println(num + ". Cerrar sesión");
            int cerrarSesionOption = num;
            System.out.println((num+1) + ". Salir del sistema");
            int salirOption = num+1;

            String opcion = sc.nextLine();
            try {
                int opNum = Integer.parseInt(opcion);
                if (opNum == cerrarSesionOption) return true;
                if (opNum == salirOption) return false;

                String accion = opciones.get(opNum);
                if (accion == null) {
                    System.out.println("❌ Opción inválida.");
                    continue;
                }

                ejecutarAccionFuncionario(accion, sc, fun, funService);
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ingresar un número válido.");
            }
        }
    }


    private static void ejecutarAccionFuncionario(String accion, Scanner sc, Funcionario fun, FuncionarioServicio funService) {
        switch (accion) {
            case "CREAR_ESTUDIANTE" -> {
                try {
                    System.out.print("Ingrese CI del estudiante: ");
                    String ci = sc.nextLine().trim();

                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine().trim();

                    System.out.print("Ingrese apellido: ");
                    String apellido = sc.nextLine().trim();

                    System.out.print("Ingrese fecha de nacimiento (YYYY-MM-DD): ");
                    LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine().trim());

                    System.out.print("Ingrese contraseña: ");
                    String password = sc.nextLine().trim();

                    System.out.print("Ingrese ID de grupo: ");
                    int idGrupo = Integer.parseInt(sc.nextLine().trim());

                    Estudiante nuevo = funService.crearEstudianteDesdeFuncionario(
                            fun, ci, nombre + "." + apellido, password, nombre, apellido, fechaNacimiento, idGrupo
                    );

                    System.out.println("✅ Estudiante creado: " + nuevo.getCorreo());
                } catch (Exception e) {
                    System.out.println("❌ No se pudo crear el estudiante: " + e.getMessage());
                }
            }

            case "CREAR_FUNCIONARIO" -> {
                try {
                    System.out.print("Ingrese CI del funcionario: ");
                    String ci = sc.nextLine().trim();

                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine().trim();

                    System.out.print("Ingrese apellido: ");
                    String apellido = sc.nextLine().trim();

                    System.out.print("Ingrese fecha de nacimiento (YYYY-MM-DD): ");
                    LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine().trim());

                    System.out.print("Ingrese username: ");
                    String username = sc.nextLine().trim();

                    System.out.print("Ingrese contraseña: ");
                    String password = sc.nextLine().trim();

                    System.out.println("Seleccione rol:");
                    System.out.println("1. ADMINISTRADOR");
                    System.out.println("2. PSICOPEDAGOGO");
                    System.out.println("3. ANALISTA_EDUCATIVO");
                    System.out.println("4. RESPONSABLE_EDUCATIVO");
                    System.out.println("5. AREA_LEGAL");
                    int rolNum = Integer.parseInt(sc.nextLine().trim());

                    Funcionario.Rol rol = switch (rolNum) {
                        case 1 -> Funcionario.Rol.ADMINISTRADOR;
                        case 2 -> Funcionario.Rol.PSICOPEDAGOGO;
                        case 3 -> Funcionario.Rol.ANALISTA_EDUCATIVO;
                        case 4 -> Funcionario.Rol.RESPONSABLE_EDUCATIVO;
                        case 5 -> Funcionario.Rol.AREA_LEGAL;
                        default -> throw new Exception("Rol inválido");
                    };

                    Funcionario nuevo = funService.registrarFuncionario(
                            rol, ci, username, password, nombre, apellido, fechaNacimiento
                    );

                    System.out.println("✅ Funcionario creado: " + nuevo.getCorreo());
                } catch (Exception e) {
                    System.out.println("❌ No se pudo crear el funcionario: " + e.getMessage());
                }
            }

            case "MODIFICAR" -> System.out.println("✏️ Modificar usuario (simulado)");
            case "CONFIDENCIAL" -> System.out.println("🔒 Accediendo a datos confidenciales...");
        }
    }
}