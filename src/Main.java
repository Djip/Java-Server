import main.ArduinoServer;

/**
 * Created by jespe on 15-03-2017.
 */
public class Main {
    public static void main(String[] args) {
        ArduinoServer arduinoServer = ArduinoServer.getInstance();
        arduinoServer.initializeSocket();
    }
}
