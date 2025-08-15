package org.amereu.java.usersjdbcmysql.model;

/**
 * Clase que representa un usuario del sistema.
 * Contiene los datos básicos de un usuario: id, nombre de usuario, contraseña y correo electrónico.
 */
public class User {

    /** Identificador único del usuario en la base de datos */
    private int id;

    /** Nombre de usuario */
    private String userName;

    /** Contraseña del usuario */
    private String password;

    /** Correo electrónico del usuario */
    private String email;

    /**
     * Constructor por defecto.
     * Permite crear un usuario vacío y luego establecer sus propiedades mediante los setters.
     */
    public User() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param id        Identificador del usuario
     * @param userName  Nombre de usuario
     * @param password  Contraseña
     * @param email     Correo electrónico
     */
    public User(int id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    // ---------- GETTERS Y SETTERS ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Representación en forma de cadena del objeto User.
     * Útil para depuración y logging.
     *
     * @return String con la información del usuario
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /*
    *Notas de estudio:

La clase User es un modelo simple o POJO (Plain Old Java Object), usado para mapear los registros de la tabla users.
Los constructores permiten crear objetos de forma vacía o inicializándolos con todos los campos.
Los getters y setters permiten acceder y modificar los atributos de manera controlada.
El método toString() es útil para imprimir el objeto en consola o logs durante pruebas. */
}
