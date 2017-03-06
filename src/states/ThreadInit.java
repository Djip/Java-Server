package states;

import interfaces.ThreadState;
import main.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by jespe on 01-03-2017.
 */
public class ThreadInit implements ThreadState {
    protected Client client;

    /**
     *@author Martin
     * @return none
     * @params none
     */
    @Override
    public void initializeClientObject() {
        //TODO read code here!
        //read only from client
        //get arduino methods here?
        System.out.println("Init is complete");
        client.setThreadState(client.getComm());
    }

    /**
     *@author Martin
     * @return none
     * @params none
     */
    @Override
    public void communicating() {
        //write only to client
        System.out.println("Can't talk until init is complete");
    }

    /**
     *@author Martin
     * @return none
     * @params none
     */
    @Override
    public void cleanUp() {
        System.out.println("Can't cleanUp until init is complete");
    }
}
