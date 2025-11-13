package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Estudiante;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstudianteDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Statement statement;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;
    private EstudianteDAOImpl estudianteDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);


        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        estudianteDAO = new EstudianteDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }


    @Test
    public void testInsertarEstudianteExito() throws SQLException {
        Estudiante est = new Estudiante(1, "12345678", "Juan", "Perez", "juanp", "1234", "juan@correo.com", 10, true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        estudianteDAO.insertarEstudiante(est);

        verify(preparedStatement).setInt(1, est.getIdUsuario());
        verify(preparedStatement).setInt(2, est.getIdGrupo());
        verify(preparedStatement).setBoolean(3, est.isActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    public void testInsertarEstudianteSQLException() throws SQLException {
        Estudiante est = new Estudiante(1, "12345678", "Juan", "Perez", "juanp", "1234", "juan@correo.com", 10, true);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> estudianteDAO.insertarEstudiante(est));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    public void testObtenerEstudianteExito() throws SQLException {
        int idUsuario = 1;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_usuario")).thenReturn(1);
        when(resultSet.getString("cedula")).thenReturn("12345678");
        when(resultSet.getString("nombre")).thenReturn("Juan");
        when(resultSet.getString("apellido")).thenReturn("Perez");
        when(resultSet.getString("username")).thenReturn("juanp");
        when(resultSet.getString("password")).thenReturn("1234");
        when(resultSet.getString("correo")).thenReturn("juan@correo.com");
        when(resultSet.getInt("id_grupo")).thenReturn(10);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Estudiante est = estudianteDAO.obtenerEstudiante(idUsuario);

        assertNotNull(est);
        assertEquals("Juan", est.getNombre());
        assertTrue(est.isActivo());
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    public void testObtenerEstudianteNoExiste() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Estudiante est = estudianteDAO.obtenerEstudiante(99);

        assertNull(est);
        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    public void testObtenerEstudianteSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> estudianteDAO.obtenerEstudiante(1));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close(); 
    }


    @Test
    public void testListarEstudiantesExito() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_usuario")).thenReturn(1, 2);
        when(resultSet.getString("nombre")).thenReturn("Juan", "Ana");
        when(resultSet.getString("apellido")).thenReturn("Perez", "Gomez");
        when(resultSet.getString("username")).thenReturn("juanp", "anag");
        when(resultSet.getString("password")).thenReturn("1234", "abcd");
        when(resultSet.getString("correo")).thenReturn("juan@correo.com", "ana@correo.com");
        when(resultSet.getInt("id_grupo")).thenReturn(10, 20);
        when(resultSet.getBoolean("est_activo")).thenReturn(true, true);

        List<Estudiante> lista = estudianteDAO.listarEstudiantes();

        assertEquals(2, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
        assertEquals("Ana", lista.get(1).getNombre());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    public void testListarEstudiantesSQLException() throws SQLException {
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> estudianteDAO.listarEstudiantes());
        assertEquals("Error BD", ex.getMessage());

        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }


    @Test
    public void testActualizarEstudianteExito() throws SQLException {
        Estudiante est = new Estudiante(1, "12345678", "Juan", "Perez", "juanp", "1234", "juan@correo.com", 10, true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = estudianteDAO.actualizarEstudiante(est);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, est.getIdGrupo());
        verify(preparedStatement).setBoolean(2, est.isActivo());
        verify(preparedStatement).setInt(3, est.getIdUsuario());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    public void testActualizarEstudianteNoAfectaFilas() throws SQLException {
        Estudiante est = new Estudiante(1, "12345678", "Juan", "Perez", "juanp", "1234", "juan@correo.com", 10, true);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = estudianteDAO.actualizarEstudiante(est);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    public void testActualizarEstudianteSQLException() throws SQLException {
        Estudiante est = new Estudiante(1, "12345678", "Juan", "Perez", "juanp", "1234", "juan@correo.com", 10, true);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> estudianteDAO.actualizarEstudiante(est));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEliminarEstudianteExito() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = estudianteDAO.eliminarEstudiante(idUsuario);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarEstudianteNoAfectaFilas() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = estudianteDAO.eliminarEstudiante(idUsuario);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarEstudianteSQLException() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> estudianteDAO.eliminarEstudiante(idUsuario));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEstaActivoVerdadero() throws SQLException {
        int idUsuario = 1;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        boolean activo = estudianteDAO.estaActivo(idUsuario);

        assertTrue(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoFalso() throws SQLException {
        int idUsuario = 2;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("est_activo")).thenReturn(false);

        boolean activo = estudianteDAO.estaActivo(idUsuario);

        assertFalse(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoNoExiste() throws SQLException {
        int idUsuario = 99;
        when(resultSet.next()).thenReturn(false);

        boolean activo = estudianteDAO.estaActivo(idUsuario);

        assertFalse(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoSQLException() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> estudianteDAO.estaActivo(idUsuario));
        assertEquals("Error BD", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }



}
