package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{

    private Socket clientSocket;
    //List to keep track of all connected clients
    public static List<ClientHandler> clients = new ArrayList<>();
    private PrintWriter out;
    private BufferedReader in;
    private String clientUsername;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true); //send message to client
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //receive message from client
        this.clientUsername = in.readLine();
        ClientHandler.clients.add(this);
        broadcastMessage("Server: " + clientUsername + " has joined the chat.");
    }

    public void run() {
        try{
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                broadcastMessage(inputLine);
            }
        } catch (IOException e){
            System.out.println("An error occurred: " + e.getMessage());
        }
        finally {
            try{
                removeClient();
                in.close();
                out.close();
            } catch (IOException e){
                removeClient();
                e.printStackTrace();
            }

        }
    }

    public void broadcastMessage(String messageToSend){
        //Broadcast message to all clients
        for (ClientHandler aClient : clients) { //for each clientHandler object in the clients list

            if (!aClient.clientUsername.equals(clientUsername)) {
                aClient.out.println(messageToSend);
            }
        }
    }

    public void removeClient(){
        broadcastMessage("Server: " + clientUsername + " has left the chat.");
        clients.remove(this);
    }
}
