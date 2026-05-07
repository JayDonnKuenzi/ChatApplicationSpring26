package server_clientHandler;

import PrivateMessage.UserService;
import java.io.*;
import java.net.Socket;
import Views.DashboardGUI;
import models.Message;
import models.User;

public class ClientConnection {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private DashboardGUI dashboard;
    private String name;
    private UserService userService;

    public ClientConnection(DashboardGUI dashboardGUI) {
        try {
            this.socket = new Socket("10.53.3.249", 5000);

            // IMPORTANT: create ObjectOutputStream FIRST
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            this.in = new ObjectInputStream(socket.getInputStream());

            this.dashboard = dashboardGUI;
            name = dashboard.getUser().getName();
            userService = new UserService();

            out.writeObject(dashboard.getUser().getUser_id());
            out.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // SEND OBJECT TO SERVER
    public void sendMessage(Message msg) {
        try {
            if (socket != null) {
                out.writeObject(msg);
                out.flush();
                System.out.println(msg.getMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // method to receive message
    public void receiveMsg() {
        Thread t = new Thread(() -> {
            try {
                System.out.println("receive thread started");
                while (true) {
                    System.out.println("waiting for object...");
                    Object obj = in.readObject();
                    System.out.println("object arrived: " + obj);

                    if (!(obj instanceof Message)) {
                        System.out.println("Not a Message object: " + obj.getClass());
                        continue;
                    }

                    Message incoming = (Message) obj;
                    System.out.println("message text: " + incoming.getMessage());
                    System.out.println("sender id: " + incoming.getSender_id());

                    String selected = dashboard.getSelectedContact();
                    System.out.println("selected contact: " + selected);

                    if (selected == null) {
                        System.out.println("selected contact is null");
                        continue;
                    }

                    User selectedUser = userService.getUserbyName(selected);
                    if (selectedUser == null) {
                        System.out.println("selected user not found");
                        continue;
                    }

                    if (selectedUser.getUser_id() == incoming.getSender_id()) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            dashboard.addMessage(incoming, false);
                            dashboard.scroll();
                        });
                    } else {
                        System.out.println("message received but filtered out");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        t.start();
    }
}
