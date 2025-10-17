package consola;

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
            while (usuario == null) {
                usuario = loginUI.iniciar();
            }

            if (usuario instanceof Estudiante est) {
                System.out.println("\n🎓 Bienvenido, estudiante " + est.getNombre() + "!");
                // Aquí podrías abrir una interfaz de estudiante si la tienes
            }
            else if (usuario instanceof Funcionario func) {
                System.out.println("\n🏢 Bienvenido, funcionario " + func.getNombre() + "!");
                // 👇 Pasamos el objeto func (Funcionario) al constructor
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
