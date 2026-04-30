/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author lukew
 */
public class Message {
    
    private int message_id;
    private int sender_id;
    private int recipient_id;
    private String message;
    private Date timestamp;

    public Message() {
    }

    public Message(int sender_id, int recipient_id, String message) {
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.message = message;
    }
    

    public Message(int message_id, int sender_id, int recipient_id, String message, Date timestamp) {
        this.message_id = message_id;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(int recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
