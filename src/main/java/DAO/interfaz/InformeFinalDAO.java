package DAO.interfaz;

import modelo.InformeFinal;
import java.sql.SQLException;
import java.util.List;

public interface InformeFinalDAO {

    // Crear un informe final
    InformeFinal crearInformeFinal(InformeFinal informe) throws SQLException;

    // Obtener informe final por ID
    InformeFinal obtenerInformeFinal(int idInfFinal) throws SQLException;

    // Listar todos los informes finales
    List<InformeFinal> listarInformesFinales() throws SQLException;

    // Actualizar informe final
    boolean actualizarInformeFinal(InformeFinal informe) throws SQLException;

    // Eliminar informe final
    boolean eliminarInformeFinal(int idInfFinal) throws SQLException;
}
