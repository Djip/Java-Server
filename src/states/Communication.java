package states;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import interfaces.ThreadState;
import java.awt.BorderLayout;
import main.ArduinoServer;
import main.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.runtime.Debug;
import models.Arduino;
import models.ArduinoMethod;

/**
 * Created by jespe on 01-03-2017.
 */
public class Communication implements ThreadState {
    private Client client;

    DataOutputStream dataOutputStream = null;

    public Communication(Client client) {
        this.client = client;
    }

    @Override
    public void initializeClientObject() {
 // not in this state
    }

    public void communicating(String methodToCall) {


        try {
            dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
            dataOutputStream.writeUTF(methodToCall);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close dataOutputStream
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void communicating(String methodName, int newValue) {
        String methodToCall = methodName + "," + newValue;

        communicating(methodToCall);
    }

    @Override
    public void communicating(String methodName, int newValue, String unitName, int unitId) {
        String methodToCall = methodName + "," + newValue + "," + unitName + "," + unitId;

        communicating(methodToCall);
    }

    @Override
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
                    client.getHeartbeatTimer().cancel();
                    client.getHeartbeatTimer().purge();
                    client.setThreadState(client.getNoCon());
                    client.cleanUp();
                }

            } catch (IOException e) {
                System.out.println("Could not instantiate DataOutputStream");
            }
        }
    }
    
    @Override
    public void cleanUp() {

    }
}
