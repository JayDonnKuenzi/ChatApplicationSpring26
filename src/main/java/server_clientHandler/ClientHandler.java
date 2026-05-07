package server_clientHandler;

import models.Message;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String name;

    public ClientHandler(Socket socket) {
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

                // Broadcast to all other clients
                sendBroadcastMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendBroadcastMessage(Message msg) {
        for (ClientHandler ch : TCPServer.allClients) {
            try {
                if (ch != this) {
                    ch.out.writeObject(msg);
                    ch.out.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
