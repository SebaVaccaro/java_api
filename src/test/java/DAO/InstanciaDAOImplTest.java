package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;
import modelo.Instancia;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstanciaDAOImplTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMocked;
    private InstanciaDAOImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton singleton = mock(ConexionSingleton.class);
        when(singleton.getConexion()).thenReturn(connection);

        conexionSingletonMocked = mockStatic(ConexionSingleton.class);
        conexionSingletonMocked.when(ConexionSingleton::getInstance).thenReturn(singleton);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        dao = new InstanciaDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMocked.close();
        clearAllCaches();
    }


    @Test
    void insertarInstancia_exito() throws Exception {
        Instancia instancia = new Incidencia("Título test", OffsetDateTime.now(), "Desc test", true, 1, "Montevideo");

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_instancia")).thenReturn(15);

        int id = dao.insertarInstancia(instancia);

        verify(preparedStatement).setString(1, instancia.getTitulo());
        verify(preparedStatement).setObject(2, instancia.getFecHora());
        verify(preparedStatement).setString(3, instancia.getDescripcion());
        verify(preparedStatement).setBoolean(4, instancia.isEstActivo());
        verify(preparedStatement).setInt(5, instancia.getIdFuncionario());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(15, id);
    }

    @Test
    void insertarInstancia_falla() throws Exception {
        Instancia instancia = new Incidencia("Fallida", OffsetDateTime.now(), "Desc", true, 2, "Salto");

        when(resultSet.next()).thenReturn(false);

        SQLException ex = assertThrows(SQLException.class,
                () -> dao.insertarInstancia(instancia));

        assertEquals("No se pudo insertar la instancia", ex.getMessage());

        verify(preparedStatement).setString(1, instancia.getTitulo());
        verify(preparedStatement).setObject(2, instancia.getFecHora());
        verify(preparedStatement).setString(3, instancia.getDescripcion());
        verify(preparedStatement).setBoolean(4, instancia.isEstActivo());
        verify(preparedStatement).setInt(5, instancia.getIdFuncionario());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void insertarInstancia_throwSQLException() throws Exception {
        Instancia instancia = new Incidencia("Error", OffsetDateTime.now(), "Desc", true, 3, "Paysandú");

        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class,
                () -> dao.insertarInstancia(instancia));

        assertEquals("Error SQL", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void actualizarInstancia_exito() throws Exception {
        Instancia instancia = new Incidencia(5, "Nuevo", OffsetDateTime.now(), "Actualizado", true, 4, "Rocha");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = dao.actualizarInstancia(instancia);

        verify(preparedStatement).setString(1, instancia.getTitulo());
        verify(preparedStatement).setObject(2, instancia.getFecHora());
        verify(preparedStatement).setString(3, instancia.getDescripcion());
        verify(preparedStatement).setBoolean(4, instancia.isEstActivo());
        verify(preparedStatement).setInt(5, instancia.getIdFuncionario());
        verify(preparedStatement).setInt(6, instancia.getIdInstancia());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void actualizarInstancia_falla() throws Exception {
        Instancia instancia = new Incidencia(9, "Sin cambios", OffsetDateTime.now(), "Desc", true, 6, "Canelones");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = dao.actualizarInstancia(instancia);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(resultado);
    }

    @Test
    void actualizarInstancia_throwSQLException() throws Exception {
        Instancia instancia = new Incidencia(11, "Err", OffsetDateTime.now(), "Desc", true, 1, "Tacuarembó");

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class,
                () -> dao.actualizarInstancia(instancia));

        assertEquals("Error SQL", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void desactivarInstancia_exito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = dao.desactivarInstancia(3);

        verify(preparedStatement).setInt(1, 3);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void desactivarInstancia_falla() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = dao.desactivarInstancia(99);

        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertFalse(resultado);
    }

    @Test
    void desactivarInstancia_throwSQLException() throws Exception {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        SQLException ex = assertThrows(SQLException.class,
                () -> dao.desactivarInstancia(7));

        assertEquals("Error SQL", ex.getMessage());
    }
}
