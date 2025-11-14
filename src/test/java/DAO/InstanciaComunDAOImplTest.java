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

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private InstanciaComunDAOImpl dao;

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

        dao = new InstanciaComunDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }


    @Test
    void insertarInstanciaComun_exito() throws Exception {
        InstanciaComun ic = new InstanciaComun(1,"T",OffsetDateTime.now(),"D",true,3,5);

        dao.insertarInstanciaComun(ic);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 5);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void insertarInstanciaComun_lanzaSQLException() throws Exception {
        InstanciaComun ic = new InstanciaComun(1,"T",OffsetDateTime.now(),"D",true,3,5);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.insertarInstanciaComun(ic));
        assertEquals("DB error", ex.getMessage());
    }


    @Test
    void obtenerInstanciaComun_exito() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_instancia")).thenReturn(1);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(3);
        when(resultSet.getInt("id_seguimiento")).thenReturn(8);

        InstanciaComun ic = dao.obtenerInstanciaComun(1);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(ic);
        assertEquals(1, ic.getIdInstancia());
        assertEquals("Titulo", ic.getTitulo());
        assertEquals(8, ic.getIdSeguimiento());
    }

    @Test
    void obtenerInstanciaComun_noEncontrada() throws Exception {
        when(resultSet.next()).thenReturn(false);

        InstanciaComun ic = dao.obtenerInstanciaComun(999);

        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNull(ic);
    }

    @Test
    void obtenerInstanciaComun_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.obtenerInstanciaComun(1));
        assertEquals("Error", ex.getMessage());
    }


    @Test
    void listarInstanciasComunes_exito() throws Exception {
        when(resultSet.next()).thenReturn(true,true,false);
        when(resultSet.getInt("id_instancia")).thenReturn(1,2);
        when(resultSet.getString("titulo")).thenReturn("A","B");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now(),OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("D1","D2");
        when(resultSet.getBoolean("est_activo")).thenReturn(true,false);
        when(resultSet.getInt("id_funcionario")).thenReturn(3,4);
        when(resultSet.getInt("id_seguimiento")).thenReturn(10,20);

        List<InstanciaComun> lista = dao.listarInstanciasComunes();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(2, lista.size());
        assertEquals("A", lista.get(0).getTitulo());
        assertEquals("B", lista.get(1).getTitulo());
    }

    @Test
    void listarInstanciasComunes_lanzaSQLException() throws Exception {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarInstanciasComunes());
        assertEquals("Error", ex.getMessage());
    }


    @Test
    void listarPorSeguimiento_exito() throws Exception {
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getInt("id_instancia")).thenReturn(5);
        when(resultSet.getString("titulo")).thenReturn("Titulo");
        when(resultSet.getObject("fec_hora", OffsetDateTime.class)).thenReturn(OffsetDateTime.now());
        when(resultSet.getString("descripcion")).thenReturn("Desc");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);
        when(resultSet.getInt("id_funcionario")).thenReturn(2);
        when(resultSet.getInt("id_seguimiento")).thenReturn(99);

        List<InstanciaComun> lista = dao.listarPorSeguimiento(99);

        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getIdInstancia());
    }

    @Test
    void listarPorSeguimiento_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.listarPorSeguimiento(100));
        assertEquals("Error", ex.getMessage());
    }


    @Test
    void actualizarInstanciaComun_exito() throws Exception {
        InstanciaComun ic = new InstanciaComun(1,"T",OffsetDateTime.now(),"D",true,3,10);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.actualizarInstanciaComun(ic);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void actualizarInstanciaComun_sinFilasAfectadas() throws Exception {
        InstanciaComun ic = new InstanciaComun(1,"T",OffsetDateTime.now(),"D",true,3,10);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.actualizarInstanciaComun(ic);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void actualizarInstanciaComun_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.actualizarInstanciaComun(
                new InstanciaComun(1,"T",OffsetDateTime.now(),"D",true,3,10)));
        assertEquals("Error", ex.getMessage());
    }


    @Test
    void eliminarInstanciaComun_exito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean ok = dao.eliminarInstanciaComun(88);

        verify(preparedStatement).setInt(1,88);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(ok);
    }

    @Test
    void eliminarInstanciaComun_sinFilasAfectadas() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean ok = dao.eliminarInstanciaComun(88);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(ok);
    }

    @Test
    void eliminarInstanciaComun_lanzaSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error"));

        SQLException ex = assertThrows(SQLException.class, () -> dao.eliminarInstanciaComun(1));
        assertEquals("Error", ex.getMessage());
    }
}
