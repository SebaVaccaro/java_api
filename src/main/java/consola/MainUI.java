package consola;

import consola.Estudiante.EstudianteUI;
import consola.Login.LoginUI;
import consola.Admin.AdminUI;
import modelo.Usuario;
import modelo.Funcionario;
import modelo.Estudiante;

public class MainUI {

    public void iniciar() {

        LoginUI loginUI = new LoginUI();
        Usuario usuario = null;

        while (true) {

            usuario = loginUI.iniciar();
            if(usuario == null){
                return;
            }
            if (usuario instanceof Estudiante est) {
                System.out.println("\n🎓 Bienvenido, estudiante " + est.getNombre() + "!");
                EstudianteUI estudianteUI= new EstudianteUI(est);
                estudianteUI.iniciar();
            }
            else if (usuario instanceof Funcionario func) {
                System.out.println("\n🏢 Bienvenido, funcionario " + func.getNombre() + "!");
                AdminUI adminUI = new AdminUI(func);
                adminUI.iniciar();
            }

            else {
                System.out.println("\n⚠️ Tipo de usuario desconocido. No se puede continuar.");
            }

            usuario = null;
            System.out.println("\n🔁 Cerrando sesión...\n");
        }
    }
}
