package com.example.Comp2005_a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    @GetMapping("/")
    String index(){
        return "Hello there!!";
    }




}