package DAO;

import DAO.interfaz.CarreraDAO;
import SINGLETON.ConexionSingleton;

import modelo.Carrera;
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


public class CarreraDAOImplTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConexionSingleton> conexionSingletonMockedStatic;

    private CarreraDAO carreraDAO;

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

        carreraDAO = new CarreraDAOImpl();
    }

    @AfterEach
    void tearDown() {
        conexionSingletonMockedStatic.close();
        clearAllCaches();
    }

    @Test
    public void crearCarreraExito() throws SQLException{
        Carrera carrera = new Carrera("codigo", "nombre", "plan");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id_carrera")).thenReturn(2);

        Carrera resultado = carreraDAO.crearCarrera(carrera);

        verify(preparedStatement).setString(1, carrera.getCodigo());
        verify(preparedStatement).setString(2, carrera.getNombre());
        verify(preparedStatement).setString(3, carrera.getPlan());
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).close();

        assertNotNull(resultado);
        assertEquals(2, resultado.getIdCarrera());


    }





}
