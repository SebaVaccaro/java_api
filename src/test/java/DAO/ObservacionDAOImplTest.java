package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Observacion;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObservacionDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;
    private ObservacionDAOImpl observacionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        observacionDAO = new ObservacionDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }


    @Test
    void testCrearObservacionExito() throws SQLException {
        Observacion obs = new Observacion(1, 2, "Titulo", "Contenido", OffsetDateTime.now(), true);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_observacion")).thenReturn(10);

        Observacion resultado = observacionDAO.crearObservacion(obs);

        assertEquals(10, resultado.getIdObservacion());
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).setString(3, "Titulo");
        verify(preparedStatement).setString(4, "Contenido");
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testCrearObservacionLanzaSQLException() throws SQLException {
        Observacion obs = new Observacion(1, 2, "Titulo", "Contenido", OffsetDateTime.now(), true);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error al insertar observacion"));

        SQLException ex = assertThrows(SQLException.class, () -> observacionDAO.crearObservacion(obs));
        assertEquals("Error al insertar observacion", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testCrearObservacionNoDevuelveIdGenerado() throws Exception {
        Observacion observacion = new Observacion(
                1, 2, "Título", "Contenido", OffsetDateTime.now(), true
        );


        when(resultSet.next()).thenReturn(false);

        SQLException exception = assertThrows(SQLException.class,
                () -> observacionDAO.crearObservacion(observacion));

        assertEquals("No se devolvió el ID generado para la observación.", exception.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testObtenerObservacionExito() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_observacion")).thenReturn(5);
        when(resultSet.getInt("id_funcionario")).thenReturn(1);
        when(resultSet.getInt("id_estudiante")).thenReturn(2);
        when(resultSet.getString("titulo")).thenReturn("Prueba");
        when(resultSet.getString("contenido")).thenReturn("Contenido prueba");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Observacion o = observacionDAO.obtenerObservacion(5);

        assertNotNull(o);
        assertEquals(5, o.getIdObservacion());
        assertEquals("Prueba", o.getTitulo());
        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testObtenerObservacionNoEncuentra() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Observacion o = observacionDAO.obtenerObservacion(99);
        assertNull(o);
        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testObtenerObservacion_lanzaSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error al obtener observacion"));

        SQLException ex = assertThrows(SQLException.class, () -> observacionDAO.obtenerObservacion(1));
        assertEquals("Error al obtener observacion", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testListarTodasExito() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_observacion")).thenReturn(1, 2);
        when(resultSet.getInt("id_funcionario")).thenReturn(10, 11);
        when(resultSet.getInt("id_estudiante")).thenReturn(20, 21);
        when(resultSet.getString("titulo")).thenReturn("Obs1", "Obs2");
        when(resultSet.getString("contenido")).thenReturn("Cont1", "Cont2");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class))
                .thenReturn(OffsetDateTime.now(), OffsetDateTime.now());
        when(resultSet.getBoolean("est_activo")).thenReturn(true, false);

        List<Observacion> lista = observacionDAO.listarTodas();

        assertEquals(2, lista.size());
        assertEquals("Obs1", lista.get(0).getTitulo());
        assertFalse(lista.get(1).isEstActivo());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarTodasVacia() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        List<Observacion> lista = observacionDAO.listarTodas();

        assertTrue(lista.isEmpty());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarTodas_lanzaSQLException() throws SQLException {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error al listar observaciones"));

        SQLException ex = assertThrows(SQLException.class, () -> observacionDAO.listarTodas());
        assertEquals("Error al listar observaciones", ex.getMessage());
        verify(statement).executeQuery(anyString());
    }


    @Test
    void testActualizarObservacion_exito() throws SQLException {
        Observacion obs = new Observacion(1, 10, 20, "Titulo", "Cont", OffsetDateTime.now(), true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = observacionDAO.actualizarObservacion(obs);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).setInt(2, 20);
        verify(preparedStatement).setString(3, "Titulo");
        verify(preparedStatement).setString(4, "Cont");
        verify(preparedStatement).setInt(7, 1);
    }

    @Test
    void testActualizarObservacion_noActualiza() throws SQLException {
        Observacion obs = new Observacion(1, 10, 20, "Titulo", "Cont", OffsetDateTime.now(), true);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = observacionDAO.actualizarObservacion(obs);

        assertFalse(resultado);
    }

    @Test
    void testActualizarObservacion_lanzaSQLException() throws SQLException {
        Observacion obs = new Observacion(1, 10, 20, "Titulo", "Cont", OffsetDateTime.now(), true);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error al actualizar observacion"));

        SQLException ex = assertThrows(SQLException.class, () -> observacionDAO.actualizarObservacion(obs));
        assertEquals("Error al actualizar observacion", ex.getMessage());
    }

    // ---------- TEST eliminarObservacion ----------

    @Test
    void testEliminarObservacion_exito() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = observacionDAO.eliminarObservacion(5);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 5);
    }

    @Test
    void testEliminarObservacion_noElimina() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = observacionDAO.eliminarObservacion(5);

        assertFalse(resultado);
    }

    @Test
    void testEliminarObservacion_lanzaSQLException() throws SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error al eliminar observacion"));

        SQLException ex = assertThrows(SQLException.class, () -> observacionDAO.eliminarObservacion(5));
        assertEquals("Error al eliminar observacion", ex.getMessage());
    }
}
