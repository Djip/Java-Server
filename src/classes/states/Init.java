package classes.states;

import classes.interfaces.ASState;
import classes.main.ArduinoServer;

import java.net.ServerSocket;

/**
 * Created by jespe on 01-03-2017.
 */
public class Init implements ASState {

    @Override
    public void initializeSocket() {
        try {
            ArduinoServer.getInstance().setServerSocket(new ServerSocket(ArduinoServer.portNumber));
            ArduinoServer.getInstance().setAsState(ArduinoServer.getInstance().getListener());
            ArduinoServer.getInstance().listening();
        } catch (Exception e) {
            e.printStackTrace();
            ArduinoServer.getInstance().setAsState(ArduinoServer.getInstance().getError());
        }
    }

    @Override
    public void listening() {
        System.out.println("Not possible to listen while socket isn't initialized");
    }

    @Override
    public void reboot() {
        System.out.println("Not possible to reboot while in init state");
    }
}
