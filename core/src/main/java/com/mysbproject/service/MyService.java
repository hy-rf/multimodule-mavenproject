package com.mysbproject.service;

import com.mysbproject.dao.GetDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MyService {


    @Autowired
    GetDatabase getDatabase;

    private final ServiceProperties serviceProperties;


    public MyService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public String message() {
        getDatabase.getDriver();
        return this.serviceProperties.getMessage();
    }
}