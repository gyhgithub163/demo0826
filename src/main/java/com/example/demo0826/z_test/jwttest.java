package com.example.demo0826.z_test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwttest {

    //公共密钥客户端不会知道
    public static String SECRET="FreeMaNong";

    public static  String  createToken() throws UnsupportedEncodingException {
        //签名发布时间
        Date iatDate=new Date();
        System.out.println(iatDate);//英文时间

        //设置签名过期时间  1分钟
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,1);
        Date expiresDate=nowTime.getTime();
        //System.out.println(expiresDate);

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("alg","HS256");//设置算法 为HS256
        map.put("typ","JWT");//设置类型为JWT
        String token= JWT.create().withHeader(map)
                .withClaim("name","Free码农")
                .withClaim("age","28")
                .withClaim("org","今日头条")
                .withClaim("username","chenyu")
                .withIssuedAt(iatDate)//设置签发时间
                .withExpiresAt(expiresDate)//设置过去时间 过期时间大于签发时间
                .sign(Algorithm.HMAC256(SECRET));//用公共密钥加密
        //System.out.println(token);
        return token;
    }

    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier =JWT.require(Algorithm.HMAC256(SECRET)).build();//用公共密钥解密验证
        DecodedJWT jwt=null;
        try{
            jwt=verifier.verify(token);
        }catch (Exception e)
        {
            throw new RuntimeException("登录凭证已过去，请重新登录");
        }
        return jwt.getClaims();
    }



    public void TestToken() throws UnsupportedEncodingException {
        String token=createToken();
        System.out.println("Token:"+token);
        Map<String,Claim> claims=verifyToken(token);
        System.out.println(claims.get("name").asString());
        System.out.println(claims.get("age").asString());
        System.out.println(claims.get("username").asString());
        System.out.println(claims.get("org")==null?null:claims.get("org").asString());

        //测试过期token
//        String GuoQiToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.izVguZPRsBQ5Rqw6dhMvcIwy8_9lQnrO3vpxGwPCuzs";
//        Map<String,Claim> claims2=verifyToken(GuoQiToken);
    }



    public void Test() throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create().withIssuer("auth0") .sign(algorithm);
        System.out.println(token);
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        String token=createToken();
        System.out.println("Token:"+token);
        Map<String,Claim> claims=verifyToken(token);
        System.out.println(claims.get("name").asString());
        System.out.println(claims.get("age").asString());
        System.out.println(claims.get("username").asString());
        System.out.println(claims.get("org")==null?null:claims.get("org").asString());

        //测试过期token
       String GuoQiToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmciOiLku4rml6XlpLTmnaEiLCJuYW1lIjoiRnJlZeeggeWGnCIsImV4cCI6MTU2ODExMjAzMSwiaWF0IjoxNTY4MTExOTcwLCJhZ2UiOiIyOCIsInVzZXJuYW1lIjoiY2hlbnl1In0.CmPBIZf4mITA_wnRcCL_D0PPdWYDg_N2PPrIL2kK2Zk";
       Map<String,Claim> claims2=verifyToken(GuoQiToken);
    }
}
