package com.ghostflower.CA_System.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static String KEY = "This is a test!!";
    //生成token
    public static String JwtGen(Map claims){


        String token = JWT.create()
                .withClaim("claims",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//添加过期时间
                .sign(Algorithm.HMAC256(KEY));//指定算法


        //System.out.println(token);
        return token;

    }
    //解析token
    public static Map<String,Object> Parse(String secret,String token){
        //模拟用户Token
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();

    }
}
