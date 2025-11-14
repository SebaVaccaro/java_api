package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Seguimiento;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeguimientoDAOImplTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private Statement statement;
    @Mock private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionMockStatic;
    private SeguimientoDAOImpl seguimientoDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton singleton = mock(ConexionSingleton.class);
        when(singleton.getConexion()).thenReturn(connection);

        conexionMockStatic = mockStatic(ConexionSingleton.class);
        conexionMockStatic.when(ConexionSingleton::getInstance).thenReturn(singleton);

        seguimientoDAO = new SeguimientoDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionMockStatic.close();
    }

    //TEST agregar
    @Test
    void testAgregar_exito() throws Exception {
        Seguimiento s = new Seguimiento(1, 10, LocalDate.now(), null, true);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = seguimientoDAO.agregar(s);

        assertTrue(ok);
        verify(preparedStatement).setInt(2, s.getIdEstudiante());
        verify(preparedStatement).setBoolean(5, s.isEstActivo());
    }

    @Test
    void testAgregar_falla() throws Exception {
        Seguimiento s = new Seguimiento(null, 11, LocalDate.now(), null, true);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = seguimientoDAO.agregar(s);

        assertFalse(ok);
    }

    @Test
    void testAgregar_lanzaSQLException() throws Exception {
        Seguimiento s = new Seguimiento(null, 12, LocalDate.now(), null, true);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.agregar(s));
    }

    //TEST actualizar
    @Test
    void testActualizar_exito() throws Exception {
        Seguimiento s = new Seguimiento(5, 2, 10, LocalDate.now(), null, true);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = seguimientoDAO.actualizar(s);

        assertTrue(ok);
        verify(preparedStatement).setInt(6, s.getIdSeguimiento());
        verify(preparedStatement).setBoolean(5, s.isEstActivo());
    }

    @Test
    void testActualizar_falla() throws Exception {
        Seguimiento s = new Seguimiento(6, null, 15, LocalDate.now(), null, true);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = seguimientoDAO.actualizar(s);

        assertFalse(ok);
    }

    @Test
    void testActualizar_lanzaSQLException() throws Exception {
        Seguimiento s = new Seguimiento(7, 3, 20, LocalDate.now(), null, false);

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.actualizar(s));
    }

    //TEST eliminar
    @Test
    void testEliminar_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = seguimientoDAO.eliminar(33);

        assertTrue(ok);
        verify(preparedStatement).setInt(1, 33);
    }

    @Test
    void testEliminar_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = seguimientoDAO.eliminar(33);

        assertFalse(ok);
    }

    @Test
    void testEliminar_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.eliminar(3));
    }

    //TEST buscarPorId


    @Test
    void testBuscarPorId_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Seguimiento s = seguimientoDAO.buscarPorId(999);

        assertNull(s);
    }

    @Test
    void testBuscarPorId_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.buscarPorId(1));
    }

    //TEST listarTodos
    @Test
    void testListarTodos_exito() throws Exception {
        String sql = "SELECT id_seguimiento, id_informe, id_estudiante, fec_inicio, fec_cierre, est_activo FROM seguimientos";
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_seguimiento")).thenReturn(1, 2);
        when(resultSet.getObject("id_informe")).thenReturn(3, null);
        when(resultSet.getInt("id_estudiante")).thenReturn(7, 8);
        when(resultSet.getDate("fec_inicio")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDate("fec_cierre")).thenReturn(null);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        List<Seguimiento> lista = seguimientoDAO.listarTodos();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdSeguimiento());
    }

    @Test
    void testListarTodos_lanzaSQLException() throws Exception {
        when(connection.createStatement()).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.listarTodos());
    }

    //TEST listarPorEstudiante
    @Test
    void testListarPorEstudiante_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_seguimiento")).thenReturn(5);
        when(resultSet.getObject("id_informe")).thenReturn(4);
        when(resultSet.getInt("id_estudiante")).thenReturn(10);
        when(resultSet.getDate("fec_inicio")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDate("fec_cierre")).thenReturn(null);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        List<Seguimiento> lista = seguimientoDAO.listarPorEstudiante(10);

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdSeguimiento());
    }

    @Test
    void testListarPorEstudiante_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.listarPorEstudiante(10));
    }

    //TEST tieneSeguimientoActivo
    @Test
    void testTieneSeguimientoActivo_true() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        assertTrue(seguimientoDAO.tieneSeguimientoActivo(10));
    }

    @Test
    void testTieneSeguimientoActivo_false() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0);

        assertFalse(seguimientoDAO.tieneSeguimientoActivo(10));
    }

    @Test
    void testTieneSeguimientoActivo_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> seguimientoDAO.tieneSeguimientoActivo(10));
    }
}
