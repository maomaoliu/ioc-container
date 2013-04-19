package com.thoughtworks.maomao.web;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.After;
import org.junit.Before;

import javax.servlet.ServletContextListener;

public abstract class AbstractWebTest {

    private Server server;
    HttpClient client;

    @Before
    public void startServer() throws Exception {
        server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addEventListener(getServletContainerInitializer());
        server.setHandler(context);
        server.start();

        QueuedThreadPool executor = new QueuedThreadPool();
        executor.setName(executor.getName() + "-client");
        client = new HttpClient();
        client.setThreadPool(executor);
        client.start();
    }

    @After
    public void stopServer() throws Exception {
        if (client != null)
            client.stop();
        if (server != null)
            server.stop();
        server = null;
    }

    protected abstract ServletContextListener getServletContainerInitializer();
}
