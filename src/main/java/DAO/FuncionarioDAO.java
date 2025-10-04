package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private final Connection conn;

    public FuncionarioDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Helpers Rol <-> id_rol
    private int mapRolToId(Funcionario.Rol rol) {
        switch (rol) {
            case ADMINISTRADOR: return 1;
            case PSICOPEDAGOGO: return 2;
            case ANALISTA_EDUCATIVO: return 3;
            case RESPONSABLE_EDUCATIVO: return 4;
            case AREA_LEGAL: return 5;
            default: throw new IllegalArgumentException("Rol no soportado: " + rol);
        }
    }

    private Funcionario.Rol mapIdToRol(int idRol) {
        switch (idRol) {
            case 1: return Funcionario.Rol.ADMINISTRADOR;
            case 2: return Funcionario.Rol.PSICOPEDAGOGO;
            case 3: return Funcionario.Rol.ANALISTA_EDUCATIVO;
            case 4: return Funcionario.Rol.RESPONSABLE_EDUCATIVO;
            case 5: return Funcionario.Rol.AREA_LEGAL;
            default: throw new IllegalArgumentException("id_rol no soportado: " + idRol);
        }
    }

    // Crear funcionario
    public Funcionario crearFuncionario(Funcionario f) throws SQLException {

        try {
            conn.setAutoCommit(false); // inicio transacción


        String sqlUsuario = "INSERT INTO usuarios(cedula, nombre, apellido, username, password, correo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";
        try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
            ps.setString(1, f.getCi());
            ps.setString(2, f.getNombre());
            ps.setString(3, f.getApellido());
            ps.setString(4, f.getUsername());
            ps.setString(5, f.getPassword());
            ps.setString(6, f.getCorreo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) f.setId(rs.getInt("id_usuario"));
        }

        String sqlFunc = "INSERT INTO funcionarios(id_usuario, id_rol, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlFunc)) {
            ps.setInt(1, f.getId());
            if (f.getRol() != null) {
                ps.setInt(2, mapRolToId(f.getRol()));
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBoolean(3, f.isActivo());

            ps.executeUpdate();
        }

            conn.commit(); // confirmamos todo
        } catch (SQLException e) {
            conn.rollback(); // revertimos todo si algo falla
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }


        return f;
    }

    // Obtener funcionario por id
    public Funcionario obtenerFuncionario(int id) throws SQLException {
        String sql = "SELECT u.id_usuario, u.cedula, u.username, u.password, u.nombre, u.apellido, u.correo, " +
                "f.id_rol, f.est_activo " +
                "FROM usuarios u JOIN funcionarios f ON u.id_usuario=f.id_usuario " +
                "WHERE u.id_usuario=?";
        Funcionario fun = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Funcionario.Rol rol = null;
                int idRol = rs.getInt("id_rol");
                if (idRol != 0) {
                    rol = mapIdToRol(idRol);
                }

                fun = new Funcionario(
                        rol,
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        null, // telefono
                        null  // direccion
                );
                fun.setActivo(rs.getBoolean("est_activo"));

            }
        }
        return fun;
    }

    // Listar todos los funcionarios
    public List<Funcionario> listarFuncionarios() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.cedula, u.username, u.password, u.nombre, u.apellido, u.correo, " +
                "f.id_rol, f.est_activo " +
                "FROM usuarios u JOIN funcionarios f ON u.id_usuario=f.id_usuario";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Funcionario.Rol rol = null;
                int idRol = rs.getInt("id_rol");
                if (idRol != 0) {
                    rol = mapIdToRol(idRol);
                }

                Funcionario f = new Funcionario(
                        rol,
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        null,
                        null
                );
                f.setActivo(rs.getBoolean("est_activo"));

                funcionarios.add(f);
            }
        }
        return funcionarios;
    }

// Actualizar funcionario

    public boolean actualizarFuncionario(Funcionario f) throws SQLException {
        int filasUsuarios = 0;
        int filasFuncionario = 0;

        String sqlUsuario = "UPDATE usuarios SET cedula=?, nombre=?, apellido=?, username=?, password=?, correo=? " +
                "WHERE id_usuario=?";
        try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
            psUsuario.setString(1, f.getCi());
            psUsuario.setString(2, f.getNombre());
            psUsuario.setString(3, f.getApellido());
            psUsuario.setString(4, f.getUsername());
            psUsuario.setString(5, f.getPassword());
            psUsuario.setString(6, f.getCorreo());
            psUsuario.setInt(7, f.getId());
            filasUsuarios = psUsuario.executeUpdate();
        }

        String sqlFunc = "UPDATE funcionarios SET id_rol=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement psFunc = conn.prepareStatement(sqlFunc)) {
            if (f.getRol() != null) {
                psFunc.setInt(1, mapRolToId(f.getRol()));
            } else {
                psFunc.setNull(1, Types.INTEGER);
            }
            psFunc.setBoolean(2, f.isActivo());
            psFunc.setInt(3, f.getId());
            filasFuncionario = psFunc.executeUpdate();
        }

        return (filasUsuarios > 0) && (filasFuncionario > 0);
    }



    // Baja lógica
    public boolean eliminarFuncionario(int id) throws SQLException {
        String sql = "UPDATE funcionarios SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
