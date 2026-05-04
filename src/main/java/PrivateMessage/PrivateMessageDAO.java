/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateMessage;

import models.Message;
import java.util.List;
import models.User;

/**
 *
 * @author lukew
 */
public interface PrivateMessageDAO {
    
    int addMessage(Message message);
    void deleteMessage(Message message);
    List<Message> getAllMessages();
    List<Message> getMessageHistory(String name, int id);
    Message getMessageFromId(int id);
    
    
}
