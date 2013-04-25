package com.thoughtworks.maomao.web;

import com.thoughtworks.maomao.web.service.GreetingServiceImpl;
import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.ContentExchange;
import org.junit.Test;

import javax.servlet.ServletContextListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SmokeTest extends AbstractWebTest {
    @Override
    protected ServletContextListener getServletContainerInitializer() {
        return new NoamServletContainerInitializer();
    }

    @Test
    public void should_get_hi_message() throws Exception {
        Address localhost = new Address("localhost", 8080);

        ContentExchange contentExchange = new ContentExchange(true);
        contentExchange.setAddress(localhost);
        contentExchange.setRequestURI("/hi?name=ryan");
        client.send(contentExchange);

        contentExchange.waitForDone();
        assertEquals(200, contentExchange.getResponseStatus());
        assertNotNull(contentExchange.getResponseContent());
        assertTrue(contentExchange.getResponseContent().contains(new GreetingServiceImpl().sayHi("ryan")));
    }
}
