package main;

import interfaces.ThreadState;
import models.Arduino;
import states.Communication;
import states.NoConnection;
import states.ThreadInit;

import java.net.Socket;

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


    /**
     *@author Martin
     * @return none
     * @params none
     */
    public Client()
    {
        init = new ThreadInit(this);
        comm = new Communication();
        noCon = new NoConnection();
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
        threadState.initializeClientObject();


        // test if we can send message to client
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
}
