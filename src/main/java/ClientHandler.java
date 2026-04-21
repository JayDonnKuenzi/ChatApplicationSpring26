
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JayDo
 */
public class ClientHandler implements Runnable{

    private Socket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private String name;
    
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try{
            this.outToClient.writeBytes("Enter your username:\n");
            String msgFromClient = inFromClient.readLine();
            
            this.name = msgFromClient;
        }catch(IOException e){
            e.printStackTrace();
        }

        while(true){
            try {
                String msgFromClient = inFromClient.readLine();
                sendBroadcastMessage(this.name.toUpperCase() + ": " + msgFromClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    private void sendBroadcastMessage(String msgFromClient){
        for(int i = 0; i < TCPServer.allClients.size(); i++){
            try {
                ClientHandler ch = TCPServer.allClients.get(i);
                if(!this.equals(ch)){
                    ch.outToClient.writeBytes(msgFromClient+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
    
}
