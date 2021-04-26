package com.micront.authorization.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @DeleteMapping("/token")
    public String revokeToken(@RequestHeader("Authorization") String token) {
        Jwt jwt = JwtHelper.decode(token.substring(7));
        String claims = jwt.getClaims();
        JSONObject job = JSON.parseObject(claims);
        String jti = job.getString("jti");
        long exp = job.getLong("exp")  - System.currentTimeMillis() / 1000;
        System.out.println(jti + " - " + exp);
        redisTemplate.opsForValue().set(jti, "", exp, TimeUnit.SECONDS);
        return claims;
    }

}
