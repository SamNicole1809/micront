package com.micront.printing.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/p/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {
        return "OK";
    }

}
