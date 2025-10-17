package util;

import java.sql.SQLException;

public class CapturadoraDeErrores{

    /**
     * Traduce los errores SQL de PostgreSQL a mensajes claros para el usuario.
     */
    public static String obtenerMensajeAmigable(SQLException ex) {
        String sqlState = ex.getSQLState();
        String mensaje = ex.getMessage();

        // === ERRORES DE CLAVES ÚNICAS (SQLSTATE 23505) ===
        if ("23505".equals(sqlState)) {
            if (mensaje.contains("usuarios_cedula_key")) {
                return "Ya existe un usuario registrado con esa cédula.";
            } else if (mensaje.contains("usuarios_username_key")) {
                return "El nombre de usuario ya está en uso. Intente con otro.";
            } else if (mensaje.contains("usuarios_correo_key")) {
                return "El correo electrónico ya está registrado.";
            } else if (mensaje.contains("roles_nombre_key")) {
                return "Ya existe un rol con ese nombre.";
            } else if (mensaje.contains("grupos_nom_grupo_key")) {
                return "Ya existe un grupo con ese nombre.";
            } else if (mensaje.contains("ciudades_cod_postal_key")) {
                return "Ya existe una ciudad registrada con ese código postal.";
            } else if (mensaje.contains("carreras_codigo_key")) {
                return "Ya existe una carrera con ese código.";
            } else {
                return "Ya existe un registro con los mismos datos únicos.";
            }
        }

        // === ERRORES DE LLAVES FORÁNEAS (SQLSTATE 23503) ===
        if ("23503".equals(sqlState)) {
            if (mensaje.contains("fk_estudiantes_grupos")) {
                return "El grupo seleccionado no existe o fue eliminado.";
            } else if (mensaje.contains("fk_estudiantes_usuarios")) {
                return "El usuario asociado al estudiante no existe.";
            } else if (mensaje.contains("fk_funcionarios_roles")) {
                return "El rol asignado al funcionario no existe.";
            } else if (mensaje.contains("fk_grupos_carreras")) {
                return "La carrera asociada al grupo no existe.";
            } else if (mensaje.contains("fk_direcciones_ciudades")) {
                return "La ciudad seleccionada no existe.";
            } else if (mensaje.contains("fk_direcciones_usuarios")) {
                return "El usuario asociado a la dirección no existe.";
            } else if (mensaje.contains("fk_itr_direcciones")) {
                return "La dirección asociada al ITR no existe.";
            } else if (mensaje.contains("fk_instancias_funcionarios")) {
                return "El funcionario asignado a la instancia no existe.";
            } else if (mensaje.contains("fk_seguimientos_estudiantes")) {
                return "El estudiante del seguimiento no existe.";
            } else if (mensaje.contains("fk_seguimientos_infofinal")) {
                return "El informe final asociado no existe.";
            } else if (mensaje.contains("fk_partseguimientos_seguimientos")) {
                return "El seguimiento especificado no existe.";
            } else if (mensaje.contains("fk_partseguimientos_usuarios")) {
                return "El participante no existe.";
            } else if (mensaje.contains("fk_instcomun_instancias")) {
                return "La instancia común asociada no existe.";
            } else if (mensaje.contains("fk_instcomun_seguimientos")) {
                return "El seguimiento vinculado no existe.";
            } else if (mensaje.contains("fk_incidencias_instancias")) {
                return "La instancia de la incidencia no existe.";
            } else if (mensaje.contains("fk_incidencias_funcionarios")) {
                return "El funcionario de la incidencia no existe.";
            } else if (mensaje.contains("fk_observaciones_estudiantes")) {
                return "El estudiante de la observación no existe.";
            } else if (mensaje.contains("fk_observaciones_funcionarios")) {
                return "El funcionario de la observación no existe.";
            } else if (mensaje.contains("fk_archadjuntos_estudiantes")) {
                return "El estudiante vinculado al archivo no existe.";
            } else if (mensaje.contains("fk_archadjuntos_usuarios")) {
                return "El usuario vinculado al archivo no existe.";
            } else if (mensaje.contains("fk_notificaciones_instancias")) {
                return "La instancia de la notificación no existe.";
            } else if (mensaje.contains("fk_recibe_notificaciones")) {
                return "La notificación indicada no existe.";
            } else if (mensaje.contains("fk_recibe_usuarios")) {
                return "El usuario receptor de la notificación no existe.";
            } else {
                return "No se puede realizar la acción: existen datos relacionados.";
            }
        }

        // === ERRORES DE CHECK CONSTRAINTS (SQLSTATE 23514) ===
        if ("23514".equals(sqlState)) {
            if (mensaje.contains("ci_uy_check")) {
                return "La cédula debe tener 7 u 8 dígitos.";
            } else if (mensaje.contains("correo_uy_check")) {
                return "El formato del correo electrónico no es válido.";
            } else {
                return "Los datos ingresados no cumplen las reglas del sistema.";
            }
        }

        // === ERRORES DE CAMPOS NULOS (SQLSTATE 23502) ===
        if ("23502".equals(sqlState)) {
            return "Faltan datos obligatorios. Verifique los campos requeridos.";
        }

        // === ERRORES DE CONEXIÓN (SQLSTATE 08003) ===
        if ("08003".equals(sqlState)) {
            return "Error de conexión con la base de datos. Intente más tarde.";
        }

        // === CASO GENÉRICO ===
        return "Error inesperado: " + mensaje;
    }
}
