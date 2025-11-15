package utils;

import SINGLETON.SesionSingleton;
import modelo.Usuario;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidarUsuarioTest {

    @Mock
    private SesionSingleton sesionMock;

    @Mock
    private Usuario usuarioMock;

    private MockedStatic<SesionSingleton> sesionStaticMock;
    private ValidarUsuario validar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sesionStaticMock = mockStatic(SesionSingleton.class);
        sesionStaticMock.when(SesionSingleton::getInstance).thenReturn(sesionMock);

        validar = new ValidarUsuario();
    }

    @AfterEach
    void tearDown() {
        sesionStaticMock.close();
    }


    @Test
    void esAdministrador_true() {
        when(sesionMock.getRolActual()).thenReturn("Administrador");
        assertTrue(validar.esAdministrador());
    }

    @Test
    void esAdministrador_false() {
        when(sesionMock.getRolActual()).thenReturn("Tutor");
        assertFalse(validar.esAdministrador());
    }

    @Test
    void esPsicopedagogo_true() {
        when(sesionMock.getRolActual()).thenReturn("PSICOPEDAGOGO");
        assertTrue(validar.esPsicopedagogo());
    }

    @Test
    void esPsicopedagogo_false() {
        when(sesionMock.getRolActual()).thenReturn("ESTUDIANTE");
        assertFalse(validar.esPsicopedagogo());
    }

    @Test
    void esEstudiante_true() {
        when(sesionMock.getRolActual()).thenReturn("eStUdIaNtE");
        assertTrue(validar.esEstudiante());
    }

    @Test
    void esTutor_true() {
        when(sesionMock.getRolActual()).thenReturn("tutor");
        assertTrue(validar.esTutor());
    }

    @Test
    void esTutor_false() {
        when(sesionMock.getRolActual()).thenReturn(null);
        assertFalse(validar.esTutor());
    }

    @Test
    void esAdminOPsico_true_Admin() {
        when(sesionMock.getRolActual()).thenReturn("ADMINISTRADOR");
        assertTrue(validar.esAdminOPsico());
    }

    @Test
    void esAdminOPsico_true_Psico() {
        when(sesionMock.getRolActual()).thenReturn("PSICOPEDAGOGO");
        assertTrue(validar.esAdminOPsico());
    }

    @Test
    void esAdminOPsico_false() {
        when(sesionMock.getRolActual()).thenReturn("TUTOR");
        assertFalse(validar.esAdminOPsico());
    }


    @Test
    void tienePermisoAdminPsicoOPropietario_esAdmin() {
        when(sesionMock.getRolActual()).thenReturn("Administrador");
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock); // <- importante
        when(usuarioMock.getIdUsuario()).thenReturn(999); // id cualquiera
        assertTrue(validar.tienePermisoAdminPsicoOPropietario(10));
    }

    @Test
    void tienePermisoAdminPsicoOPropietario_esPsico() {
        when(sesionMock.getRolActual()).thenReturn("PSICOPEDAGOGO");
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock); // <- importante
        when(usuarioMock.getIdUsuario()).thenReturn(999);
        assertTrue(validar.tienePermisoAdminPsicoOPropietario(10));
    }


    @Test
    void tienePermisoAdminPsicoOPropietario_esPropietario() {
        when(sesionMock.getRolActual()).thenReturn("ESTUDIANTE");
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock);
        when(usuarioMock.getIdUsuario()).thenReturn(7);

        assertTrue(validar.tienePermisoAdminPsicoOPropietario(7));
    }

    @Test
    void tienePermisoAdminPsicoOPropietario_sinSesion() {
        when(sesionMock.getUsuarioActual()).thenReturn(null);
        assertFalse(validar.tienePermisoAdminPsicoOPropietario(5));
    }

    @Test
    void tienePermisoAdminPsicoOPropietario_noTienePermiso() {
        when(sesionMock.getRolActual()).thenReturn("TUTOR");
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock);
        when(usuarioMock.getIdUsuario()).thenReturn(3);

        assertFalse(validar.tienePermisoAdminPsicoOPropietario(99));
    }

    @Test
    void tienePermisoAdminPsico_esAdmin() {
        when(sesionMock.getRolActual()).thenReturn("ADMINISTRADOR");
        assertTrue(validar.tienePermisoAdminPsico(88));
    }

    @Test
    void tienePermisoAdminPsico_esPsico() {
        when(sesionMock.getRolActual()).thenReturn("PSICOPEDAGOGO");
        assertTrue(validar.tienePermisoAdminPsico(1));
    }

    @Test
    void tienePermisoAdminPsico_false() {
        when(sesionMock.getRolActual()).thenReturn("TUTOR");
        assertFalse(validar.tienePermisoAdminPsico(1));
    }


    @Test
    void esPropietario_true() {
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock);
        when(usuarioMock.getIdUsuario()).thenReturn(20);

        assertTrue(validar.esPropietario(20));
    }

    @Test
    void esPropietario_false() {
        when(sesionMock.getUsuarioActual()).thenReturn(usuarioMock);
        when(usuarioMock.getIdUsuario()).thenReturn(1);

        assertFalse(validar.esPropietario(99));
    }

    @Test
    void esPropietario_noSesion() {
        when(sesionMock.getUsuarioActual()).thenReturn(null);
        assertFalse(validar.esPropietario(10));
    }

    @Test
    void haySesionActiva_true() {
        when(sesionMock.haySesionActiva()).thenReturn(true);
        assertTrue(validar.haySesionActiva());
    }

    @Test
    void haySesionActiva_false() {
        when(sesionMock.haySesionActiva()).thenReturn(false);
        assertFalse(validar.haySesionActiva());
    }
}
