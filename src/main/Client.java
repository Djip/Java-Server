package main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import interfaces.ThreadState;
import models.Arduino;
import states.Communication;
import states.NoConnection;
import states.ThreadInit;

import java.net.Socket;
import java.util.Map;
import java.util.Timer;
import models.ArduinoMethod;

/**
 * Created by jespe on 01-03-2017.
 */
public class Client implements Runnable {

    /**
     *@author Martin
     * @return none
     * @params none
     */
    //current state
    private ThreadState threadState;

    //Variables
    private Arduino arduino;

    //our other states
    private ThreadState init;
    private ThreadState comm; //communication
    private ThreadState noCon; //NoConnection

    private Socket socket;

    private Timer heartbeatTimer;

    /**
     *@author Martin
     * @return none
     * @params none
     */
    public Client()
    {
        init = new ThreadInit(this);
        comm = new Communication(this);
        noCon = new NoConnection(this);
    }

    /**
     *@author Martin
     * @return none
     * @params the client socket
     */
    public Client(Socket socket)
    {
        this(); //runs the default constructor
        this.socket = socket;

    }

    @Override
    public void run()
    {
        System.out.println("we are in run");
        threadState = getInit();
        initializeClientObject();
    }

    public void initializeClientObject() {
        threadState.initializeClientObject();
    }

    public void communicating() {
        threadState.communicating();
    }

    public void cleanUp() {
        threadState.cleanUp();
    }

    public ThreadState getThreadState() {
        return threadState;
    }

    /**
     *@author Martin
     * @return void
     * @params the new state
     */
    public void setThreadState (ThreadState state)
    {
        threadState = state;
    }

    public ThreadState getInit() { return init; }

    public ThreadState getComm()
    {
        return comm;
    }

    public ThreadState getNoCon()
    {
        return noCon;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public Arduino getArduino() {
        return arduino;
    }

    public void setArduino(Arduino arduino) {
        this.arduino = arduino;
    }

    public Timer getHeartbeatTimer() {
        return heartbeatTimer;
    }

    public void setHeartbeatTimer(Timer heartbeatTimer) {
        this.heartbeatTimer = heartbeatTimer;
    }
    
    public String serializeXML() {
        String xml = "";
        xml += "<ArduinoCollection>";
        xml += "<Arduinos>";
                
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(ArduinoMethod.class); 
        //xstream.omitField(Arduino.class, "entry");
        xstream.alias("Arduino", Arduino.class);
        xstream.aliasField("core", Arduino.class, "coreMethods");
        xstream.aliasField("group", Arduino.class, "groupMethods");
        ArduinoServer.getInstance().getClients();
        
        for (Map.Entry<String, Client> entry : ArduinoServer.getInstance().getClients().entrySet() ) {
            //System.out.println("We are inside for loop");
            
            Arduino xmlArduino = entry.getValue().getArduino();
            xml += xstream.toXML(xmlArduino);
  
        }
        
        xml += "</Arduinos>";
        xml += "</ArduinoCollection>";
        
        System.out.println(xml);
        
        return xml;
    }
}
