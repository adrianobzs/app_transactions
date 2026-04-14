package com.pismo.transaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DummyController {

    @GetMapping("/hello")
    public String hello() {
        return "Application is Running! :)";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
