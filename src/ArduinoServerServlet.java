import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.ArduinoServer;
import main.Client;
import models.Arduino;

/**
 * Created by jespe on 01-03-2017.
 */
@SuppressWarnings("serial")
public class ArduinoServerServlet extends HttpServlet {
    
    private PrintWriter out = null;
    
    @Override
    public void init() throws ServletException
    {       
        /*System.out.println("We have now started the server called init method! :D");
        ArduinoServer arduinoServer = ArduinoServer.getInstance();
        arduinoServer.initializeSocket();*/
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
       // Set response content type
      response.setContentType("text/xml");
      
      Client a = new Client();
      String xmlReturned = a.serializeXML();
      out = response.getWriter();
      out.println(xmlReturned);
      out.close();
   }
}