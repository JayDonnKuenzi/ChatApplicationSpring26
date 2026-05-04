/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateMessage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Message;
import models.User;
import utils.Config;

/**
 *
 * @author lukew
 */
public class PrivateMessage implements PrivateMessageDAO {

    @Override
    public int addMessage(Message message) {
        String sql = "INSERT INTO private_messages (sender_id, recipient_id, message) VALUES (?, ?, ?)";

        try (
                PreparedStatement ps = Config.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, message.getSender_id());
            ps.setInt(2, message.getRecipient_id());
            ps.setString(3, message.getMessage());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    @Override
    public void deleteMessage(Message message) {
        String sql = "DELETE FROM private_messages WHERE id = ?";

        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);

            ps.setInt(1, message.getMessage_id());

            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try {

            String sql = "select * from private_messages";

            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message();

                message.setMessage_id(rs.getInt("id"));
                message.setSender_id(rs.getInt("sender_id"));
                message.setRecipient_id(rs.getInt("recipient_id"));
                message.setMessage(rs.getString("message"));
                message.setTimestamp(rs.getDate("timestamp"));

                messages.add(message);
            }

        } catch (SQLException ex) {
            System.getLogger(PrivateMessage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return messages;
    }

    @Override
    public List<Message> getMessageHistory(String name, int id) {
        UserService userservice = new UserService();
        List<Message> messages = new ArrayList<>();

        String sql = "select * from private_messages WHERE sender_id = ? AND recipient_id = ? OR sender_id = ? AND recipient_id = ?";

        User user = userservice.getUserbyName(name);
        int user_id = user.getUser_id();

        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);

            ps.setInt(1, user_id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, user_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message();

                message.setMessage_id(rs.getInt("id"));
                message.setSender_id(rs.getInt("sender_id"));
                message.setRecipient_id(rs.getInt("recipient_id"));
                message.setMessage(rs.getString("message"));
                message.setTimestamp(rs.getDate("timestamp"));

                messages.add(message);
            }

        } catch (SQLException ex) {
            System.getLogger(PrivateMessage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return messages;
    }

    @Override
    public Message getMessageFromId(int id) {
        List<Message> messages = getAllMessages();

        for (Message message : messages) {
            if (message.getMessage_id() == id) {
                return message;
            } else {
                continue;
            }
        }
        return null;
    }

}
