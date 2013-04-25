package com.thoughtworks.maomao.web.service;

import com.thoughtworks.maomao.web.annotation.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHi(String name) {
        return "Nice to meet you, " + name;
    }
}
