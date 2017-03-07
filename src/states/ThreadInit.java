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
    private Client client;

    /**
     *@author Martin
     * @return none
     * @params none
     */
    public ThreadInit (Client client)
    {
        this.client = client;
    }

    /**
     *@author Martin
     * @return none
     * @params none
     */
    @Override
    public void initializeClientObject() {
        //TODO method read code here?
        try
        {
            InputStreamReader ir = new InputStreamReader(client.getSocket().getInputStream());
            BufferedReader br = new BufferedReader(ir);
            String message = br.readLine();
            //Confirms that the message was received
            System.out.println(message);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

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
