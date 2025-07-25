
import java.io.*;
import java.net.*;
@SuppressWarnings("CallToPrintStackTrace")

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.print("Enter your name: ");
            String name = input.readLine();
            out.println(name);

            // Send messages
            @SuppressWarnings("CallToPrintStackTrace")
            Thread sendThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {
                        out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Receive messages
            @SuppressWarnings("CallToPrintStackTrace")
            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = serverIn.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            sendThread.start();
            receiveThread.start();

            sendThread.join();
            receiveThread.join();

        } 
        
        catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}
