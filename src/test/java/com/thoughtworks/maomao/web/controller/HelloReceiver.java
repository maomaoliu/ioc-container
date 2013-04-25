package com.thoughtworks.maomao.web.controller;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.web.annotation.Controller;
import com.thoughtworks.maomao.web.service.GreetingService;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller(path = "/hi")
public class HelloReceiver extends HttpServlet{

    @Glue
    private GreetingService greetingService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = greetingService.sayHi(req.getParameter("name"));
        ST st = new ST(Files.toString(new File(getClass().getResource("/com/thoughtworks/maomao/web/view/hi.stg").getFile()), Charsets.UTF_8), '$', '$');
        st.add("message", message);
        resp.getOutputStream().write(st.render().getBytes());
    }

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public GreetingService getGreetingService() {
        return greetingService;
    }
}
