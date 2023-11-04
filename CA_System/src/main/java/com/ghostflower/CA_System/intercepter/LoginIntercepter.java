package com.ghostflower.CA_System.intercepter;

import com.ghostflower.CA_System.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claims = JwtUtil.Parse("This is a test!!",token);//密钥已设定好了
            return true;
        } catch(Exception e){

            response.setStatus(401);
            return false;


        }

    }
}
