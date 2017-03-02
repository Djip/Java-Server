package states;

import interfaces.ASState;
import main.ArduinoServer;

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
                Socket socket = serverSocket.accept();

                System.out.println("Client connected");

                InputStreamReader ir = new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                String message = br.readLine();
                //Confirms that the message was received
                System.out.println(message);

                if (message.equals("HELLO")) {
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println("Received our hello message.");
                } else {
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println("Did not receive your hello message");
                }

                if (message.equals("bye"))
                    break;    // breaking the while loop.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reboot() {

    }
}
