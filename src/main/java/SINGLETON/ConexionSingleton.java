package main.java.SINGLETON;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionSingleton {
    private static ConexionSingleton instancia;
    private Connection conexion;

    private final String URL = "jdbc:postgresql://localhost:5432/utecdb";
    private final String USUARIO = "postgres";
    private final String PASSWORD = "postgres";

    private ConexionSingleton() throws SQLException {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }

    public static ConexionSingleton getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionSingleton();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }


}
