package client;

import java.net.*;
import java.io.*;


public class ChatClient {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private BufferedReader inputConsole = null;

    public ChatClient(String address, int port) {
        try{
           socket = new Socket(address, port);
           System.out.println("Connected to the chat server at " + address + ":" + port);

           inputConsole = new BufferedReader(new InputStreamReader(System.in)); //receives messages from server
           out = new PrintWriter(socket.getOutputStream(), true); //send user input to server
           in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //read user input

            String line = "";
            while(!line.equals("exit")){
                line = inputConsole.readLine();
                out.println(line); //send user input to server
                System.out.println(in.readLine());
            }

            socket.close();
            in.close();
            out.close();


        }
        catch (UnknownHostException u){
            System.out.println("Host not found: " + u.getMessage());
        }
        catch (IOException e){
                System.out.println("An error occurred: " + e.getMessage());
            }

    }



    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 9001);
    }
}
