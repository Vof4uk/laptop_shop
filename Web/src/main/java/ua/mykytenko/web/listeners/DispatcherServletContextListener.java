package ua.mykytenko.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DispatcherServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String realPath = sce.getServletContext().getRealPath("");
        sce.getServletContext().setAttribute("war_path", realPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
