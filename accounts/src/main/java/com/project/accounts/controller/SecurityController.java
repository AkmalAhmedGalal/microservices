package com.project.accounts.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SecurityController {


    @GetMapping("/get-welcome-message")
    public ResponseEntity<String> getResponse(){
        return new ResponseEntity<>("Hello From Security Controller! ", HttpStatus.OK);
    }

    @PostMapping("/create-welcome-message")
    public ResponseEntity<String> craeteCustomeWelcomeMessage(@RequestBody String message){
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
