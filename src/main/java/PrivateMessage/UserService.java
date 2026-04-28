/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.User;
import utils.Config;

/**
 *
 * @author lukew
 */
public class UserService implements UserDAO{

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "select * from users";
            
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                User user = new User();
                user.setUser_id(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
                users.add(user);
            }
        } catch (SQLException ex) {
            System.getLogger(UserService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return users;
    }

    @Override
    public User getUserbyName(String name){
        for (User user: getUsers()) {
            if (user.getName().equalsIgnoreCase(name)){
                return user;
            } else{
                continue;
            }
        }
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
        User user = null;
        try {
            String sql = "select * from users WHERE username = ? AND password = ?";
            
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                username = rs.getString("username");
                password = rs.getString("password");
                
                user = new User(id, name, username, password);
                return user;
            }
            
        } catch (SQLException ex) {
            System.out.println("Username or password is incorrect.");
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        
    }
    
}
