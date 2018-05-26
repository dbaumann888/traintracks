package traintracks.server;

import traintracks.api.Game;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener on the servlet context -- global to all servlets for the app
 * During initialization sets up the connections for the different endpoints.
 */
public class TTServletContextListener implements ServletContextListener {

    private Game game;

    public TTServletContextListener(Game game) {
        this.game = game;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("game", this.game);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
