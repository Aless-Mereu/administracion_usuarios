package org.amereu.java.usersjdbcmysql.repository;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD (Create, Read, Update, Delete) sobre cualquier entidad.
 *
 * @param <T> Tipo de entidad que manejará el repositorio (por ejemplo, User)
 */
public interface Repository<T> {

    /**
     * Obtiene todos los registros de la entidad en la base de datos.
     *
     * @return Lista con todos los objetos de tipo T
     */
    List<T> getAll();

    /**
     * Obtiene un registro específico por su ID.
     *
     * @param id Identificador del registro
     * @return Objeto de tipo T si existe, null si no se encuentra
     */
    T getById(int id);

    /**
     * Inserta un nuevo registro o actualiza uno existente.
     *
     * @param t Objeto de tipo T a guardar
     * @return true si fue una inserción nueva, false si fue una actualización o ocurrió un error
     */
    boolean save(T t);

    /**
     * Elimina un registro por su ID.
     *
     * @param id Identificador del registro a eliminar
     */
    void delete(int id);

    /*Notas de estudio:

Es genérica, lo que permite que cualquier tipo de entidad (User, Producto, etc.) pueda implementar este
repositorio sin repetir código.
Define la firma de métodos CRUD, dejando la implementación concreta a clases como UserRepositoryImpl.
Sirve como contrato: cualquier repositorio que implemente esta interfaz debe ofrecer estas operaciones básicas.*/
}
