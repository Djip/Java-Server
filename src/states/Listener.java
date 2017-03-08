package states;

import interfaces.ASState;
import main.ArduinoServer;
import main.Client;
import models.Arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
                }, 10000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reboot() {

    }

    public void heartbeat() {
        Map<String, Arduino> tmp = ArduinoServer.getInstance().getClients();
        boolean clientNotPinged = false;

        for(Map.Entry<String, Arduino> entry : tmp.entrySet()) {
            String key = entry.getKey();
            Arduino value = entry.getValue();

            // Trying to ping the Arduino client
            try {
                boolean pinged = InetAddress.getByName(value.getIp()).isReachable(5000);

                if(!pinged) {
                    tmp.remove(key);
                    clientNotPinged = true;
                    System.out.println("Client disconnected, and prepared for remove");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(clientNotPinged) {
            // When removed items we set the Arduino HasMap of client.
            ArduinoServer.getInstance().setClients(tmp);
            System.out.println("Setting the Map to our tmp");
        }
    }

}
