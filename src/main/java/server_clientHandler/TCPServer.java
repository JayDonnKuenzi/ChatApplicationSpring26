package server_clientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author JayDo
 */
public class TCPServer {

    // stores all connected clients
    public static ArrayList<ClientHandler> allClients;

    public TCPServer() {
        // initialize client list
        allClients = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        // initialize client list
        allClients = new ArrayList<>();

        // create server socket on port 5000
        ServerSocket ss = new ServerSocket(5000);
        while (true) {
            try {
                // wait for a client connection
                Socket incomingSocket = ss.accept();

                // create handler for the client
                ClientHandler ch = new ClientHandler(incomingSocket);
                allClients.add(ch);
                // run client handler on a new thread
                Thread t = new Thread(ch);
                t.start();

            } catch (Exception e) {

            }
        }

    }
}
