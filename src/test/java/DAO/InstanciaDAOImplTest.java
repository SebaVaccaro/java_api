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

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonStatic;

    private InstanciaDAOImpl instanciaDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        conexionSingletonStatic = mockStatic(ConexionSingleton.class);
        ConexionSingleton mockSingleton = mock(ConexionSingleton.class);
        conexionSingletonStatic.when(ConexionSingleton::getInstance).thenReturn(mockSingleton);
        when(mockSingleton.getConexion()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        instanciaDAO = new InstanciaDAOImpl();
    }

    @AfterEach
    void tearDown() {

        conexionSingletonStatic.close();
        clearAllCaches();
    }


    //TEST Insertar Instancia
    @Test
    void testInsertarInstancia_exito() throws Exception {
        Instancia instancia = new Incidencia("Título test", OffsetDateTime.now(), "Desc test", true, 1, "Montevideo");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_instancia")).thenReturn(15);

        int id = instanciaDAO.insertarInstancia(instancia);

        assertEquals(15, id);

        verify(preparedStatement).setString(1, instancia.getTitulo());
        verify(preparedStatement).setObject(2, instancia.getFecHora());
        verify(preparedStatement).setString(3, instancia.getDescripcion());
        verify(preparedStatement).setBoolean(4, instancia.isEstActivo());
        verify(preparedStatement).setInt(5, instancia.getIdFuncionario());
    }


    @Test
    void testInsertarInstancia_falla() throws Exception {
        Instancia instancia = new Incidencia("Fallida", OffsetDateTime.now(), "Desc", true, 2, "Salto");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertThrows(SQLException.class, () -> instanciaDAO.insertarInstancia(instancia));
    }


    @Test
    void testInsertarInstancia_throwSQLException() throws Exception {
        Instancia instancia = new Incidencia("Error", OffsetDateTime.now(), "Desc", true, 3, "Paysandú");

        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error SQL"));

        assertThrows(SQLException.class, () -> instanciaDAO.insertarInstancia(instancia));
    }

    //TEST Actualizar Instancia
    @Test
    void testActualizarInstancia_exito() throws Exception {
        Instancia instancia = new Incidencia(5, "Nuevo", OffsetDateTime.now(), "Actualizado", true, 4, "Rocha");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = instanciaDAO.actualizarInstancia(instancia);

        assertTrue(resultado);
    }


    @Test
    void testActualizarInstancia_falla() throws Exception {
        Instancia instancia = new Incidencia(9, "Sin cambios", OffsetDateTime.now(), "Desc", true, 6, "Canelones");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = instanciaDAO.actualizarInstancia(instancia);

        assertFalse(resultado);
    }


    @Test
    void testActualizarInstancia_throwSQLException() throws Exception {
        Instancia instancia = new Incidencia(11, "Err", OffsetDateTime.now(), "Desc", true, 1, "Tacuarembó");

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        assertThrows(SQLException.class, () -> instanciaDAO.actualizarInstancia(instancia));
    }

    //TEST desactivarInstancia
    @Test
    void testDesactivarInstancia_exito() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = instanciaDAO.desactivarInstancia(3);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 3);
    }


    @Test
    void testDesactivarInstancia_falla() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = instanciaDAO.desactivarInstancia(99);

        assertFalse(resultado);
    }


    @Test
    void testDesactivarInstancia_throwSQLException() throws Exception {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error SQL"));

        assertThrows(SQLException.class, () -> instanciaDAO.desactivarInstancia(7));
    }
}
