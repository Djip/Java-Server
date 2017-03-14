package classes.main;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import classes.interfaces.ASState;
import classes.states.Init;
import classes.states.Listener;

/**
 * Created by jespe on 01-03-2017.
 */

public class ArduinoServer {
    // Constants
    public static final int portNumber = 9000;

    // Instance
    public static ArduinoServer instance;

    // State variables
    private ASState asState;

    private ASState init;
    private ASState listener;
    private ASState error;

    // Standard Variables
    private ServerSocket serverSocket;
    private Map<String, Client> clients = new HashMap<>();

    // Constructer
    private ArduinoServer() {
        init = new Init();
        listener = new Listener();
        //error = new Error();

        asState = getInit();
    }

    // Get Instance (Singleton)
    public static ArduinoServer getInstance() {
        if (instance == null) {
            instance = new ArduinoServer();
        }

        return instance;
    }

    // Set new state
    public void setAsState(ASState asState) {
        this.asState = asState;
    }

    // Run Methods in states
    public void initializeSocket() {
        asState.initializeSocket();
    }

    public void listening() {
        asState.listening();
    }

    public void reboot() {
        asState.reboot();
    }

    // Get States
    public ASState getInit() {
        return init;
    }

    public ASState getListener() {
        return listener;
    }

    public ASState getError() {
        return error;
    }

    // Getters / Setters
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        this.clients.put(client.getArduino().getIp(), client);
    }
}
