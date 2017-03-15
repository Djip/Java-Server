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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reboot() {

    }
}
