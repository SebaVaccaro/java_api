package servicios;

import DAO.TeleUsuarioDAOImpl;
import DAO.EstudianteDAOImpl;
import DAO.FuncionarioDAOImpl;
import SINGLETON.ConexionSingleton;
import modelo.TeleUsuario;
import modelo.Estudiante;
import modelo.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioServicio {

    private final TeleUsuarioDAOImpl dao;
    private final EstudianteDAOImpl estudianteDAO;
    private final FuncionarioDAOImpl funcionarioDAO;

    public TeleUsuarioServicio() throws SQLException {
        this.dao = new TeleUsuarioDAOImpl();
        this.estudianteDAO = new EstudianteDAOImpl();

        Connection conn = ConexionSingleton.getInstance().getConexion();
        this.funcionarioDAO = new FuncionarioDAOImpl(conn);
    }


    public TeleUsuario agregarTelefono(String numero, int idUsuario) throws SQLException {

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
        }

        if (!numero.matches("[0-9\\s\\-()+]+")) {
            throw new IllegalArgumentException("El número de teléfono contiene caracteres inválidos.");
        }

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        if (!validarUsuarioActivo(idUsuario)) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no está activo.");
        }

        List<TeleUsuario> telefonos = dao.listarTodos();
        for (TeleUsuario tel : telefonos) {
            if (tel.getIdUsuario() == idUsuario && tel.getNumero().equals(numero.trim())) {
                throw new IllegalArgumentException("El número " + numero + " ya está registrado para el usuario " + idUsuario + ".");
            }
        }

        TeleUsuario t = new TeleUsuario(numero.trim(), idUsuario);
        return dao.crearTeleUsuario(t);
    }

    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        // Validar ID de teléfono
        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleUsuario telefonoExistente = dao.obtenerTeleUsuario(idTelefono);
        if (telefonoExistente == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
        }

        if (!numero.matches("[0-9\\s\\-()+]+")) {
            throw new IllegalArgumentException("El número de teléfono contiene caracteres inválidos.");
        }

        // Validar ID de usuario
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        if (!validarUsuarioActivo(idUsuario)) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no está activo.");
        }

        List<TeleUsuario> telefonos = dao.listarTodos();
        for (TeleUsuario tel : telefonos) {
            if (tel.getIdTelefono() != idTelefono &&
                    tel.getIdUsuario() == idUsuario &&
                    tel.getNumero().equals(numero.trim())) {
                throw new IllegalArgumentException("El número " + numero + " ya está registrado para el usuario " + idUsuario + ".");
            }
        }

        TeleUsuario t = new TeleUsuario(idTelefono, numero.trim(), idUsuario);
        return dao.actualizarTeleUsuario(t);
    }

    // Eliminar teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleUsuario telefono = dao.obtenerTeleUsuario(idTelefono);
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        return dao.eliminarTeleUsuario(idTelefono);
    }

    public TeleUsuario buscarPorId(int idTelefono) throws SQLException {

        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleUsuario telefono = dao.obtenerTeleUsuario(idTelefono);
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        return telefono;
    }


    public List<TeleUsuario> listarTodos() throws SQLException {
        return dao.listarTodos();
    }


    public List<TeleUsuario> listarPorUsuario(int idUsuario) throws SQLException {

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        List<TeleUsuario> todosTelefonos = dao.listarTodos();
        return todosTelefonos.stream()
                .filter(tel -> tel.getIdUsuario() == idUsuario)
                .toList();
    }

    private boolean validarUsuarioExiste(int idUsuario) throws SQLException {
        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idUsuario);
        if (estudiante != null) {
            return true;
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idUsuario);
        return funcionario != null;
    }


    private boolean validarUsuarioActivo(int idUsuario) throws SQLException {

        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idUsuario);
        if (estudiante != null) {
            return estudiante.isActivo();
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (funcionario != null) {
            return funcionario.isActivo();
        }

        return false;
    }
}
