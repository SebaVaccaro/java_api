package DAO;

import SINGLETON.ConexionSingleton;
import modelo.PartSeguimiento;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartSeguimientoDAOImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private PartSeguimientoDAOImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton singleton = mock(ConexionSingleton.class);
        when(singleton.getConexion()).thenReturn(connection);

        conexionSingletonMocked = mockStatic(ConexionSingleton.class);
        conexionSingletonMocked.when(ConexionSingleton::getInstance).thenReturn(singleton);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        dao = new PartSeguimientoDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }


    @Test
    void agregarParticipante_exito() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(1, 10);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = dao.agregarParticipante(ps);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 10);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void agregarParticipante_falla() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(2, 20);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = dao.agregarParticipante(ps);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(resultado);
    }

    @Test
    void agregarParticipante_throwSQLException() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(3, 30);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.agregarParticipante(ps));
        assertEquals("Error SQL", ex.getMessage());
    }


    @Test
    void eliminarParticipante_exito() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(1, 10);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = dao.eliminarParticipante(ps);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 10);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void eliminarParticipante_falla() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(2, 20);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = dao.eliminarParticipante(ps);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(resultado);
    }

    @Test
    void eliminarParticipante_throwSQLException() throws Exception {
        PartSeguimiento ps = new PartSeguimiento(3, 30);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.eliminarParticipante(ps));
        assertEquals("Error SQL", ex.getMessage());
    }


    @Test
    void listarTodos_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_participante")).thenReturn(1, 2);
        when(resultSet.getInt("id_seguimiento")).thenReturn(10, 20);

        List<PartSeguimiento> lista = dao.listarTodos();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdParticipante());
        assertEquals(20, lista.get(1).getIdSeguimiento());
    }

    @Test
    void listarTodos_throwSQLException() throws Exception {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarTodos());
        assertEquals("Error SQL", ex.getMessage());
    }


    @Test
    void listarSeguimientosPorParticipante_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_seguimiento")).thenReturn(10, 20);

        List<Integer> lista = dao.listarSeguimientosPorParticipante(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(2, lista.size());
        assertEquals(10, lista.get(0));
        assertEquals(20, lista.get(1));
    }

    @Test
    void listarSeguimientosPorParticipante_throwSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarSeguimientosPorParticipante(1));
        assertEquals("Error SQL", ex.getMessage());
    }


    @Test
    void listarParticipantesPorSeguimiento_exito() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_participante")).thenReturn(1, 2);

        List<Integer> lista = dao.listarParticipantesPorSeguimiento(10);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0));
        assertEquals(2, lista.get(1));
    }

    @Test
    void listarParticipantesPorSeguimiento_throwSQLException() throws Exception {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarParticipantesPorSeguimiento(10));
        assertEquals("Error SQL", ex.getMessage());
    }
}
