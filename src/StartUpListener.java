import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import main.ArduinoServer;
import main.Client;

public class StartUpListener implements ServletContextListener {
        Thread t;
        ArduinoServer arduinoServer;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Hvad sker der DEST");
		t = null;
                t.interrupt();
               
                
                try {
                    ArduinoServer.getInstance().getServerSocket().close();
                } catch (IOException ex) {
                    Logger.getLogger(StartUpListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for(Map.Entry<String, Client> entry : ArduinoServer.getInstance().getClients().entrySet()) {
                    String key = entry.getKey();
                    Client value = entry.getValue();
                    
                    value.getHeartbeatTimer().cancel();
                    value.getHeartbeatTimer().purge();
                    ArduinoServer.instance.getClients().remove(key);
                }
                
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
            System.err.println("Tråden skal startes");
            arduinoServer = ArduinoServer.getInstance();
            t = new Thread(arduinoServer);
            t.start();
            
            
            //arduinoServer.initializeSocket();
		
	}

}
