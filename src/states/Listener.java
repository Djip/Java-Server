package states;

import interfaces.ASState;
import main.ArduinoServer;
import main.Client;
import models.Arduino;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jespe on 01-03-2017.
 */
public class Listener implements ASState {
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
        System.out.println("Hello from heartbeat24");
        Map<String, Client> tmp = ArduinoServer.getInstance().getClients();
        boolean clientNotPinged = false;
System.out.println(tmp.size());
        for(Map.Entry<String, Client> entry : tmp.entrySet()) {
            System.out.println("Hello from heartbeat37");
            String key = entry.getKey();
            Client value = entry.getValue();
            try {
                DataOutputStream os = new DataOutputStream(value.getSocket().getOutputStream());
                try {
                    os.writeBoolean(true);
                    System.out.println("Hello from heartbeat");
                } catch (SocketException sockEx) {
                    System.out.println("Catch on the fucker.....");
                    tmp.remove(key);
                    clientNotPinged = true;
                    System.out.println("Client disconnected, and prepared for remove");
                }

            } catch (IOException e) {

                System.out.println("Could not instantiate DataOutputStream");
            }

            // Trying to ping the Arduino client
            /*try {
                boolean pinged = InetAddress.getByName(value.getIp()).isReachable(5000);

                if(!pinged) {
                    tmp.remove(key);
                    clientNotPinged = true;
                    System.out.println("Client disconnected, and prepared for remove");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }

        if(clientNotPinged) {
            // When removed items we set the Arduino HasMap of client.
            ArduinoServer.getInstance().setClients(tmp);
            System.out.println("Setting the Map to our tmp");
        }
    }

}
