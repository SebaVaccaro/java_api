package DAO;

import SINGLETON.ConexionSingleton;
import modelo.InformeFinal;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InformeFinalDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Statement statement;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;
    private InformeFinalDAOImpl informeFinalDAO;

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

        informeFinalDAO = new InformeFinalDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }


    @Test
    void testCrearInformeFinalExitoso() throws SQLException {
        InformeFinal informe = new InformeFinal("Contenido", 8, LocalDate.now());

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_inf_final")).thenReturn(1);

        InformeFinal resultado = informeFinalDAO.crearInformeFinal(informe);

        assertEquals(1, resultado.getIdInfFinal());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testCrearInformeFinalSinIdDevueltoLanzaSQLException() throws SQLException {
        InformeFinal informe = new InformeFinal("Cont", 5, LocalDate.now());
        when(resultSet.next()).thenReturn(false);

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.crearInformeFinal(informe));

        assertEquals("No se devolvió el ID generado para el informe final.", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testCrearInformeFinalSQLException() throws SQLException {
        InformeFinal informe = new InformeFinal("Error", 1, LocalDate.now());
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.crearInformeFinal(informe));
        assertEquals("Error BD", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testObtenerInformeFinalEncontrado() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_inf_final")).thenReturn(1);
        when(resultSet.getString("contenido")).thenReturn("Texto");
        when(resultSet.getInt("valoracion")).thenReturn(9);
        when(resultSet.getDate("fec_creacion")).thenReturn(Date.valueOf(LocalDate.now()));

        InformeFinal informe = informeFinalDAO.obtenerInformeFinal(1);

        assertNotNull(informe);
        assertEquals(1, informe.getIdInfFinal());
        verify(preparedStatement).setInt(1, 1);
    }

    @Test
    void testObtenerInformeFinalNoEncontrado() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        InformeFinal informe = informeFinalDAO.obtenerInformeFinal(99);

        assertNull(informe);
        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testObtenerInformeFinalSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error al obtener informe"));

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.obtenerInformeFinal(1));
        assertEquals("Error al obtener informe", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }


    @Test
    void testListarInformesFinalesExitoso() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_inf_final")).thenReturn(1, 2);
        when(resultSet.getString("contenido")).thenReturn("Uno", "Dos");
        when(resultSet.getInt("valoracion")).thenReturn(8, 9);
        when(resultSet.getDate("fec_creacion")).thenReturn(Date.valueOf(LocalDate.now()));

        List<InformeFinal> informes = informeFinalDAO.listarInformesFinales();

        assertEquals(2, informes.size());
        assertEquals("Uno", informes.get(0).getContenido());
        assertEquals("Dos", informes.get(1).getContenido());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarInformesFinalesSQLException() throws SQLException {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error al listar"));

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.listarInformesFinales());
        assertEquals("Error al listar", ex.getMessage());
        verify(statement).executeQuery(anyString());
        verify(statement).close();

    }


    @Test
    void testActualizarInformeFinalExitoso() throws SQLException {
        InformeFinal informe = new InformeFinal(1, "Nuevo contenido", 10, LocalDate.now());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = informeFinalDAO.actualizarInformeFinal(informe);

        assertTrue(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarInformeFinalNoActualiza() throws SQLException {
        InformeFinal informe = new InformeFinal(1, "Nada", 5, LocalDate.now());
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = informeFinalDAO.actualizarInformeFinal(informe);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarInformeFinalSQLException() throws SQLException {
        InformeFinal informe = new InformeFinal(1, "Err", 1, LocalDate.now());
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.actualizarInformeFinal(informe));
        assertEquals("Error BD", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEliminarInformeFinalExitoso() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = informeFinalDAO.eliminarInformeFinal(10);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarInformeFinalNoElimina() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = informeFinalDAO.eliminarInformeFinal(10);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarInformeFinalSQLException() throws SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error al eliminar"));

        SQLException ex = assertThrows(SQLException.class, () -> informeFinalDAO.eliminarInformeFinal(5));
        assertEquals("Error al eliminar", ex.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }
}

