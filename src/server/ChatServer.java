package server;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        //Port number 2000
        System.out.println("Server started. Listening on port 2000...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");
    }
}
