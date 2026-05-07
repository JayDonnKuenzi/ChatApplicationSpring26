package server_clientHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author JayDo
 */

public class ClientConnection{
    
    private Socket socket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private String inFromUser;
    private String msg;

    public ClientConnection(DataOutputStream outToServer, BufferedReader inFromServer) {
        try {
            this.socket = new Socket("10.53.3.249", 5000);
            this.outToServer = new DataOutputStream(socket.getOutputStream());
            this.inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.inFromUser = null;
            this.msg = "";
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }
        
        public void outToServer(String msg) throws IOException{
            inFromUser = msg;
            outToServer.writeBytes(inFromUser+"\n");
        
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    listenForMessagesFromServer(inFromServer);
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
             
    
    
    public void listenForMessagesFromServer(BufferedReader inFromServer){
        while(true){
            String serverResponse;
            try {
                serverResponse = inFromServer.readLine();
                if(!serverResponse.isEmpty()){
                    msg = serverResponse;
                }
            } catch (IOException ex) {
             ex.printStackTrace();
            }
        }
    }
}
