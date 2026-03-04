package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    //List to keep track of all connected clients
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9001);
        //Port number 9001
        System.out.println("Server started. Listening on port 9001...");

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }


    }

    static class ClientHandler implements Runnable{

        private Socket clientSocket;
        private List<ClientHandler> clients;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket, List<ClientHandler> clients) throws IOException{
            this.clientSocket = clientSocket;
            this.clients = clients;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void run() {
           try{
               String inputLine;
               while ((inputLine = in.readLine()) != null)
               {
                   //Broadcast message to all clients
                   for(ClientHandler aClient : clients){ //for each clientHandler object in the clients list
                       aClient.out.println(inputLine);
                   }

               }
           } catch (IOException e){
               System.out.println("An error occurred: " + e.getMessage());
           }
           finally {
               try{
                   in.close();
                   out.close();
               } catch (IOException e){
                   e.printStackTrace();
               }

           }
        }
    }

}
