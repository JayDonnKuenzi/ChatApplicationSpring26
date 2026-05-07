/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
    
    void addUser(User user) throws SQLIntegrityConstraintViolationException;
    List<User> getUsers();
    User getUserbyName(String name);
    User loginUser(String username, String password);
    void changeUsername(int id, String newUsername) throws SQLIntegrityConstraintViolationException;
    void changePassword(int id, String newPassword);
}
