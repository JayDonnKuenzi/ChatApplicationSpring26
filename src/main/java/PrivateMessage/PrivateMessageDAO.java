/*
 * click nbfs://nbhost/systemfilesystem/templates/licenses/license-default.txt to change this license
 * click nbfs://nbhost/systemfilesystem/templates/classes/class.java to edit this template
 */
package PrivateMessage;

import models.Message;
import java.util.List;

/**
 *
 * @author lukew
 */
public interface PrivateMessageDAO {

    // add a new private message
    int addMessage(Message message);

    // delete a private message
    void deleteMessage(Message message);

    // get all private messages
    List<Message> getAllMessages();

    // get message history between two users
    List<Message> getMessageHistory(String name, int id);

    // get a message by its id
    Message getMessageFromId(int id);

}
