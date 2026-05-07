package server_clientHandler;

import PrivateMessage.UserService;
import models.Message;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    // client socket connection
    private Socket socket;
    // input stream for receiving objects
    private ObjectInputStream in;
    // output stream for sending objects
    private ObjectOutputStream out;
    private String name;
    private String recipient_name;
    private UserService userService;
    // connected user's id
    private int userId;

    public ClientHandler(Socket socket) {
        // initialize user service
        userService = new UserService();
        try {
            this.socket = socket;

            // create output stream first
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            // create input stream after output stream
            this.in = new ObjectInputStream(socket.getInputStream());

            // read user id from client
            this.userId = (Integer) in.readObject();
            System.out.println("Connected user id: " + userId);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // read incoming message
                Message msg = (Message) in.readObject();
                // get recipient user id
                int recipient = msg.getRecipient_id();
                // send message to recipient
                sendToClient(recipient, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(int targetUserId, Message msg) {
        // search connected clients for recipient
        for (ClientHandler ch : TCPServer.allClients) {
            if (ch.userId == targetUserId) {
                try {
                    // send message to matching client
                    ch.out.writeObject(msg);
                    ch.out.flush();
                    System.out.println("Sent message to user id: " + targetUserId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("Recipient not connected: " + targetUserId);
    }
}
