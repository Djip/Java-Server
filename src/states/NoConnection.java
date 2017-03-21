package states;

import interfaces.ThreadState;
import main.Client;

import java.io.IOException;

/**
 * Created by jespe on 01-03-2017.
 */
public class NoConnection implements ThreadState {
    private Client client;

    public NoConnection(Client client) {
        this.client = client;
    }
    @Override
    public void initializeClientObject() {

    }

    @Override
    public void communicating(String methodName, int newValue) {
        System.out.println("Can't communicate while no connection");
    }

    @Override
    public void communicating(String methodName, int newValue, String unitName, int unitId) {
        System.out.println("Can't communicate while no connection");
    }

    @Override
    public void heartbeat() {

    }

    @Override
    public void cleanUp() {
        // close client socket
        try {
            client.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CleanUp done");
    }
}
