package org.amereu.java.usersjdbcmysql.repository;

import org.amereu.java.usersjdbcmysql.model.User;
import org.amereu.java.usersjdbcmysql.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz Repository para la entidad User.
 * Proporciona operaciones CRUD (Create, Read, Update, Delete) sobre la tabla `users` en MySQL.
 */
public class UserRepositoryImpl implements Repository<User> {

    /**
     * Obtiene la conexión a la base de datos usando la clase DbConnection.
     *
     * @return Connection a la base de datos
     * @throws SQLException si ocurre un error de conexión
     */
    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance();
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Lista de objetos User con todos los registros de la tabla `users`
     */
    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username, password, email FROM users";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre los resultados y crea un objeto User por cada fila
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                list.add(user); // Añade el usuario a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id Identificador del usuario
     * @return Objeto User si se encuentra, null si no existe
     */
    @Override
    public User getById(int id) {
        User user = null;
        String sql = "SELECT id, username, password, email FROM users WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id); // Asigna el parámetro de la consulta

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Inserta un nuevo usuario o actualiza uno existente.
     * Utiliza la cláusula ON DUPLICATE KEY UPDATE para evitar duplicados.
     *
     * @param user Objeto User a insertar o actualizar
     * @return true si fue una inserción nueva, false si fue actualización o error
     */
    @Override
    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password), " +
                "email = VALUES(email)";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 1) {
                // Inserción nueva: obtenemos el ID generado y lo asignamos al objeto
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return true;
            } else {
                // Actualización: no se genera ID nuevo
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // En caso de error
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id Identificador del usuario a eliminar
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id); // Asigna el parámetro de la consulta
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     Notas de estudio importantes:

Try-with-resources: Cierra automáticamente Connection, Statement y ResultSet.
Muy recomendable para evitar fugas de memoria.
PreparedStatement vs Statement:
PreparedStatement se usa cuando hay parámetros (?) para evitar SQL Injection.
Statement se usa para queries simples sin parámetros.
ON DUPLICATE KEY UPDATE: Permite actualizar un registro si ya existe una clave única (como el ID o email).
getGeneratedKeys(): Permite recuperar el ID autogenerado tras un INSERT.
Retorno boolean en save: Indica si fue inserción (true) o actualización (false).
    * */
}
