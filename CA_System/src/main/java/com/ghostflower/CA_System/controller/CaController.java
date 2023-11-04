package com.ghostflower.CA_System.controller;

import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.service.CaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ca")
public class CaController {
    @Autowired
    private CaService cs;
    @GetMapping("/apply")
    public Result Apply(String username, String password, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);

        if(ca==null){
            //说明没有该证书记录，可以注册
            cs.Apply(token);
            return Result.Success();

        }else{
            return Result.Error("用户证书已存在！！！");
        }


    }

}