import main.ArduinoServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jespe on 01-03-2017.
 */
public class Main {

    public static void main(String[] args) {

        ArduinoServer arduinoServer = ArduinoServer.getInstance();
        arduinoServer.initializeSocket();
    }
}