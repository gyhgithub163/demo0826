package com.example.demo0826.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo0826.entity.User;
import com.example.demo0826.service.TokenService;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//Token服务实现类
@Service
public class TokenServiceImpl implements TokenService {
    //公共密钥
    private static final String JWTSECRET = "TJ";
    private static final long EXPIRE_TIME = 60*1000;
    //通过user对象信息获得token
    public String getToken(User user) {
        //签名发布时间
        Date issueTime=new Date();
        //设置签名过期时间  1分钟
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,1);
        Date expiresDate=nowTime.getTime();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("alg","HS256");//设置算法 为HS256
        map.put("typ","JWT");//设置类型为JWT
        String token= JWT.create().withHeader(map)
                .withClaim("id",user.getId())
                .withIssuedAt(issueTime)//设置签发时间
                .withExpiresAt(expiresDate)//设置过去时间 过期时间大于签发时间
                .sign(Algorithm.HMAC256(JWTSECRET));//用公共密钥加密
        return token;
    }
    public  Boolean verifyToken(String token) throws UnsupportedEncodingException  {
        JWTVerifier verifier =JWT.require(Algorithm.HMAC256(JWTSECRET)).build();//用公共密钥解密验证
        DecodedJWT jwt=null;
        try{
            jwt=verifier.verify(token);
        }catch (Exception e)
        {
            e.printStackTrace();
            return true;
        }
        return  false;
    }
}
