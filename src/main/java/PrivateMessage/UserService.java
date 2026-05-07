/*
 * click nbfs://nbhost/systemfilesystem/templates/licenses/license-default.txt to change this license
 * click nbfs://nbhost/systemfilesystem/templates/classes/class.java to edit this template
 */
package PrivateMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import models.User;
import utils.Config;

/**
 *
 * @author lukew
 */
public class UserService implements UserDAO {

    @Override
    public List<User> getUsers() {
        // get all users from database
        List<User> users = new ArrayList<>();
        try {
            String sql = "select * from users";

            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
    public User getUserbyName(String name) {
        // find a user by name
        for (User user : getUsers()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            } else {
                continue;
            }
        }
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
        // authenticate a user
        User user = null;
        try {
            String sql = "select * from users WHERE username = ? AND password = ?";

            PreparedStatement ps = Config.getConnection().prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                username = rs.getString("username");
                password = rs.getString("password");

                user = new User(id, name, username, password);
                return user;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("username or password is incorrect.");
        }
        return null;
    }

    @Override
    public void addUser(User user) throws SQLIntegrityConstraintViolationException {
        // add a new user
        String sql = "INSERT INTO users (id, name, username, password) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = Config.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, user.getUser_id());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();

            System.out.println("user added successfully!");
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw ex;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void changeUsername(int id, String newUsername) throws SQLIntegrityConstraintViolationException {
        // update a user's username
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = Config.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, newUsername);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            System.out.println("username changed successfully!");
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw ex;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void changePassword(int id, String newPassword) {
        // update a user's password
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = Config.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            System.out.println("password changed successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
