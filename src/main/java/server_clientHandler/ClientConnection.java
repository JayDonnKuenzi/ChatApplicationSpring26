package server_clientHandler;

import java.io.*;
import java.net.Socket;
import Views.DashboardGUI;
import models.Message;

public class ClientConnection {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private DashboardGUI dashboard;

    public ClientConnection(DashboardGUI dashboardGUI) {
        try {
            this.socket = new Socket("10.53.3.249", 5000);

            // IMPORTANT: create ObjectOutputStream FIRST
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();

            this.in = new ObjectInputStream(socket.getInputStream());

            this.dashboard = dashboardGUI;

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

                    // Update GUI safely
                    dashboard.addMessage(incoming, false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        t.start();
    }
}
