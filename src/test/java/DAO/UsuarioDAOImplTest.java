package DAO;

import DAO.interfaz.UsuarioDAO;
import SINGLETON.ConexionSingleton;
import modelo.Estudiante;
import modelo.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);
        // conexionSingletonMockedStatic.when(ConexionSingleton.getInstance()).thenReturn(mockedSingleton);


        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        usuarioDAO = new UsuarioDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }

    @Test
    public void testInsertarEstudianteExitoso() throws SQLException {
        //preparamos los datos de prueba
        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);

        //agreagamos comportamiento a los elementos de jdbc
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_usuario")).thenReturn(2);

        //ejecución
        int idGenerado = usuarioDAO.insertarUsuario(usuario);

        //verificación
        verify(preparedStatement).setString(1, usuario.getCedula());
        verify(preparedStatement).setString(2, usuario.getNombre());
        verify(preparedStatement).setString(3, usuario.getApellido());
        verify(preparedStatement).setString(4, usuario.getUsername());
        verify(preparedStatement).setString(5, usuario.getPassword());
        verify(preparedStatement).setString(6, usuario.getCorreo());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(2, idGenerado);
    }

    @Test
    public void insertarEstudianteLanzaSQLException() throws SQLException{
        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error de base de datos"));


        SQLException exception = assertThrows(SQLException.class, () -> usuarioDAO.insertarUsuario(usuario));

        assertEquals("Error de base de datos", exception.getMessage());
    }

    @Test
    public void testInsertarEstudianteNoDevuelveIdLanzaSQL() throws SQLException {
        //preparamos los datos de prueba
        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);
        when(resultSet.next()).thenReturn(false);

        //ejecución
        SQLException exception = assertThrows(SQLException.class, () -> usuarioDAO.insertarUsuario(usuario));

        assertEquals("No se pudo obtener el id generado de usuario.", exception.getMessage());
    }

    @Test
    public void testActualizarUsuarioOk() throws SQLException {
        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = usuarioDAO.actualizarUsuario(usuario);

        verify(preparedStatement).setString(1, usuario.getCedula());
        verify(preparedStatement).setString(2, usuario.getNombre());
        verify(preparedStatement).setString(3, usuario.getApellido());
        verify(preparedStatement).setString(4, usuario.getUsername());
        verify(preparedStatement).setString(5, usuario.getPassword());
        verify(preparedStatement).setString(6, usuario.getCorreo());
        verify(preparedStatement).setInt(7, usuario.getIdUsuario());
        verify(preparedStatement).close();

        assertTrue(resultado);

    }

    @Test
    public void testActualizarUsuarioFalla() throws SQLException {
        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = usuarioDAO.actualizarUsuario(usuario);

        verify(preparedStatement).setString(1, usuario.getCedula());
        verify(preparedStatement).setString(2, usuario.getNombre());
        verify(preparedStatement).setString(3, usuario.getApellido());
        verify(preparedStatement).setString(4, usuario.getUsername());
        verify(preparedStatement).setString(5, usuario.getPassword());
        verify(preparedStatement).setString(6, usuario.getCorreo());
        verify(preparedStatement).setInt(7, usuario.getIdUsuario());
        verify(preparedStatement).close();

        assertFalse(resultado);

    }

    @Test
    public void testActualizarUsuarioLanzaSQLException() throws SQLException {

        Usuario usuario = new Estudiante(0, "11111111", "Pepe", "Perez", "pperez", "123456", "pperez@gmail.com", 1, true);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error de base de datos"));


        SQLException exception = assertThrows(SQLException.class, () -> usuarioDAO.actualizarUsuario(usuario));

        assertEquals("Error de base de datos", exception.getMessage());
    }

}
