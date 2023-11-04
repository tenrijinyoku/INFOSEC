package com.ghostflower.CA_System.controller;

import com.ghostflower.CA_System.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerifyController {
    @GetMapping("/verify")
    public Result<String> list(){

        return Result.Success("所有的数据。。。。。");


    }
}

