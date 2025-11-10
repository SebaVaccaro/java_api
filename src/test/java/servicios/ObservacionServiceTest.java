package servicios;

import DAO.ObservacionDAOImpl;
import modelo.Observacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ObservacionServiceTest {

    @Mock
    private ObservacionDAOImpl observacionDAO;


    private ObservacionServicio service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ObservacionServicio(observacionDAO);
    }

    @Test
    public void crearObservaciónTestOk() throws SQLException {
        Observacion observacion = new Observacion();

        when(observacionDAO.crearObservacion(any())).thenReturn(observacion);
        service.crearObservacion(1, 2, "titulo", "contenido", OffsetDateTime.now());
        verify(observacionDAO).crearObservacion(any());
    }


    @Test
    public void crearObservaciónTestErrorTituloVacio() throws SQLException {
        Observacion observacion = new Observacion();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.crearObservacion(1, 2, null, "contenido", OffsetDateTime.now()));

        assertEquals("El título no puede estar vacío.", exception.getMessage());
        verifyNoInteractions(observacionDAO);
    }


}
