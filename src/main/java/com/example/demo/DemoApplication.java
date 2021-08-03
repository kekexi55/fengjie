package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private YmlListValueConfig ymlListValueConfig;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
