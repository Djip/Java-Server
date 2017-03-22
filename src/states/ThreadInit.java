package states;

import interfaces.ThreadState;
import main.ArduinoServer;
import main.Client;
import models.Arduino;
import models.ArduinoMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.SocketTimeoutException;
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
        //TODO method read code here?
        try
        {
            System.out.println("Yo whats up!");
            InputStreamReader ir = new InputStreamReader(client.getSocket().getInputStream());
            BufferedReader br = new BufferedReader(ir);
            String message = "";

            try {
                while (br.ready()) {
                    message = br.readLine().trim();
                    if (!message.isEmpty()) {
                        System.out.println(message);
                        // Deserializing the string from Arduino
                        deSerialize(message);

                        // Creating timer that will do check on each client with Heartbeat method.
                        client.setHeartbeatTimer(new Timer());
                        client.getHeartbeatTimer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                client.getThreadState().heartbeat();
                            }
                        }, 2000, 2000);

                        System.out.println("New client connected");

                        client.setThreadState(client.getComm());
                    }
                 }

            } catch (SocketTimeoutException ee) {

                System.err.println("Timeout");

            } catch (Exception e) {

                e.printStackTrace();

            }
            //br.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void communicating(String methodName, int newValue) {
        System.out.println("Can't talk until init is complete");
    }

    @Override
    public void communicating(String methodName, int newValue, String unitName, int unitId) {
        System.out.println("Can't talk until init is complete");
    }

    public void deSerialize(String message)
    {
        System.out.println("OK");
        Arduino arduino = new Arduino();

        // Split message by "#"
        String[] parts = message.split("#");

        //Set the Arduino name
        arduino.setName(parts[0]);

        //This method takes an array and converts it to a List object.
        List<String> methods = new ArrayList<String>(Arrays.asList(parts));

        //Removing the name
        methods.remove(0);

        // Creating a hashmap for group and core methods
        List<ArduinoMethod> groupMethods = new ArrayList<>();
        List<ArduinoMethod> coreMethods = new ArrayList<>();


        for (String methodString : methods)
        {
            //Split var by ","
            String [] var = methodString.split(",", -1);

            int unitCount = 0;
            if (var[6] != null && !var[6].equals("")) {
                unitCount = Integer.parseInt(var[6]);
            }

            //checking currentState are null or empty
            if(unitCount > 0)
            {
                //If empty or null put into groupMethods
                groupMethods.add(new ArduinoMethod(var[0],Integer.parseInt(var[1]),Integer.parseInt(var[2]),Integer.parseInt(var[3]),Integer.parseInt(var[4]),var[5], var[6]));

            }
            else
            {
                //Else put into coreMethods
                coreMethods.add(new ArduinoMethod(var[0],Integer.parseInt(var[1]),Integer.parseInt(var[2]),Integer.parseInt(var[3]),Integer.parseInt(var[4]),var[5], var[6]));
            }

        }
        // Set core & groupmethods
        arduino.setCoreMethods(coreMethods);
        arduino.setGroupMethods(groupMethods);

        //Get and Set the ip and converte the ip to a string
        arduino.setIp(client.getSocket().getInetAddress().toString().replace("/", ""));

        // Creating new hashmap for connectet arduino client
        //Map<String, Arduino> arduoinoClient = new HashMap<>();

        // Adding the arduino to the client.
        client.setArduino(arduino);

        // Get instance and set clients in arduinoClient
        // ArduinoServer.getInstance().setClients(arduoinoClient);
        ArduinoServer.getInstance().addClient(client);
    }

    /**
     *@author Martin
     * @return none
     * @params none
     */
    @Override
    public void heartbeat() {
        System.out.println("Can't heartbeat while initializing");
    }

    @Override
    public void cleanUp() {
        System.out.println("Can't cleanUp until init is complete");
    }
}

