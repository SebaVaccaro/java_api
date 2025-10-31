package SINGLETON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionSingleton {

    // Instancia única de la clase (patrón Singleton)
    private static ConexionSingleton instancia;

    // Objeto de conexión a la base de datos
    private Connection conexion;

    // Datos de conexión obtenidos de variables de entorno
    private final String dbHost = System.getenv("DB_HOST");
    private final String dbUser = System.getenv("DB_USER");
    private final String dbPassword = System.getenv("DB_PASSWORD");

    // Constructor privado: establece la conexión y el esquema por defecto
    private ConexionSingleton() throws SQLException {
        try {
            // Conexión a la base de datos
            conexion = DriverManager.getConnection(dbHost, dbUser, dbPassword);

            // Establecer esquema por defecto para todas las consultas
            try (Statement stmt = conexion.createStatement()) {
                stmt.execute("SET search_path TO proyecto_desarrollo_sienep");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al conectar a la base de datos", e);
        }
    }

    // Obtener la instancia única de ConexionSingleton
    public static ConexionSingleton getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionSingleton();
        }
        return instancia;
    }

    // Obtener la conexión a la base de datos
    public Connection getConexion() {
        return conexion;
    }
}
