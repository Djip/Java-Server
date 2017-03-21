/**
 * Created by jespe on 20-03-2017.
 */

import main.ArduinoServer;
import main.Client;
import models.Arduino;
import models.ArduinoMethod;

import javax.servlet.http.HttpServlet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@ServerEndpoint("/websocket")
public class AppWebsocket extends HttpServlet {
    private List<Session> sessions = new ArrayList<>();

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection");

        sessions.add(session);

        try {
            session.getBasicRemote().sendText(ArduinoServer.getInstance().serializeXML());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);

        String[] methodToCall = message.split(",", -1);

        for (Map.Entry<String, Client> client : ArduinoServer.getInstance().getClients().entrySet()) {
            if (client.getKey().equals(methodToCall[0])) {
                if (methodToCall[3] == null || methodToCall[3].equals("")) {
                    for (ArduinoMethod arduinoMethod : client.getValue().getArduino().getCoreMethods()) {
                        if (methodToCall[1].equals(arduinoMethod.getName())) {
                            int newValue = Integer.parseInt(methodToCall[2]);
                            arduinoMethod.setCurrentValue(newValue);
                            client.getValue().communicating(methodToCall[1], newValue);
                        }
                    }
                } else {
                    for (ArduinoMethod arduinoMethod : client.getValue().getArduino().getGroupMethods()) {
                        if (methodToCall[1].equals(arduinoMethod.getName())) {
                            int newValue = Integer.parseInt(methodToCall[2]);
                            arduinoMethod.setCurrentValue(newValue);
                            client.getValue().communicating(methodToCall[1], newValue, methodToCall[3], Integer.parseInt(methodToCall[4]));
                        }
                    }
                }
            }
        }

        for (Session session1 : sessions) {
            try {
                session1.getBasicRemote().sendText(ArduinoServer.getInstance().serializeXML());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        System.out.println("Session " +session.getId()+" has ended");
    }
}
