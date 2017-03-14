import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartUpListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Hvad sker der DEST");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Hvad sker der INI");
		
	}

}
