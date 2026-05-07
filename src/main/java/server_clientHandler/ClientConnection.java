package server_clientHandler;

import PrivateMessage.UserService;
import java.io.*;
import java.net.Socket;
import Views.DashboardGUI;
import models.Message;
import models.User;

public class ClientConnection {

    // socket connected to the server
    private Socket socket;
    // stream used to send objects
    private ObjectOutputStream out;
    // stream used to receive objects
    private ObjectInputStream in;
    private DashboardGUI dashboard;
    private String name;
    private UserService userService;

    public ClientConnection(DashboardGUI dashboardGUI) {
        try {
            // connect to the server
            this.socket = new Socket("10.53.3.249", 5000);

            // create output stream first
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            // create input stream second
            this.in = new ObjectInputStream(socket.getInputStream());

            // store dashboard reference
            this.dashboard = dashboardGUI;
            name = dashboard.getUser().getName();
            // initialize user service
            userService = new UserService();

            // send current user id to server
            out.writeObject(dashboard.getUser().getUser_id());
            out.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // SEND OBJECT TO SERVER
    public void sendMessage(Message msg) {
        try {
            // send message if socket exists
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
        // start background thread for incoming messages
        Thread t = new Thread(() -> {
            try {
                System.out.println("receive thread started");
                while (true) {
                    System.out.println("waiting for object...");
                    // read next object from server
                    Object obj = in.readObject();
                    System.out.println("object arrived: " + obj);

                    // ignore non-message objects
                    if (!(obj instanceof Message)) {
                        System.out.println("Not a Message object: " + obj.getClass());
                        continue;
                    }

                    // cast object to message
                    Message incoming = (Message) obj;
                    System.out.println("message text: " + incoming.getMessage());
                    System.out.println("sender id: " + incoming.getSender_id());

                    // get currently selected contact
                    String selected = dashboard.getSelectedContact();
                    System.out.println("selected contact: " + selected);

                    // skip if no contact is selected
                    if (selected == null) {
                        System.out.println("selected contact is null");
                        continue;
                    }

                    // find selected user by name
                    User selectedUser = userService.getUserbyName(selected);
                    if (selectedUser == null) {
                        System.out.println("selected user not found");
                        continue;
                    }

                    // only show message if it matches selected chat
                    if (selectedUser.getUser_id() == incoming.getSender_id()) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            // update gui on swing thread
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
