package com.thoughtworks.maomao.web.filter;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.web.annotation.Controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WheelFilter implements Filter {
    private Map<String, Class> servletMap = new HashMap<String, Class>();
    private WheelContainer wheelContainer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        wheelContainer = (WheelContainer) filterConfig.getServletContext().getAttribute("WHEEL_CONTAINER");
        Set<Class> controllers = wheelContainer.getAllClassWithAnnotation(Controller.class);
        for (Class controller : controllers) {
            Controller controllerAnnotation = (Controller) controller.getAnnotation(Controller.class);
            servletMap.put(controllerAnnotation.path(), controller);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Class aClass = servletMap.get(httpServletRequest.getPathInfo());
        Servlet servlet = (Servlet) wheelContainer.getWheelInstance(aClass);
        servlet.service(request, response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
