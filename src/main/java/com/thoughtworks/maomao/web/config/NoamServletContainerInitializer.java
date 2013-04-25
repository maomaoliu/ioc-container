package com.thoughtworks.maomao.web.config;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.web.annotation.Controller;
import com.thoughtworks.maomao.web.annotation.Service;
import com.thoughtworks.maomao.web.filter.WheelFilter;
import com.thoughtworks.maomao.web.servlet.DispatchServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

@WebListener
public abstract class NoamServletContainerInitializer implements ServletContextListener {

    private WheelContainer wheelContainer;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        wheelContainer = new WheelContainer(getPackage(), new Class[]{Controller.class, Service.class});
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("WHEEL_CONTAINER", wheelContainer);
        servletContext.addFilter("wheelFilter", WheelFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
        servletContext.addServlet("dispatchServlet", DispatchServlet.class).addMapping("/*");
    }

    public abstract String getPackage();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
