package DAO;

import DAO.interfaz.SeguimientoDAO;
import SINGLETON.ConexionSingleton;
import modelo.Seguimiento;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeguimientoDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionMockStatic;

    private SeguimientoDAO seguimientoDAO;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionMockStatic = mockStatic(ConexionSingleton.class);
        conexionMockStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        seguimientoDAO = new SeguimientoDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionMockStatic.close();
        clearAllCaches();
    }

    // ============================================================
    //                           AGREGAR
    // ============================================================

    @Test
    void testAgregarExito() throws Exception {
        Seguimiento s = new Seguimiento(1, 10, LocalDate.now(), null, true);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = seguimientoDAO.agregar(s);

        verify(preparedStatement).setInt(2, s.getIdEstudiante());
        verify(preparedStatement).setBoolean(5, s.isEstActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void testAgregarSinFilasAfectadas() throws Exception {
        Seguimiento s = new Seguimiento(null, 11, LocalDate.now(), null, true);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = seguimientoDAO.agregar(s);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testAgregarLanzaSQLException() throws Exception {
        Seguimiento s = new Seguimiento(null, 12, LocalDate.now(), null, true);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("DB error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.agregar(s));

        assertEquals("DB error", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    // ============================================================
    //                          ACTUALIZAR
    // ============================================================

    @Test
    void testActualizarExito() throws Exception {
        Seguimiento s = new Seguimiento(5, 2, 10, LocalDate.now(), null, true);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = seguimientoDAO.actualizar(s);

        verify(preparedStatement).setInt(6, s.getIdSeguimiento());
        verify(preparedStatement).setBoolean(5, s.isEstActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void testActualizarSinFilasAfectadas() throws Exception {
        Seguimiento s = new Seguimiento(6, null, 15, LocalDate.now(), null, true);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = seguimientoDAO.actualizar(s);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarLanzaSQLException() throws Exception {
        Seguimiento s = new Seguimiento(7, 3, 20, LocalDate.now(), null, false);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.actualizar(s));

        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    // ============================================================
    //                           ELIMINAR
    // ============================================================

    @Test
    void testEliminarExito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = seguimientoDAO.eliminar(33);

        verify(preparedStatement).setInt(1, 33);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void testEliminarSinFilasAfectadas() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = seguimientoDAO.eliminar(33);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarLanzaSQLException() throws Exception {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.eliminar(3));

        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    // ============================================================
    //                        BUSCAR POR ID
    // ============================================================

    @Test
    void testBuscarPorIdExito() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_seguimiento")).thenReturn(1);
        when(resultSet.getObject("id_informe")).thenReturn(2);
        when(resultSet.getInt("id_estudiante")).thenReturn(10);
        when(resultSet.getDate("fec_inicio")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDate("fec_cierre")).thenReturn(null);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Seguimiento resultado = seguimientoDAO.buscarPorId(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSeguimiento());
    }

    @Test
    void testBuscarPorIdNoExiste() throws Exception {
        when(resultSet.next()).thenReturn(false);

        Seguimiento resultado = seguimientoDAO.buscarPorId(999);

        assertNull(resultado);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testBuscarPorIdLanzaSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.buscarPorId(1));

        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    // ============================================================
    //                        LISTAR TODOS
    // ============================================================

    @Test
    void testListarTodosExito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_seguimiento")).thenReturn(1, 2);
        when(resultSet.getObject("id_informe")).thenReturn(3, null);
        when(resultSet.getInt("id_estudiante")).thenReturn(7, 8);
        when(resultSet.getDate("fec_inicio")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDate("fec_cierre")).thenReturn(null);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        List<Seguimiento> lista = seguimientoDAO.listarTodos();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdSeguimiento());
    }

    @Test
    void testListarTodosLanzaSQLException() throws Exception {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.listarTodos());

        assertEquals("Error", ex.getMessage());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    // ============================================================
    //                   LISTAR POR ESTUDIANTE
    // ============================================================

    @Test
    void testListarPorEstudianteExito() throws Exception {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_seguimiento")).thenReturn(5);
        when(resultSet.getObject("id_informe")).thenReturn(4);
        when(resultSet.getInt("id_estudiante")).thenReturn(10);
        when(resultSet.getDate("fec_inicio")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getDate("fec_cierre")).thenReturn(null);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        List<Seguimiento> lista = seguimientoDAO.listarPorEstudiante(10);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdSeguimiento());
    }

    @Test
    void testListarPorEstudianteLanzaSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.listarPorEstudiante(10));

        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    // ============================================================
    //                  TIENE SEGUIMIENTO ACTIVO
    // ============================================================

    @Test
    void testTieneSeguimientoActivoTrue() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        assertTrue(seguimientoDAO.tieneSeguimientoActivo(10));
    }

    @Test
    void testTieneSeguimientoActivoFalse() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(0);

        assertFalse(seguimientoDAO.tieneSeguimientoActivo(10));
    }

    @Test
    void testTieneSeguimientoActivoLanzaSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> seguimientoDAO.tieneSeguimientoActivo(10));

        assertEquals("Error", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }
}
