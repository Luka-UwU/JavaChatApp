package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ChatClient {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private BufferedReader userInput = null;
    private String username = null;

    public ChatClient(String address, int port, String username) {
        try{
           this.socket = new Socket(address, port);
           System.out.println("Connected to the chat server at " + address + ":" + port);

           this.userInput = new BufferedReader(new InputStreamReader(System.in)); //read user input
           this.out = new PrintWriter(socket.getOutputStream(), true); //send user input to server
           this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //receive messages from other users
           this.username = username;


        }
        catch (UnknownHostException u){
            System.out.println("Host not found: " + u.getMessage());
        }
        catch (IOException e){
                System.out.println("An error occurred: " + e.getMessage());
            }

    }

    public void sendMessage(){
        try {
            out.println(username);

            while(socket.isConnected()) {
                String line = "";
                while (!line.equals("exit")) {
                    line = userInput.readLine();
                    out.println(username + ": " + line); //send user input to server

                }
            }

            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void listenToMessage(){
        new Thread(new Runnable() {
            public void run() {
                String message;
                while(socket.isConnected()) {
                    try{
                        message = in.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                }
            }
        }).start();
    }



    public static void main(String[] args) {
        System.out.println("Enter your username: ");
        Scanner name = new Scanner(System.in);
        String username = name.nextLine();
        ChatClient client = new ChatClient("localhost", 9001, username);
        client.listenToMessage();
        client.sendMessage();
    }
}
