package SINGLETON;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexionSingleton {
    private static ConexionSingleton instancia;
    private Connection conexion;

    private final String URL = "jdbc:postgresql://db.epcuoytpsskxbhiubyrc.supabase.co:5432/postgres?sslmode=require";
    private final String USUARIO = "postgres";
    private final String PASSWORD = "maZsud-tanbaw-3nomtu";

    private ConexionSingleton() throws SQLException {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            // Establecemos el esquema por defecto para todas las consultas
            try (Statement stmt = conexion.createStatement()) {
                stmt.execute("SET search_path TO proyecto_desarrollo_sienep");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
