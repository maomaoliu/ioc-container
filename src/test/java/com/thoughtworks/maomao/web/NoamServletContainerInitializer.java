package com.thoughtworks.maomao.web;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.web.annotation.Controller;
import com.thoughtworks.maomao.web.annotation.Service;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

@WebListener
public class NoamServletContainerInitializer implements ServletContextListener {

    private WheelContainer wheelContainer;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        wheelContainer = new WheelContainer("com.thoughtworks.maomao.web", new Class[]{Controller.class, Service.class});
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("WHEEL_CONTAINER", wheelContainer);
        servletContext.addFilter("noamFilter", HiFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/hi");
        servletContext.addServlet("dispatchServlet", DispatchServlet.class).addMapping("/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
