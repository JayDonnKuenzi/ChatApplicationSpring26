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

    public ClientHandler(Socket socket) {
        userService = new UserService();
        try {
            this.socket = socket;

            // IMPORTANT: output stream first
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            this.in = new ObjectInputStream(socket.getInputStream());

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
                // Receive Message object from client
                Message msg = (Message) in.readObject();

                int sender = msg.getSender_id();
                int recipient = msg.getRecipient_id();
                for (User user : userService.getUsers()) {
                        if (recipient == user.getUser_id()) {
                            recipient_name = user.getName();
                        }
                    }
                if (name == null) {
                    for (User user : userService.getUsers()) {
                        if (sender == user.getUser_id()) {
                            name = user.getName();
                        }
                    }
                }
                
               
                sendToClient(recipient_name, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(String targetName, Message msg) {
        for (ClientHandler ch : TCPServer.allClients) {
            if (ch.name != null && ch.name.equalsIgnoreCase(targetName)) {
                try {
                    ch.out.writeObject(msg);
                    ch.out.flush();
                    System.out.println("This is a test");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
