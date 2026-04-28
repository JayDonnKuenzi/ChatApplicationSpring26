/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package PrivateMessage;

import java.util.List;
import models.User;

/**
 *
 * @author lukew
 */
public interface UserDAO {
    
    List<User> getUsers();
    User getUserbyName(String name);
    User loginUser(String username, String password);
}
