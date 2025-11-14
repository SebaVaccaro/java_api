package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Notificacion;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionDAOImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private NotificacionDAOImpl dao;

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

        dao = new NotificacionDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }


    @Test
    void crearNotificacion_exito() throws Exception {
        Notificacion n = new Notificacion(5, "Asunto", "Mensaje",
                "dest@correo.com", LocalDate.now(), true);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_notificacion")).thenReturn(99);

        Notificacion resultado = dao.crearNotificacion(n);

        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).setString(2, "Asunto");
        verify(preparedStatement).setString(3, "Mensaje");
        verify(preparedStatement).setString(4, "dest@correo.com");
        verify(preparedStatement).setDate(5, Date.valueOf(n.getFecEnvio()));
        verify(preparedStatement).setBoolean(6, true);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(99, resultado.getIdNotificacion());
    }

    @Test
    void crearNotificacion_sinIdGenerado() throws Exception {
        Notificacion n = new Notificacion(3, "A", "M", "d", LocalDate.now(), true);

        when(resultSet.next()).thenReturn(false);

        SQLException ex = assertThrows(SQLException.class, () -> dao.crearNotificacion(n));
        assertEquals("No se obtuvo el id generado de Notificacion", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void crearNotificacion_throwSQLException() throws Exception {
        Notificacion n = new Notificacion(3, "A", "M", "d", LocalDate.now(), true);

        when(preparedStatement.executeQuery()).thenThrow(new SQLException("DB ERROR"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.crearNotificacion(n));
        assertEquals("DB ERROR", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void obtenerNotificacion_exito() throws Exception {
        LocalDate hoy = LocalDate.now();

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_notificacion")).thenReturn(50);
        when(resultSet.getInt("id_instancia")).thenReturn(5);
        when(resultSet.getString("asunto")).thenReturn("Asunto X");
        when(resultSet.getString("mensaje")).thenReturn("Mensaje X");
        when(resultSet.getString("destinatario")).thenReturn("correo@test.com");
        when(resultSet.getDate("fec_envio")).thenReturn(Date.valueOf(hoy));
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Notificacion n = dao.obtenerNotificacion(50);

        verify(preparedStatement).setInt(1, 50);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(n);
        assertEquals(50, n.getIdNotificacion());
        assertEquals("Asunto X", n.getAsunto());
    }

    @Test
    void obtenerNotificacion_noExiste() throws Exception {
        when(resultSet.next()).thenReturn(false);

        Notificacion n = dao.obtenerNotificacion(5);

        assertNull(n);
    }

    @Test
    void obtenerNotificacion_throwSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error X"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.obtenerNotificacion(1));
        assertEquals("Error X", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }



    @Test
    void listarTodas_exito() throws Exception {
        LocalDate hoy = LocalDate.now();

        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getInt("id_notificacion")).thenReturn(1, 2);
        when(resultSet.getInt("id_instancia")).thenReturn(10, 20);
        when(resultSet.getString("asunto")).thenReturn("A1", "A2");
        when(resultSet.getString("mensaje")).thenReturn("M1", "M2");
        when(resultSet.getString("destinatario")).thenReturn("d1@test", "d2@test");
        when(resultSet.getDate("fec_envio")).thenReturn(Date.valueOf(hoy));
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        List<Notificacion> lista = dao.listarTodas();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdNotificacion());
        assertEquals("A2", lista.get(1).getAsunto());
    }

    @Test
    void listarTodas_throwSQLException() throws Exception {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Err"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarTodas());
        assertEquals("Err", ex.getMessage());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }


    @Test
    void actualizarNotificacion_exito() throws Exception {
        Notificacion n = new Notificacion(7, 55, "A", "M", "d", LocalDate.now(), true);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.actualizarNotificacion(n);

        verify(preparedStatement).setInt(1, 55);
        verify(preparedStatement).setString(2, "A");
        verify(preparedStatement).setString(3, "M");
        verify(preparedStatement).setString(4, "d");
        verify(preparedStatement).setDate(5, Date.valueOf(n.getFecEnvio()));
        verify(preparedStatement).setBoolean(6, true);
        verify(preparedStatement).setInt(7, 7);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void actualizarNotificacion_falla() throws Exception {
        Notificacion n = new Notificacion(7, 55, "A", "M", "d", LocalDate.now(), true);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.actualizarNotificacion(n);

        assertFalse(ok);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void actualizarNotificacion_throwSQLException() throws Exception {
        Notificacion n = new Notificacion(7, 55, "A", "M", "d", LocalDate.now(), true);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.actualizarNotificacion(n));
        assertEquals("SQL error", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }



    @Test
    void eliminarNotificacion_exito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.eliminarNotificacion(10);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void eliminarNotificacion_falla() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.eliminarNotificacion(5);

        assertFalse(ok);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void eliminarNotificacion_throwSQLException() throws Exception {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.eliminarNotificacion(5));
        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }
}
