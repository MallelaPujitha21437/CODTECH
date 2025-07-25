import java.io.*;
import java.net.*;
import java.util.*;
@SuppressWarnings("CallToPrintStackTrace")
public class ChatServer {
    private static final int PORT = 12345;
    @SuppressWarnings("FieldMayBeFinal")
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server is running on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    static class ClientHandler implements Runnable {
        @SuppressWarnings("FieldMayBeFinal")
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @SuppressWarnings("override")
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String name = in.readLine(); // first message = name
                System.out.println(name + " joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(name + ": " + message);
                    ChatServer.broadcast(name + ": " + message, this);
                }
            } catch (IOException e) {
                System.out.println("A client disconnected.");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ChatServer.removeClient(this);
            }
        }

        void sendMessage(String message) {
            out.println(message);
        }
    }
}
