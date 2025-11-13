package DAO;

import DAO.interfaz.LoginDAO;
import SINGLETON.ConexionSingleton;
import modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;

    private LoginDAO loginDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        loginDAO = new LoginDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }


    @Test
    void testObtenerUsuarioPorUsernameExito() throws SQLException {
        String username = "wilmar.hellbusch";
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_usuario")).thenReturn(1);
        when(resultSet.getString("cedula")).thenReturn("54321098");
        when(resultSet.getString("nombre")).thenReturn("Wilmar");
        when(resultSet.getString("apellido")).thenReturn("Hellbusch");
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getString("password")).thenReturn("contraseña");
        when(resultSet.getString("correo")).thenReturn("wilmar.hellbusch@utec.edu.uy");

        Usuario usuario = loginDAO.obtenerUsuarioPorUsername(username);

        verify(preparedStatement).setString(1, username);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(usuario);
        assertEquals(1, usuario.getIdUsuario());
        assertEquals("Wilmar", usuario.getNombre());
        assertEquals(username, usuario.getUsername());
    }


    @Test
    void testObtenerUsuarioPorUsernameNoExiste() throws SQLException {
        String username = "usuario.inexistente";
        when(resultSet.next()).thenReturn(false);

        Usuario usuario = loginDAO.obtenerUsuarioPorUsername(username);

        verify(preparedStatement).setString(1, username);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNull(usuario);
    }


    @Test
    void testObtenerUsuarioPorUsernameLanzaSQLException() throws SQLException {
        String username = "usuario";
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error de Base de Datos"));

        SQLException exception = assertThrows(SQLException.class,
                () -> loginDAO.obtenerUsuarioPorUsername(username));

        assertEquals("Error de Base de Datos", exception.getMessage());
    }
}
