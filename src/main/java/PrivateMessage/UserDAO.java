/*
 * click nbfs://nbhost/systemfilesystem/templates/licenses/license-default.txt to change this license
 * click nbfs://nbhost/systemfilesystem/templates/classes/interface.java to edit this template
 */
package PrivateMessage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import models.User;

/**
 *
 * @author lukew
 */
public interface UserDAO {

    // add a new user
    void addUser(User user) throws SQLIntegrityConstraintViolationException;

    // get all users
    List<User> getUsers();

    // find a user by name
    User getUserbyName(String name);

    // authenticate a user
    User loginUser(String username, String password);

    // change a user's username
    void changeUsername(int id, String newUsername) throws SQLIntegrityConstraintViolationException;

    // change a user's password
    void changePassword(int id, String newPassword);
}
