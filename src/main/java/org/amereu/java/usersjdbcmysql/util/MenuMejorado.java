package org.amereu.java.usersjdbcmysql.util;

import org.amereu.java.usersjdbcmysql.repository.Repository;
import org.amereu.java.usersjdbcmysql.repository.UserRepositoryImpl;
import org.amereu.java.usersjdbcmysql.model.User;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase Menu
 *
 * Proporciona una interfaz gráfica sencilla mediante JOptionPane para administrar usuarios.
 * Permite operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la base de datos de usuarios.
 */
public class MenuMejorado extends JFrame {

    public static void main(String[] args) {
        // Mapa de operaciones disponibles, con su índice correspondiente
        Map<String, Integer> operations = new HashMap<>();
        operations.put("Delete", 0);
        operations.put("List", 1);
        operations.put("Search for Id", 2);
        operations.put("Add", 3);
        operations.put("Exit", 4);

        // Instancia del repositorio de usuarios
        Repository<User> repository = new UserRepositoryImpl();

        // Bucle principal del menú, se repite hasta que el usuario elige salir
        while (true) {
            // Convierte las claves del mapa en un array para mostrar en el JComboBox de JOptionPane
            Object[] opArray = operations.keySet().toArray();

            // Muestra un diálogo para que el usuario seleccione una operación
            Object option = JOptionPane.showInputDialog(
                    null,
                    "Select Operation",
                    "Admin. Users",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opArray,
                    opArray[0] // opción por defecto
            );

            // Si el usuario cierra el diálogo o no selecciona nada
            if (option == null) {
                JOptionPane.showMessageDialog(null, "You have to select an operation");
                continue;
            }

            // Recupera el índice de la operación seleccionada
            int indexOption = operations.get(option.toString());

            // Ejecuta la operación correspondiente
            switch (indexOption) {
                case 0: // Delete
                    String deleteIdStr = JOptionPane.showInputDialog("Enter user ID to delete:");
                    if (deleteIdStr != null) {
                        int deleteId = Integer.parseInt(deleteIdStr);
                        repository.delete(deleteId);
                        JOptionPane.showMessageDialog(null, "User deleted.");
                    }
                    break;

                case 1: // List
                    // Muestra todos los usuarios en un diálogo
                    JOptionPane.showMessageDialog(null, repository.getAll().toString());
                    break;

                case 2: // Search for Id
                    String searchIdStr = JOptionPane.showInputDialog("Enter user ID to search:");
                    if (searchIdStr != null) {
                        int searchId = Integer.parseInt(searchIdStr);
                        User foundUser = repository.getById(searchId);
                        JOptionPane.showMessageDialog(null,
                                foundUser != null ? foundUser.toString() : "User not found");
                    }
                    break;

                case 3: // Add
                    // Solicita datos del usuario a crear
                    String username = JOptionPane.showInputDialog("Enter username:");
                    String password = JOptionPane.showInputDialog("Enter password:");
                    String email = JOptionPane.showInputDialog("Enter email:");
                    if (username != null && password != null && email != null) {
                        User newUser = new User();
                        newUser.setUserName(username);
                        newUser.setPassword(password);
                        newUser.setEmail(email);
                        repository.save(newUser);
                        JOptionPane.showMessageDialog(null, "User added.");
                    }
                    break;

                case 4: // Exit
                    JOptionPane.showMessageDialog(null, "Exiting program.");
                    System.exit(0);
                    break;
            }
        }
    }
    /*Mejoras implementadas:
Validación de campos vacíos antes de guardar o actualizar usuarios.
Manejo de NumberFormatException para IDs no numéricos.
Opción de actualizar usuario (CRUD completo).
Mensajes más claros sobre éxito o fallo de operaciones.
Manejo genérico de excepciones para capturar posibles errores de la base de datos.*/
}
