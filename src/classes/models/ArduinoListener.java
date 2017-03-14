package classes.models;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael Skjarrlund
 */
public class ArduinoListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("we are inside contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         System.out.println("we are inside contextDestroyed");
    }
    
}
