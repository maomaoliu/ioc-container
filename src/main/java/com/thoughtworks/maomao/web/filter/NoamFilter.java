package com.thoughtworks.maomao.web.filter;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.web.annotation.Controller;
import com.thoughtworks.maomao.web.config.NameResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NoamFilter implements Filter {
    private Map<String, Class> servletMap = new HashMap<String, Class>();
    private Map<String, Servlet> servletInstanceMap = new HashMap<String, Servlet>();
    private WheelContainer wheelContainer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        wheelContainer = (WheelContainer) filterConfig.getServletContext().getAttribute("WHEEL_CONTAINER");
        Set<Class> controllers = wheelContainer.getAllClassWithAnnotation(Controller.class);
        for (Class controller : controllers) {
            Controller controllerAnnotation = (Controller) controller.getAnnotation(Controller.class);
            Class model = controllerAnnotation.model();
            NameResolver nameResolver = new NameResolver(model);
            servletMap.put(nameResolver.getSingularPath(), controller);
            servletMap.put(nameResolver.getPluralPath(), controller);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String pathInfo = httpServletRequest.getPathInfo();
        Servlet servlet = servletInstanceMap.get(pathInfo);
        if (servlet == null) {
            Class aClass = servletMap.get(pathInfo);
            if (aClass != null) {
                servlet = (Servlet) wheelContainer.getWheelInstance(aClass);
                servletInstanceMap.put(pathInfo, servlet);
            }
        }
        if (servlet != null) {
            servlet.service(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}