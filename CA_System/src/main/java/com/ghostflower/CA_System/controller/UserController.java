package com.ghostflower.CA_System.controller;

import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.pojo.User;
import com.ghostflower.CA_System.service.UserService;
import com.ghostflower.CA_System.utils.JwtUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//参数需要校验
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService us;


    @PostMapping("/register")
    public Result Register(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password){
        User u = us.FindUser(username);
        if(u==null){
            //说明没有该用户记录，可以注册
            us.register(username,password);
            return Result.Success();

        }else{
            return Result.Error("用户名已被占用！！！");
        }


    }
    @PostMapping("/login")
    public Result<String> Login(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password){
        User u = us.FindUser(username);
        if(u==null){
            //说明没有该用户记录
            return Result.Error("用户名不存在！！！");

        }
        if(password.equals(u.getPassWord())){

            Map<String,Object> claims = new HashMap<>();
            claims.put("uid",u.getUid());
            claims.put("username",u.getUserName());
            //登陆成功，返回token
            String token = JwtUtil.JwtGen(claims);
            return Result.Success(token);
        }
        return Result.Error("密码错误!!!!");


    }
}
