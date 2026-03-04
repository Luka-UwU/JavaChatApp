package server;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost", 5000);
        //Connect to server on port 5000
        System.out.println("Connected to server.");
    }
}
