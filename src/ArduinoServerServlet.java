import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import classes.main.ArduinoServer;

/**
 * Created by jespe on 01-03-2017.
 */
@SuppressWarnings("serial")
public class ArduinoServerServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException
    {       
        System.out.println("We have now started the server called init method! :D");
        ArduinoServer arduinoServer = ArduinoServer.getInstance();
        arduinoServer.initializeSocket();
    }
}