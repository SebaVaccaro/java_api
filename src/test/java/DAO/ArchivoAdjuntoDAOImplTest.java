package DAO;


import DAO.interfaz.ArchivoAdjuntoDAO;
import SINGLETON.ConexionSingleton;
import modelo.ArchivoAdjunto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArchivoAdjuntoDAOImplTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;

    private ArchivoAdjuntoDAO archivoAdjuntoDAO;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        ConexionSingleton mockedSingleton = mock(ConexionSingleton.class);
        conexionSingletonMockedStatic = mockStatic(ConexionSingleton.class);
        conexionSingletonMockedStatic.when(ConexionSingleton::getInstance).thenReturn(mockedSingleton);
        when(mockedSingleton.getConexion()).thenReturn(connection);


        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        archivoAdjuntoDAO = new ArchivoAdjuntoDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }

    @Test
    public void crearArchivoAdjuntoExito() throws SQLException{
        ArchivoAdjunto archivo = new ArchivoAdjunto(1, 1, "ruta al archivo", "academico", true);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_archivo_adjunto")).thenReturn(1);



        ArchivoAdjunto resultado = archivoAdjuntoDAO.crearArchivoAdjunto(archivo);


        verify(preparedStatement).setInt(1, archivo.getIdUsuario());
        verify(preparedStatement).setInt(2, archivo.getIdEstudiante());
        verify(preparedStatement).setString(3, archivo.getRuta());
        verify(preparedStatement).setString(4, archivo.getCategoria());
        verify(preparedStatement).setBoolean(5, archivo.isEstActivo());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdArchivoAdjunto());
    }

    @Test
    public void crearArchivoAdjuntoNoDevuelveId() throws  SQLException{
        ArchivoAdjunto archivo = new ArchivoAdjunto(1, 1, "ruta al archivo", "academico", true);

        when(resultSet.next()).thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        SQLException exception = assertThrows(SQLException.class, () -> {
            archivoAdjuntoDAO.crearArchivoAdjunto(archivo);
        });

        // Verificación del mensaje de error
        assertEquals("No se devolvió el ID del archivo adjunto generado.", exception.getMessage());


        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

    }

    @Test
    public void crearArchivoAdjuntoLanzaSQLException() throws SQLException{
        ArchivoAdjunto archivo = new ArchivoAdjunto(1, 1, "ruta al archivo", "academico", true);
        when(preparedStatement.executeQuery()).thenThrow((new SQLException("Error de base de datos")));

        SQLException exception = assertThrows((SQLException.class), () -> archivoAdjuntoDAO.crearArchivoAdjunto(archivo));

        assertEquals("Error de base de datos", exception.getMessage());


    }

    @Test
    public void obtenerArchivoAdjuntoExito() throws SQLException {
        int idArchivo = 5;

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_archivo_adjunto")).thenReturn(5);
        when(resultSet.getInt("id_usuario")).thenReturn(1);
        when(resultSet.getInt("id_estudiante")).thenReturn(2);
        when(resultSet.getString("ruta")).thenReturn("ruta del archivo");
        when(resultSet.getString("categoria")).thenReturn("academico");
        when(resultSet.getBoolean("est_activo")).thenReturn(true);

        ArchivoAdjunto resultado = archivoAdjuntoDAO.obtenerArchivoAdjunto(idArchivo);

        verify(preparedStatement).setInt(1, idArchivo);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(resultado);
        assertEquals(5, resultado.getIdArchivoAdjunto());
        assertEquals(1, resultado.getIdUsuario());
        assertEquals(2, resultado.getIdEstudiante());
        assertEquals("ruta del archivo", resultado.getRuta());
        assertEquals("academico", resultado.getCategoria());
        assertTrue(resultado.isEstActivo());
    }

    @Test
    public void obtenerArchivoAdjuntoNoExiste() throws SQLException {
        int idArchivo = 99;

        when(resultSet.next()).thenReturn(false);

        ArchivoAdjunto resultado = archivoAdjuntoDAO.obtenerArchivoAdjunto(idArchivo);

        verify(preparedStatement).setInt(1, idArchivo);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNull(resultado);
    }

    @Test
    public void obtenerArchivoAdjuntoLanzaSQLException() throws SQLException{
        int id = 5;
        when(preparedStatement.executeQuery()).thenThrow((new SQLException("Error de base de datos")));

        SQLException exception = assertThrows((SQLException.class), () -> archivoAdjuntoDAO.obtenerArchivoAdjunto(id));

        assertEquals("Error de base de datos", exception.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();


    }

    @Test
    public void testListarArchivosAdjuntosActivosExito() throws SQLException {
        List<ArchivoAdjunto> esperados = new ArrayList<>();
        esperados.add(new ArchivoAdjunto(1, 10, 100, "ruta/uno.pdf", "academico", true));
        esperados.add(new ArchivoAdjunto(2, 11, 101, "ruta/dos.pdf", "medico", true));


        // Simulamos dos registros
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_archivo_adjunto")).thenReturn(1, 2);
        when(resultSet.getInt("id_usuario")).thenReturn(10, 11);
        when(resultSet.getInt("id_estudiante")).thenReturn(100, 101);
        when(resultSet.getString("ruta")).thenReturn("ruta/uno.pdf", "ruta/dos.pdf");
        when(resultSet.getString("categoria")).thenReturn("academico", "medico");
        when(resultSet.getBoolean("est_activo")).thenReturn(true, true);

        List<ArchivoAdjunto> resultado = archivoAdjuntoDAO.listarArchivosAdjuntosActivos();

        verify(statement).executeQuery(anyString());
        verify(statement).close();

        assertEquals(esperados.size(), resultado.size());
        assertEquals(esperados.get(0).getRuta(), resultado.get(0).getRuta());
        assertEquals(esperados.get(1).getCategoria(), resultado.get(1).getCategoria());
    }

    @Test
    public void listarArchivosAdjuntosActivosLanzaSQLException() throws SQLException{
        when(statement.executeQuery(anyString())).thenThrow((new SQLException("Error de base de datos")));

        SQLException exception = assertThrows((SQLException.class), () -> archivoAdjuntoDAO.listarArchivosAdjuntosActivos());

        assertEquals("Error de base de datos", exception.getMessage());
        verify(statement).executeQuery(anyString());
        verify(statement).close();
    }

    @Test
    void testListarPorEstudianteExito() throws SQLException {
        int idEstudiante = 100;

        List<ArchivoAdjunto> esperados = new ArrayList<>();
        esperados.add(new ArchivoAdjunto(1, 10, 100, "ruta/uno.pdf", "academico", true));
        esperados.add(new ArchivoAdjunto(2, 10, 100, "ruta/dos.pdf", "medico", true));



        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id_archivo_adjunto")).thenReturn(1, 2);
        when(resultSet.getInt("id_usuario")).thenReturn(10, 10);
        when(resultSet.getInt("id_estudiante")).thenReturn(100, 100);
        when(resultSet.getString("ruta")).thenReturn("ruta/uno.pdf", "ruta/dos.pdf");
        when(resultSet.getString("categoria")).thenReturn("academico", "medico");
        when(resultSet.getBoolean("est_activo")).thenReturn(true, true);

        List<ArchivoAdjunto> resultado = archivoAdjuntoDAO.listarPorEstudiante(idEstudiante);

        verify(preparedStatement).setInt(1, idEstudiante);
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertEquals(esperados.size(), resultado.size());
        assertEquals(esperados.get(0).getCategoria(), resultado.get(0).getCategoria());
        assertEquals(esperados.get(1).getRuta(), resultado.get(1).getRuta());
    }

    @Test
    void testListarPorEstudianteLanzaSQLException() throws SQLException {
        int idEstudiante = 100;

        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error de base de datos"));
        SQLException exception = assertThrows((SQLException.class), () -> archivoAdjuntoDAO.listarPorEstudiante(idEstudiante));

        assertEquals("Error de base de datos", exception.getMessage());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarArchivoAdjuntoExito() throws SQLException {
        ArchivoAdjunto archivo = new ArchivoAdjunto(1, 10, 100, "ruta/nueva.pdf", "academico", true);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = archivoAdjuntoDAO.actualizarArchivoAdjunto(archivo);

        verify(preparedStatement).setInt(1, 10);
        verify(preparedStatement).setInt(2, 100);
        verify(preparedStatement).setString(3, "ruta/nueva.pdf");
        verify(preparedStatement).setString(4, "academico");
        verify(preparedStatement).setBoolean(5, true);
        verify(preparedStatement).setInt(6, 1);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void testActualizarArchivoAdjuntoLanzaSQLException() throws SQLException {
        ArchivoAdjunto archivo = new ArchivoAdjunto(1, 10, 100, "ruta/nueva.pdf", "academico", true);

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error al actualizar"));

        SQLException exception = assertThrows(SQLException.class,
                () -> archivoAdjuntoDAO.actualizarArchivoAdjunto(archivo));

        assertEquals("Error al actualizar", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testActualizarArchivoAdjuntoSinFilasAfectadas() throws SQLException {
        ArchivoAdjunto archivo = new ArchivoAdjunto(999, 10, 100, "ruta/inexistente.pdf", "academico", true);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = archivoAdjuntoDAO.actualizarArchivoAdjunto(archivo);

        assertFalse(resultado);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }


    @Test
    void testEliminarArchivoAdjuntoExito() throws SQLException {
        int idArchivo = 1;

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean resultado = archivoAdjuntoDAO.eliminarArchivoAdjunto(idArchivo);

        verify(preparedStatement).setInt(1, idArchivo);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();

        assertTrue(resultado);
    }

    @Test
    void testEliminarArchivoAdjuntoLanzaSQLException() throws SQLException {
        int idArchivo = 1;

        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Error al eliminar"));

        SQLException exception = assertThrows(SQLException.class,
                () -> archivoAdjuntoDAO.eliminarArchivoAdjunto(idArchivo));

        assertEquals("Error al eliminar", exception.getMessage());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
    }

    @Test
    void testEliminarArchivoAdjuntoSinFilasAfectadas() throws SQLException {
        int idArchivo = 999;

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean resultado = archivoAdjuntoDAO.eliminarArchivoAdjunto(idArchivo);


        verify(preparedStatement).setInt(1, idArchivo);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();


        assertFalse(resultado);
    }








}
