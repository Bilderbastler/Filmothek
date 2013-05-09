/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eventhandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import model.FilmothekModel;

/**
 * Web application lifecycle listener.
 * @author neumeister
 */
public class ContextHandler implements ServletContextListener {

    /**
     * Erzeugt das Datenmodel inklusive Datenbank-Anbindung beim Start der
     * Webapplication
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        FilmothekModel database = new FilmothekModel();
        sce.getServletContext().setAttribute("database", database);
    }

    /**
     * trennt die Datenbank-Verbindung beim Beenden der
     * Webapplication
     * 
     * @param sce
     */
    public void contextDestroyed(ServletContextEvent sce) {
        FilmothekModel database = (FilmothekModel) sce.getServletContext().getAttribute("database");
        database.shutdown();
    }
}
