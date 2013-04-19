package com.thoughtworks.maomao.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

//        WebAppContext webAppContext = new WebAppContext();
//        webAppContext.setServer(server);
//        webAppContext.setContextPath("/");
//        webAppContext.setWar("/");
//        webAppContext.addEventListener(new NoamServletContainerInitializer());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addEventListener(new NoamServletContainerInitializer());
        server.setHandler(context);
//        server.setHandler(webAppContext);
        server.start();
//        server.join();
    }
}
