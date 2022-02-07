package org.sslcertificates.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WelcomeController {
    @RequestMapping("/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome", HttpStatus.OK);
    }
}
