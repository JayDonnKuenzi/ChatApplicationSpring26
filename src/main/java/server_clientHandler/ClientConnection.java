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
                System.out.println(msg.getMessage());
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
                    
                    System.out.println(dashboard.getSelectedContact());
                    System.out.println(userService.getUserbyName(dashboard.getSelectedContact()).getUser_id());
                    System.out.println(incoming.getSender_id());
                    //Receive message and check the selected value for if it is correct
                    if (userService.getUserbyName(dashboard.getSelectedContact()).getUser_id() == incoming.getSender_id()){
                        System.out.println("test print");
                        dashboard.addMessage(incoming, false);
                        dashboard.scroll();
                    } else {
                        continue;
                    }
                    
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        t.start();
    }
}
