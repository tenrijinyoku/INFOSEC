package com.ghostflower.CA_System;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {

    public String JwtGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        String token = JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//添加过期时间
                .sign(Algorithm.HMAC256("This is a test!!"));//指定算法


        //System.out.println(token);
        return token;

    }
    @Test
    public void Parse(){



        //模拟用户Token
        String token = JwtGen();
        JWTVerifier jwtv = JWT.require(Algorithm.HMAC256("This is a test!!")).build();//生成请求处理器（输入密钥）
        DecodedJWT Djwt = jwtv.verify(token);//解析token，生成解析后的对象，成功则说明正确
        Map<String, Claim> claims = Djwt.getClaims();
        System.out.println(claims.get("user"));
    }
}
