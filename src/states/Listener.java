package states;

import interfaces.ASState;
import main.ArduinoServer;
import main.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

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
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reboot() {

    }
}
