package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {

        ChatServer chatServer = new ChatServer(9001);
        //Port number 9001
        chatServer.startServer();

    }

    public void startServer(){
        try {
            System.out.println("Server started. Listening on port 9001...");

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected.");
                ClientHandler clientThread = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientThread);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("An error occurred when trying to start the server.");
        }
    }

    public void stopServer(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server stopped.");
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }




}
