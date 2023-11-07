package com.ghostflower.CA_System.controller;

import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.DigitalEnvelope;
import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.service.CipherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    CipherService cs;
    @GetMapping("/cipher")
    public Result TestDE(HttpServletRequest request){
        DigitalEnvelope de = cs.GenerateDigitalEnvelope("this is a test plainText!!!","8IF4T29607an8Dmn");
        if(de==null){


            return Result.Error("收信人尚未申请证书！！！");

        }else{
            String plaintext = cs.DecryptDigitalEnvelope(de);
            return Result.Success(plaintext);
        }

    }
    @GetMapping("/de")
    public Result TestDE2(HttpServletRequest request) {
        DigitalEnvelope de = cs.GenerateDigitalEnvelope("this is a test plainText!!!", "8IF4T29607an8Dmn");
        if (de == null) {


            return Result.Error("收信人尚未申请证书！！！");

        } else {
            String plaintext = cs.DecryptDigitalEnvelope(de);
            return Result.Success(plaintext);
        }
    }
}

