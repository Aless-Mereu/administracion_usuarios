package org.amereu.java.usersjdbcmysql.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para mantener una única conexión reutilizable.
 */
public class DbConnection {

    // URL de conexión a la base de datos, incluyendo puerto y zona horaria
    private static final String URL = "jdbc:mysql://localhost:3307/java_curso?serverTimezone=UTC";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario
    private static final String PASSWORD = "";

    // Instancia única de la conexión
    private static Connection connection;

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe, crea una nueva; si ya existe y está abierta, la devuelve.
     *
     * @return Connection a la base de datos
     * @throws SQLException si ocurre un error al conectarse
     */
    public static Connection getInstance() throws SQLException {
        // Verifica si no existe la conexión o si está cerrada
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /*Comentarios clave

Singleton: Solo se crea una conexión y se reutiliza, evitando abrir y cerrar conexiones innecesariamente.
Constantes finales: URL, usuario y contraseña no cambian, por eso se declaran final.
Método closeConnection(): Es útil para cerrar la conexión cuando termina toda la aplicación,
  por ejemplo al salir del programa.
  Chequeo isClosed(): Garantiza que no intentamos usar una conexión cerrada.*/
}
