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
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.runtime.Debug;
import models.Arduino;
import models.ArduinoMethod;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

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

    @Override
    public void communicating() {
        
        // Testing the xml convertion
        client.serializeXML();
        
        // til client fra UWP
        System.out.println("Trying to sent a test");
        try {
            dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
            dataOutputStream.writeUTF("Test");
            System.out.println("Test sent");
            
            // Lets get back to listen
            dataOutputStream.writeUTF("Writing to the client again to test that the socket is still open for buisness");
            System.out.println("Test sent");
            // Now we can write to the client as much as we want. AS LONG THAT WE DONT CLOSE dataoutput og socket...
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close dataOutputStream
        /*try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
        // We should not close this connection unless we dont have one to the client.
        // Lets check if the socket has been closed, if it is, then lets cleanup.
        if(client.getSocket().isClosed()) {
            // set State: NoConnection and method: cleanUp
            client.setThreadState(client.getNoCon());
            client.cleanUp();
        }
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

            } catch (UnknownHostException e) {

                //e.printStackTrace();
                System.out.println("got unknowhost exeption");

            } catch (IOException e) {

                System.out.println("got IOExeption");
                //e.printStackTrace();

            }
        }
    }
    
    @Override
    public void cleanUp() {

    }
}
