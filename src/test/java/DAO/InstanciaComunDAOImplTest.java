package DAO;

import SINGLETON.ConexionSingleton;
import modelo.InstanciaComun;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstanciaComunDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionMockStatic;

    private InstanciaComunDAOImpl instanciaComunDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockSingleton = mock(ConexionSingleton.class);
        when(mockSingleton.getConexion()).thenReturn(connection);

        conexionMockStatic = mockStatic(ConexionSingleton.class);
        conexionMockStatic.when(ConexionSingleton::getInstance).thenReturn(mockSingleton);

        instanciaComunDAO = new InstanciaComunDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionMockStatic.close();
    }

    //TEST Insertar

    @Test
    void testInsertarInstanciaComun() throws Exception {
        InstanciaComun instancia = new InstanciaComun(1, "T", OffsetDateTime.now(), "D", true, 10, 5);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        instanciaComunDAO.insertarInstanciaComun(instancia);

        verify(preparedStatement).setInt(1, instancia.getIdInstancia());
        verify(preparedStatement).setInt(2, instancia.getIdSeguimiento());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testInsertarInstanciaComun_lanzaSQLException() throws Exception {
        InstanciaComun ic = new InstanciaComun(1, "T", OffsetDateTime.now(), "D", true, 4, 10);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.insertarInstanciaComun(ic));
    }


    //TEST Obtener

    @Test
    void testObtenerInstanciaComun_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_instancia")).thenReturn(1);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(3);
        when(resultSet.getInt("id_seguimiento")).thenReturn(8);

        InstanciaComun ic = instanciaComunDAO.obtenerInstanciaComun(1);

        assertNotNull(ic);
        assertEquals(1, ic.getIdInstancia());
        assertEquals("Titulo", ic.getTitulo());
        assertEquals(8, ic.getIdSeguimiento());
    }

    @Test
    void testObtenerInstanciaComun_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        InstanciaComun ic = instanciaComunDAO.obtenerInstanciaComun(999);

        assertNull(ic);
    }

    @Test
    void testObtenerInstanciaComun_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.obtenerInstanciaComun(1));
    }


    //TEST Listar

    @Test
    void testListarInstanciasComunes() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_instancia")).thenReturn(1, 2);
        when(resultSet.getString("titulo")).thenReturn("A", "B");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now(), OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("D1", "D2");
        when(resultSet.getBoolean("est_activo")).thenReturn(true, false);
        when(resultSet.getInt("id_funcionario")).thenReturn(3, 4);
        when(resultSet.getInt("id_seguimiento")).thenReturn(10, 20);

        List<InstanciaComun> lista = instanciaComunDAO.listarInstanciasComunes();

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdInstancia());
        assertEquals("B", lista.get(1).getTitulo());
    }

    @Test
    void testListarInstanciasComunes_lanzaSQLException() throws Exception {
        when(connection.createStatement()).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.listarInstanciasComunes());
    }

    @Test
    void testListarPorSeguimiento() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_instancia")).thenReturn(5);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(2);
        when(resultSet.getInt("id_seguimiento")).thenReturn(99);

        List<InstanciaComun> lista = instanciaComunDAO.listarPorSeguimiento(99);

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdInstancia());
    }

    @Test
    void testListarPorSeguimiento_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.listarPorSeguimiento(100));
    }

    @Test
    void testListarPorEstudiante() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id_instancia")).thenReturn(7);
        when(resultSet.getString("titulo")).thenReturn("X");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(4);
        when(resultSet.getInt("id_seguimiento")).thenReturn(50);

        List<InstanciaComun> lista = instanciaComunDAO.listarPorEstudiante(123);

        assertEquals(1, lista.size());
        assertEquals(7, lista.get(0).getIdInstancia());
    }

    @Test
    void testListarPorEstudiante_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.listarPorEstudiante(50));
    }

    //TEST Actualizar

    @Test
    void testActualizarInstanciaComun_exito() throws Exception {
        InstanciaComun instancia = new InstanciaComun(1, "T", OffsetDateTime.now(), "D", true, 3, 10);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = instanciaComunDAO.actualizarInstanciaComun(instancia);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, instancia.getIdSeguimiento());
        verify(preparedStatement).setInt(2, instancia.getIdInstancia());
    }

    @Test
    void testActualizarInstanciaComun_falla() throws Exception {
        InstanciaComun instancia = new InstanciaComun(1, "T", OffsetDateTime.now(), "D", true, 3, 10);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = instanciaComunDAO.actualizarInstanciaComun(instancia);

        assertFalse(resultado);
    }

    @Test
    void testActualizarInstanciaComun_lanzaSQLException() throws Exception {
        InstanciaComun ic = new InstanciaComun(1, "T", OffsetDateTime.now(), "D", true, 4, 10);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.actualizarInstanciaComun(ic));
    }

    //TEST Eliminar

    @Test
    void testEliminarInstanciaComun_exito() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = instanciaComunDAO.eliminarInstanciaComun(88);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 88);
    }

    @Test
    void testEliminarInstanciaComun_falla() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = instanciaComunDAO.eliminarInstanciaComun(88);

        assertFalse(resultado);
    }

    @Test
    void testEliminarInstanciaComun_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("X"));
        assertThrows(SQLException.class, () -> instanciaComunDAO.eliminarInstanciaComun(1));
    }
}
