package as;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;

/*
生成验证jwt的token
@return
 */
public class jwt {
    long oneday;
    String  secret;
    String base64EncodedSecretKey;

    public jwt(long oneday, String secret, String base64EncodedSecretKey) {
        this.oneday = oneday;
        this.secret = secret;
        this.base64EncodedSecretKey = base64EncodedSecretKey;
    }

    public  String generateToken(){
        JwtBuilder builder = Jwts.builder();
        return builder
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .claim("username","tom")
                .claim("userid","123")
                .setExpiration(new Date(System.currentTimeMillis()+ oneday))
                .signWith(SignatureAlgorithm.HS256,base64EncodedSecretKey)
                .compact();
    }
    public   void parseToken(String jwt){
        try {

            JwtParser parser = Jwts.parser();
            Jws<Claims> claimsJws = parser.setSigningKey(base64EncodedSecretKey).parseClaimsJws(jwt);
            Claims claims = claimsJws.getBody();
            System.out.println(claims.get("username"));
            System.out.println(claims.getExpiration());
        }catch (JwtException e){
            System.out.println("there is a wrong!"); //handle part
        }

    }


//    public static void main(String[] args) {
//       jwt testjwt=new jwt(60*60*24*1000,"admin",TextCodec.BASE64.encode("admin".getBytes()));
//       String temp = testjwt.generateToken();
//       testjwt.parseToken(temp);
//
//
//    }

}


