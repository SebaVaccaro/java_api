package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Funcionario;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioDAOImplTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Statement statement;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;
    private FuncionarioDAOImpl funcionarioDAO;

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


        funcionarioDAO = new FuncionarioDAOImpl(connection);
    }

    @AfterEach
    public void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }

    @Test
    void testInsertarFuncionarioConRolExito() throws SQLException {
        Funcionario funcionario = new Funcionario(
                1, "123", "Juan", "Perez", "jperez", "pass", "jperez@mail.com", 2, true);


        when(preparedStatement.executeUpdate()).thenReturn(1);

        funcionarioDAO.insertarFuncionario(funcionario);


        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, funcionario.getIdUsuario());
        verify(preparedStatement).setInt(2, funcionario.getIdRol());
        verify(preparedStatement).setBoolean(3, funcionario.isActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testInsertarFuncionarioSinRolExito() throws SQLException {
        Funcionario funcionario = new Funcionario(
                1, "123", "Maria", "Lopez", "mlopez", "pass", "mlopez@mail.com", 0, true);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        funcionarioDAO.insertarFuncionario(funcionario);

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, funcionario.getIdUsuario());
        verify(preparedStatement).setNull(2, Types.INTEGER);
        verify(preparedStatement).setBoolean(3, funcionario.isActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testInsertarFuncionarioLanzaSQLException() throws SQLException {
        Funcionario funcionario = new Funcionario(
                1, "123", "Ana", "Gomez", "agomez", "pass", "agomez@mail.com", 2, true);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> funcionarioDAO.insertarFuncionario(funcionario));

        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).setInt(1, funcionario.getIdUsuario());
        verify(preparedStatement).setInt(2, funcionario.getIdRol());
        verify(preparedStatement).setBoolean(3, funcionario.isActivo());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testObtenerFuncionarioExito() throws SQLException {
        int idUsuario = 1;

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_usuario")).thenReturn(1);
        when(resultSet.getString("cedula")).thenReturn("123");
        when(resultSet.getString("nombre")).thenReturn("Carlos");
        when(resultSet.getString("apellido")).thenReturn("Gomez");
        when(resultSet.getString("username")).thenReturn("cgomez");
        when(resultSet.getString("password")).thenReturn("pass");
        when(resultSet.getString("correo")).thenReturn("cgomez@mail.com");
        when(resultSet.getInt("id_rol")).thenReturn(2);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        Funcionario resultado = funcionarioDAO.obtenerFuncionario(idUsuario);

        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("cgomez", resultado.getUsername());
        assertTrue(resultado.isActivo());
    }

    @Test
    void testObtenerFuncionarioNoExiste() throws SQLException {
        int idUsuario = 99;
        when(resultSet.next()).thenReturn(false);

        Funcionario resultado = funcionarioDAO.obtenerFuncionario(idUsuario);

        assertNull(resultado);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testObtenerFuncionarioSQLException() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> funcionarioDAO.obtenerFuncionario(idUsuario));
        assertEquals("Error BD", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testListarFuncionariosExito() throws SQLException {

        List<Funcionario> esperados = new ArrayList<>();
        esperados.add(new Funcionario(1, "11111111", "Juan", "Perez", "jperez", "pass1", "jperez@mail.com", 2, true));
        esperados.add(new Funcionario(2, "22222222", "Ana", "Lopez", "alopez", "pass2", "alopez@mail.com", 3, false));


        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_usuario")).thenReturn(1, 2);
        when(resultSet.getString("cedula")).thenReturn("11111111", "22222222");
        when(resultSet.getString("nombre")).thenReturn("Juan", "Ana");
        when(resultSet.getString("apellido")).thenReturn("Perez", "Lopez");
        when(resultSet.getString("username")).thenReturn("jperez", "alopez");
        when(resultSet.getString("password")).thenReturn("pass1", "pass2");
        when(resultSet.getString("correo")).thenReturn("jperez@mail.com", "alopez@mail.com");
        when(resultSet.getInt("id_rol")).thenReturn(2, 3);
        when(resultSet.getBoolean("est_activo")).thenReturn(true, false);

        List<Funcionario> resultado = funcionarioDAO.listarFuncionarios();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(esperados.size(), resultado.size());
        assertEquals(esperados.get(0).getNombre(), resultado.get(0).getNombre());
        assertEquals(esperados.get(1).getUsername(), resultado.get(1).getUsername());
    }

    @Test
    void testListarFuncionariosSQLException() throws SQLException {

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> funcionarioDAO.listarFuncionarios());
        assertEquals("Error BD", ex.getMessage());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testActualizarFuncionarioExito() throws SQLException {
        Funcionario f = new Funcionario(1, "1111", "Ana", "Lopez", "analo", "pass", "ana@mail.com", 5, true);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = funcionarioDAO.actualizarFuncionario(f);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, f.getIdRol());
        verify(preparedStatement).setBoolean(2, f.isActivo());
        verify(preparedStatement).setInt(3, f.getIdUsuario());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarFuncionarioSinFilasAfectadas() throws SQLException {
        Funcionario f = new Funcionario(1, "1111", "Ana", "Lopez", "analo", "pass", "ana@mail.com", 5, true);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = funcionarioDAO.actualizarFuncionario(f);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarFuncionarioSQLException() throws SQLException {
        Funcionario f = new Funcionario(1, "1111", "Ana", "Lopez", "analo", "pass", "ana@mail.com", 5, true);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> funcionarioDAO.actualizarFuncionario(f));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEliminarFuncionarioExito() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = funcionarioDAO.eliminarFuncionario(idUsuario);

        assertTrue(resultado);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarFuncionarioSinFilasAfectadas() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = funcionarioDAO.eliminarFuncionario(idUsuario);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarFuncionarioSQLException() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error BD"));

        SQLException exception = assertThrows(SQLException.class, () -> funcionarioDAO.eliminarFuncionario(idUsuario));
        assertEquals("Error BD", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEstaActivoVerdadero() throws SQLException {
        int idUsuario = 1;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        boolean activo = funcionarioDAO.estaActivo(idUsuario);

        assertTrue(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoFalso() throws SQLException {
        int idUsuario = 1;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getBoolean("est_activo")).thenReturn(false);

        boolean activo = funcionarioDAO.estaActivo(idUsuario);

        assertFalse(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoNoExiste() throws SQLException {
        int idUsuario = 99;
        when(resultSet.next()).thenReturn(false);

        boolean activo = funcionarioDAO.estaActivo(idUsuario);

        assertFalse(activo);
        verify(preparedStatement).setInt(1, idUsuario);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testEstaActivoSQLException() throws SQLException {
        int idUsuario = 1;
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error BD"));

        SQLException ex = assertThrows(SQLException.class, () -> funcionarioDAO.estaActivo(idUsuario));
        assertEquals("Error BD", ex.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

}





















