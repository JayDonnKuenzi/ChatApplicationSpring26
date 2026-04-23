package server_clientHandler;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static ArrayList<ClientHandler> allClients;
    
    public TCPServer(){
        allClients = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
                allClients = new ArrayList<>();

        ServerSocket ss = new ServerSocket(5000);
        while(true){
            try{
                Socket incomingSocket = ss.accept();
                
                ClientHandler ch = new ClientHandler(incomingSocket);
                allClients.add(ch);
                Thread t = new Thread(ch);
                t.start();
                
            }catch(Exception e){
                
            }
        }
        
    }
}
