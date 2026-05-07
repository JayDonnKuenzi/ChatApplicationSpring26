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

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // SEND OBJECT TO SERVER
    public void sendMessage(Message msg) {
        try {
            if (socket != null){
            out.writeObject(msg);
            out.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // START LISTENING THREAD
    public void receiveMsg() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    Message incoming = (Message) in.readObject();
                    for (User user: userService.getUsers()){
                    if (dashboard.getSelectedContact().equals(
                    // Update GUI safely
                            }
                    dashboard.addMessage(incoming, false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        t.start();
    }
}
