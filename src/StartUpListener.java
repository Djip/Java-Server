import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import main.ArduinoServer;

public class StartUpListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Hvad sker der DEST");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
            System.err.println("Tråden skal startes");
            ArduinoServer arduinoServer = ArduinoServer.getInstance();
            Thread t = new Thread(arduinoServer);
            t.start();
            
            
            //arduinoServer.initializeSocket();
		
	}

}
