package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Rol;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;
    private RolDAOImpl rolDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Mock del Singleton
        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        rolDAO = new RolDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }


    @Test
    void testBuscarPorIdEncuentraRol() throws SQLException {
        Rol esperado = new Rol(1, "Administrador", true);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_rol")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("Administrador");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Rol resultado = rolDAO.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(esperado.getIdRol(), resultado.getIdRol());
        assertEquals(esperado.getNombre(), resultado.getNombre());
        assertEquals(esperado.isEstActivo(), resultado.isEstActivo());

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testBuscarPorIdNoEncuentraRol() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Rol resultado = rolDAO.buscarPorId(99);

        assertNull(resultado);
        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testBuscarPorIdLanzaSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error al ejecutar la consulta"));

        SQLException exception = assertThrows(SQLException.class, () -> rolDAO.buscarPorId(5));
        assertEquals("Error al ejecutar la consulta", exception.getMessage());

        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

    }


    @Test
    void testListarTodos_rolesEncontrados() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_rol")).thenReturn(1, 2);
        when(resultSet.getString("nombre")).thenReturn("Admin", "Docente");
        when(resultSet.getBoolean("est_activo")).thenReturn(true, false);

        List<Rol> roles = rolDAO.listarTodos();

        assertEquals(2, roles.size());
        assertEquals("Admin", roles.get(0).getNombre());
        assertEquals("Docente", roles.get(1).getNombre());
        assertTrue(roles.get(0).isEstActivo());
        assertFalse(roles.get(1).isEstActivo());

        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarTodos_listaVacia() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        List<Rol> roles = rolDAO.listarTodos();

        assertTrue(roles.isEmpty());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarTodos_lanzaSQLException() throws SQLException {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error al listar roles"));

        SQLException exception = assertThrows(SQLException.class, () -> rolDAO.listarTodos());
        assertEquals("Error al listar roles", exception.getMessage());

        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }


}

