package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidenciaDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private IncidenciaDAOImpl incidenciaDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton singleton = mock(ConexionSingleton.class);
        when(singleton.getConexion()).thenReturn(connection);

        conexionSingletonMocked = mockStatic(ConexionSingleton.class);
        conexionSingletonMocked.when(ConexionSingleton::getInstance).thenReturn(singleton);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);


        incidenciaDAO = new IncidenciaDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }

    @Test
    void insertarIncidencia_exito() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        incidenciaDAO.insertarIncidencia(inc);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setString(2, "Oficina");
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void insertarIncidencia_lanzaSQLException() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Patio");

        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("DB error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.insertarIncidencia(inc));

        assertEquals("DB error", ex.getMessage());
    }

    @Test
    void obtenerIncidencia_exito() throws Exception {

        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt("id_instancia")).thenReturn(1);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class))
                .thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(3);
        when(resultSet.getString("lugar")).thenReturn("Biblioteca");

        Incidencia inc = incidenciaDAO.obtenerIncidencia(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(inc);
        assertEquals(1, inc.getIdInstancia());
        assertEquals("Titulo", inc.getTitulo());
        assertEquals("Biblioteca", inc.getLugar());
    }

    @Test
    void obtenerIncidencia_noEncontrada() throws Exception {

        when(resultSet.next()).thenReturn(false);

        Incidencia inc = incidenciaDAO.obtenerIncidencia(999);

        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNull(inc);
    }

    @Test
    void obtenerIncidencia_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.obtenerIncidencia(10));

        assertEquals("Error", ex.getMessage());
    }


    @Test
    void listarIncidencias_exito() throws Exception {

        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getInt("id_instancia")).thenReturn(1, 2);
        when(resultSet.getString("titulo")).thenReturn("A", "B");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class))
                .thenReturn(OffsetDateTime.now(), OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("D1", "D2");
        when(resultSet.getBoolean("est_activo")).thenReturn(true, false);
        when(resultSet.getInt("id_funcionario")).thenReturn(5, 6);
        when(resultSet.getString("lugar")).thenReturn("Salon", "Oficina");

        List<Incidencia> lista = incidenciaDAO.listarIncidencias();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0).getTitulo());
        assertEquals("B", lista.get(1).getTitulo());
    }

    @Test
    void listarIncidencias_lanzaSQLException() throws Exception {
        when(statement.executeQuery(anyString()))
                .thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.listarIncidencias());

        assertEquals("Error", ex.getMessage());
    }


    @Test
    void listarPorFuncionario_exito() throws Exception {

        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt("id_instancia")).thenReturn(5);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class))
                .thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(10);
        when(resultSet.getString("lugar")).thenReturn("Hall");

        List<Incidencia> lista = incidenciaDAO.listarPorFuncionario(10);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdInstancia());
    }

    @Test
    void listarPorFuncionario_lanzaSQLException() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.listarPorFuncionario(10));

        assertEquals("Error", ex.getMessage());
    }


    @Test
    void actualizarIncidencia_exito() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = incidenciaDAO.actualizarIncidencia(inc);

        verify(preparedStatement).setString(1, "Oficina");
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void actualizarIncidencia_sinFilasAfectadas() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = incidenciaDAO.actualizarIncidencia(inc);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void actualizarIncidencia_lanzaSQLException() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.actualizarIncidencia(inc));

        assertEquals("Error", ex.getMessage());
    }


    @Test
    void eliminarIncidencia_exito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = incidenciaDAO.eliminarIncidencia(99);

        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void eliminarIncidencia_sinFilasAfectadas() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = incidenciaDAO.eliminarIncidencia(99);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void eliminarIncidencia_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class,
                () -> incidenciaDAO.eliminarIncidencia(50));

        assertEquals("Error", ex.getMessage());
    }
}
