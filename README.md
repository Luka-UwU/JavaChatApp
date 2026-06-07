# Java Multi-Threaded CLI Chat Application

A lightweight, real-time command-line chat application engineered in Java using a classic Client-Server architecture. The project leverages low-level network sockets and concurrent multi-threading to facilitate synchronous, bi-directional message routing between multiple active clients over a single server network.

## 🚧 Status & Roadmap (Work in Progress)
This project is currently under active development. While the core multi-threaded broadcasting engine is fully operational, there are several features planned to improve scalability, security, and user experience:

* **[Planned] Graphical User Interface (GUI):** Transitioning from a CLI to a rich desktop interface using JavaFX or Swing.
* **[Planned] Private Messaging:** Implementing a command (e.g., `/whisper <user>`) to route messages to specific clients instead of broadcasting to the entire room.
* **[Planned] User Authentication:** Adding a secure login system requiring a password before granting access to the server port.
* **[Planned] Remove hardcoded variables:** Refactor the ChatClient to ask for ip and port before connecting.

## 🚀 Features
* **Real-Time Client-Server Architecture:** Utilizes dedicated server-side sockets running on port `9001` to listen for incoming connections and handle persistent communication channels.
* **Concurrent Multi-Threading:** Spawns an independent `ClientHandler` thread for each connected user, preventing blocking operations and ensuring scalable message handling.
* **Broadcasting Protocol:** Implements a dynamic client-tracking static list on the server to instantly broadcast messages from one sender to all other active chat participants.
* **Graceful Disconnection Handlers:** Actively monitors socket states and user inputs. When a user types `exit` or disconnects, the system safely closes input/output streams and notifies the room that the user has left the chat.

## 🛠️ Tech Stack & Concepts
* **Core Language:** Java 
* **Networking (Java I/O):** `ServerSocket`, `Socket`, `BufferedReader`, and `PrintWriter` for TCP/IP data stream communication.
* **Concurrency:** `Runnable` and `Thread` architectures for asynchronous task management.
* **Interface:** Command-Line Interface (CLI) utilizing standard `System.in` and `Scanner` for user input.

## 📁 Project Architecture
```text
.
├── .idea/                  # IDE configuration files
├── src/
│   ├── client/
│   │   └── ChatClient.java     # Establishes socket connection and manages user I/O
│   └── server/
│       ├── ChatServer.java     # Listens for connections on port 9001
│       └── ClientHandler.java  # Runnable thread handling individual client broadcasts
├── .gitignore
├── JavaChatApp.iml         # IntelliJ IDEA module file
└── README.md
```

## 🌐 Network Configuration (IP & Port)
By default, this application is configured to run locally on a single machine for testing purposes. It uses localhost (127.0.0.1) as the IP address and port 9001.

If you want to use this chat application across multiple computers on a Local Area Network (LAN) or over the internet, you will need to modify the source code before compiling:

1. Changing the Server Port:
   
Open src/server/ChatServer.java. You can change the port number passed into the ChatServer constructor if port 9001 is already in use by another application on your machine.

```java
// Inside ChatServer.main()
ChatServer chatServer = new ChatServer(9001); // Change 9001 to your desired port
```
2. Changing the Client Target IP & Port:
   
Open src/client/ChatClient.java. To connect to a server hosted on a different machine, replace "localhost" with the IPv4 address of the computer running the server. Make sure the port matches the one you set in the server file!
```java
// Inside ChatClient.main()
ChatClient client = new ChatClient("192.168.x.x", 9001, username); // Replace with server's IP and Port
```
(Note: If connecting over the internet, ensure the host machine has port-forwarded the selected port through their router firewall).

## ⚙️ Installation & Setup
Follow these steps to compile and run the application on your local machine using the terminal:

1. Clone the Repository:
```bash
git clone [https://github.com/Luka-UwU/JavaChatApp.git](https://github.com/Luka-UwU/JavaChatApp.git)
cd JavaChatApp
```
2. Compile the Source Code:
   
From the root directory of your project, compile the Java files into an output folder (e.g., out):

```bash
javac -d out src/client/*.java src/server/*.java
```
3. Run the Server Instance:
   
The server must be up and running before any clients try to connect.

```bash
java -cp out server.ChatServer
```
You should see a message confirming: "Server started. Listening on port 9001..."

4. Launch Client Instances:
   
Open a separate terminal window for each user you want to bring into the chatroom.

```bash
java -cp out client.ChatClient
```
## 🎮 How to Use
Upon running the client, you will be prompted to enter a username in the console.

Once entered, the server will announce your arrival to all other connected clients.

Type your message and hit Enter to broadcast it to the room.

To safely disconnect and close your socket, simply type exit into the console.

## 🛡️ License
Distributed under the MIT License.
