package com.ghostflower.CA_System.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class HtmlController {

    @GetMapping("/user")
    public String UserHtml(){
        return "login";
    }

    @GetMapping("/ca")
    public String CaHtml(){
        return "apply";
    }


}


