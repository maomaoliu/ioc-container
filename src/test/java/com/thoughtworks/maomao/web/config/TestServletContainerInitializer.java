package com.thoughtworks.maomao.web.config;

public class TestServletContainerInitializer extends NoamServletContainerInitializer {
    @Override
    public String getPackage() {
        return "com.thoughtworks.maomao.web";
    }
}
