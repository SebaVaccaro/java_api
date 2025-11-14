package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Recibe;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RecibeDAOImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private RecibeDAOImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockSingleton = mock(ConexionSingleton.class);
        when(mockSingleton.getConexion()).thenReturn(connection);

        conexionSingletonMocked = mockStatic(ConexionSingleton.class);
        conexionSingletonMocked.when(ConexionSingleton::getInstance).thenReturn(mockSingleton);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        dao = new RecibeDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }


    @Test
    void agregarRecibe_exito() throws Exception {
        Recibe r = new Recibe(10, 5);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.agregar(r);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).setInt(2, 5);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void agregarRecibe_falla() throws Exception {
        Recibe r = new Recibe(10, 5);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.agregar(r);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void agregarRecibe_LanzaSQLException() throws Exception {
        Recibe r = new Recibe(3, 4);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("ERROR"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.agregar(r));
        assertEquals("ERROR", ex.getMessage());

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void eliminarRecibe_exito() throws Exception {
        Recibe r = new Recibe(7, 2);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.eliminar(r);

        verify(preparedStatement).setInt(1, 7);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void eliminarRecibe_falla() throws Exception {
        Recibe r = new Recibe(7, 2);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.eliminar(r);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void eliminarRecibe_LanzaSQLException() throws Exception {
        Recibe r = new Recibe(1, 1);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("X"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.eliminar(r));
        assertEquals("X", ex.getMessage());

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void listarTodosRecibe_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_notificacion")).thenReturn(1, 2);
        when(resultSet.getInt("id_usuario")).thenReturn(10, 20);

        List<Recibe> lista = dao.listarTodos();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdNotificacion());
        assertEquals(20, lista.get(1).getIdUsuario());
    }

    @Test
    void listarTodosRecibe_SQLException() throws Exception {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("ERROR"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarTodos());
        assertEquals("ERROR", ex.getMessage());

        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }


    @Test
    void listarUsuariosPorNotificacion_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_usuario")).thenReturn(5, 8);

        List<Integer> usuarios = dao.listarUsuariosPorNotificacion(9);

        verify(preparedStatement).setInt(1, 9);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(2, usuarios.size());
        assertEquals(5, usuarios.get(0));
        assertEquals(8, usuarios.get(1));
    }

    @Test
    void listarUsuariosPorNotificacion_sinRegistros() throws Exception {
        when(resultSet.next()).thenReturn(false);

        List<Integer> usuarios = dao.listarUsuariosPorNotificacion(9);


        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertTrue(usuarios.isEmpty());
    }

    @Test
    void listarUsuariosPorNotificacion_LanzaSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("ERROR"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarUsuariosPorNotificacion(9));
        assertEquals("ERROR", ex.getMessage());

        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void listarNotificacionesPorUsuario_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_notificacion")).thenReturn(11, 22);

        List<Integer> notificaciones = dao.listarNotificacionesPorUsuario(3);

        verify(preparedStatement).setInt(1, 3);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(2, notificaciones.size());
        assertEquals(11, notificaciones.get(0));
        assertEquals(22, notificaciones.get(1));
    }

    @Test
    void listarNotificacionesPorUsuario_sinRegistros() throws Exception {
        when(resultSet.next()).thenReturn(false);

        List<Integer> usuarios = dao.listarNotificacionesPorUsuario(9);


        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertTrue(usuarios.isEmpty());
    }


    @Test
    void listarNotificacionesPorUsuario_LanzaSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("ERROR"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarNotificacionesPorUsuario(3));
        assertEquals("ERROR", ex.getMessage());

        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }
}
