package states;

import interfaces.ASState;
import main.ArduinoServer;
import main.Client;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jespe on 01-03-2017.
 */
public class Listener implements ASState {
    DataInputStream readFromClient;
    DataOutputStream writeToClient;
    
    @Override
    public void initializeSocket() {
        System.out.println("The socket is already initialized");
    }

    @Override
    public void listening() {
        while(true) {
            try {
                System.out.println("The Server is waiting for a client on port 1000");
                //Accepts the connection for the client socket
                ServerSocket serverSocket = ArduinoServer.getInstance().getServerSocket();
                //waits for a client
                Socket socket = serverSocket.accept();

                //TODO throw new conns into a hashmap
                Client client = new Client(socket);
                client.run();
                                
                System.out.println("New client connected");
                
                readFromClient = new DataInputStream(socket.getInputStream());
                writeToClient = new DataOutputStream(socket.getOutputStream());

            // Get methode from client
                String metoder = readFromClient.readUTF();
                System.out.println(metoder);
                writeToClient.writeUTF("\nmetoden blev modtaget ");
                
                // Creating timer that will do check on each client with Heartbeat method.
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        heartbeat();
                        System.out.println("Running the Heartbeat method");
                    }
                }, 10000, 10000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reboot() {

    }

    public void heartbeat() {

        // Looping though our clients and testing if the connection to all of then is still okay.
        // Else se simply remove the client from the HashMap
        for(Map.Entry<String, Client> entry : ArduinoServer.getInstance().getClients().entrySet()) {
            String key = entry.getKey();
            Client value = entry.getValue();
            try {
                // Creating output stream so we can test connection.
                DataOutputStream os = new DataOutputStream(value.getSocket().getOutputStream());
                try {

                    // Trying to write to the socket, if that failes. then we dont have connection anymore
                    // and we will catch exeption and remove the client from our client HashMap.
                    os.writeBoolean(true);
                    System.out.println("Trying to write on the socket..");

                } catch (SocketException sockEx) {

                    // Remove the client from the Hashmap clients
                    ArduinoServer.getInstance().getClients().remove(key);
                    System.out.println("Client disconnected, and removed from client list");
                }

            } catch (IOException e) {
                System.out.println("Could not instantiate DataOutputStream");
            }
        }
    }

}
