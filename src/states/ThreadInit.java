package states;

import interfaces.ThreadState;
import main.ArduinoServer;
import main.Client;
import models.Arduino;
import models.ArduinoMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

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

    /**
     *@author Samet
     * @return none
     * @params Deserialize and HashMap
     * */
    @Override
    public void initializeClientObject() {
        Arduino arduino = new Arduino();


        //TODO method read code here?
        try
        {
            InputStreamReader ir = new InputStreamReader(client.getSocket().getInputStream());
            BufferedReader br = new BufferedReader(ir);
            String message = br.readLine();
            //Confirms that the message was received
            System.out.println(message);

            message = "samet#togkoer,defaultValue,minValue,maxValue,currentValue,unitName,unitCount#togkoer,defaultValue,minValue,maxValue,currentValue,unitName,unitCount#" ;

            // Split message by "#"
            String[] parts = message.split("#");

            //Set the Arduino name
            arduino.setName(parts[0]);

            //This method takes an array and converts it to a List object.
            List<String> methods = new ArrayList<String>(Arrays.asList(parts));

            //Removing the name
            methods.remove(0);

            // Creating a hashmap for group and core methods
             Map<String, ArduinoMethod> groupMethods = new HashMap<>();
             Map<String, ArduinoMethod> coreMethods = new HashMap<>();


            for (String methodString : methods)
            {
                //Split var by ","
                String [] var = methodString.split(",");

                //checking currentState are null or empty
                if(var[5] != null || var[5] != "")
                {
                    //If empty or null put into groupMethods
                    groupMethods.put(var[0], new ArduinoMethod(var[0],Integer.parseInt(var[1]),Integer.parseInt(var[2]),Integer.parseInt(var[3]),Integer.parseInt(var[4]),var[5], var[6]));


                }
                else
                {
                    //Else put into coreMethods
                    coreMethods.put(var[0], new ArduinoMethod(var[0],Integer.parseInt(var[1]),Integer.parseInt(var[2]),Integer.parseInt(var[3]),Integer.parseInt(var[4]),var[5], var[6]));
                }

            }
            // Set core & groupmethods
            arduino.setCoreMethods(coreMethods);
            arduino.setGroupMethods(groupMethods);

            //Get and Set the ip and converte the ip to a string
            arduino.setIp(client.getSocket().getInetAddress().toString());

            // Creating new hashmap for connectet arduino client
            Map<String, Arduino> arduoinoClient = new HashMap<>();


            arduoinoClient.put(arduino.getIp(), arduino);
            // Get instance and set clients in arduinoClient
            ArduinoServer.getInstance().setClients(arduoinoClient);
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

    private void deSerialize()
    {


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

