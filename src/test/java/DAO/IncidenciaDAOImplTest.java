package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidenciaDAOImplTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private Statement statement;
    @Mock private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionMockStatic;

    private IncidenciaDAOImpl incidenciaDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton singleton = mock(ConexionSingleton.class);
        when(singleton.getConexion()).thenReturn(connection);

        conexionMockStatic = mockStatic(ConexionSingleton.class);
        conexionMockStatic.when(ConexionSingleton::getInstance).thenReturn(singleton);

        incidenciaDAO = new IncidenciaDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionMockStatic.close();
    }

    //TEST Insertar Incidencia

    @Test
    void testInsertarIncidencia_exito() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        incidenciaDAO.insertarIncidencia(inc);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setString(2, "Oficina");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testInsertarIncidencia_lanzaSQLException() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Patio");

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.insertarIncidencia(inc));
    }

    //TEST Obtener Incidencia

    @Test
    void testObtenerIncidencia_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_instancia")).thenReturn(1);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(3);
        when(resultSet.getString("lugar")).thenReturn("Biblioteca");

        Incidencia inc = incidenciaDAO.obtenerIncidencia(1);

        assertNotNull(inc);
        assertEquals(1, inc.getIdInstancia());
        assertEquals("Titulo", inc.getTitulo());
        assertEquals("Biblioteca", inc.getLugar());
    }

    @Test
    void testObtenerIncidencia_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Incidencia inc = incidenciaDAO.obtenerIncidencia(999);

        assertNull(inc);
    }

    @Test
    void testObtenerIncidencia_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.obtenerIncidencia(10));
    }

    //TEST Listar Incidencias

    @Test
    void testListarIncidencias_exito() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

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

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdInstancia());
        assertEquals("B", lista.get(1).getTitulo());
    }

    @Test
    void testListarIncidencias_lanzaSQLException() throws Exception {
        when(connection.createStatement()).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.listarIncidencias());
    }

    //TEST Listar por Funcionario

    @Test
    void testListarPorFuncionario_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_instancia")).thenReturn(5);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(10);
        when(resultSet.getString("lugar")).thenReturn("Hall");

        List<Incidencia> lista = incidenciaDAO.listarPorFuncionario(10);

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdInstancia());
    }

    @Test
    void testListarPorFuncionario_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.listarPorFuncionario(10));
    }

    //TEST Actualizar Incidencia

    @Test
    void testActualizarIncidencia_exito() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = incidenciaDAO.actualizarIncidencia(inc);

        assertTrue(resultado);
        verify(preparedStatement).setString(1, "Oficina");
        verify(preparedStatement).setInt(2, 1);
    }

    @Test
    void testActualizarIncidencia_falla() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = incidenciaDAO.actualizarIncidencia(inc);

        assertFalse(resultado);
    }

    @Test
    void testActualizarIncidencia_lanzaSQLException() throws Exception {
        Incidencia inc = new Incidencia(1,"T",OffsetDateTime.now(),"D",true,3,"Oficina");

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.actualizarIncidencia(inc));
    }

    //TEST Eliminar Incidencia

    @Test
    void testEliminarIncidencia_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = incidenciaDAO.eliminarIncidencia(99);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 99);
    }

    @Test
    void testEliminarIncidencia_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = incidenciaDAO.eliminarIncidencia(99);

        assertFalse(resultado);
    }

    @Test
    void testEliminarIncidencia_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        assertThrows(SQLException.class, () -> incidenciaDAO.eliminarIncidencia(50));
    }
}
