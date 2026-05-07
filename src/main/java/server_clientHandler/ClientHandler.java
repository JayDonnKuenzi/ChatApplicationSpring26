package server_clientHandler;

import PrivateMessage.UserService;
import models.Message;
import java.io.*;
import java.net.Socket;
import models.User;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String name;
    private String recipient_name;
    private UserService userService;
    private int userId;

    public ClientHandler(Socket socket) {
        userService = new UserService();
        try {
            this.socket = socket;

            // IMPORTANT: output stream first
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            this.in = new ObjectInputStream(socket.getInputStream());

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
                Message msg = (Message) in.readObject();
                int recipient = msg.getRecipient_id();
                sendToClient(recipient, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(int targetUserId, Message msg) {
        for (ClientHandler ch : TCPServer.allClients) {
            if (ch.userId == targetUserId) {
                try {
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
