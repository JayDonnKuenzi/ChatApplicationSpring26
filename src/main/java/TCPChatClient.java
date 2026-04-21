
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
public class TCPChatClient {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("10.53.3.249", 5000);
        
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    listenForMessagesFromServer(inFromServer);
                }
            };
            Thread t = new Thread(r);
            t.start();
            
            while(true){
                String userInput = inFromUser.readLine();
                
                outToServer.writeBytes(userInput+"\n");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    static void listenForMessagesFromServer(BufferedReader inFromServer){
        while(true){
            String serverResponse;
            try {
                serverResponse = inFromServer.readLine();
                if(!serverResponse.isEmpty()){
                    System.out.println(serverResponse);
                }
            } catch (IOException ex) {
             ex.printStackTrace();
            }
        }
    }
}
