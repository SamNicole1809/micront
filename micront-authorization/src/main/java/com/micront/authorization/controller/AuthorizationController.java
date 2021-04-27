package com.micront.authorization.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;

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
        long exp = job.getLong("exp") - System.currentTimeMillis() / 1000;
        System.out.println(jti + " - " + exp);
        redisTemplate.opsForValue().set(jti, "", exp, TimeUnit.SECONDS);
        return claims;
    }

    @GetMapping("/token")
    public String getToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX2lkIjoyLCJ1c2VyX25hbWUiOiIxODMwMDAwMDAwMCIsInNjb3BlIjpbImFkbWluIl0sImV4cCI6MTYxOTQyODIxMywiYXV0aG9yaXRpZXMiOlsicDEiXSwianRpIjoiYzM2YzFiYmYtOWUxOC00ZjNkLWE0MjUtMWQ4ZGY2MWJkZGIzIiwiY2xpZW50X2lkIjoiYzEifQ.GYDCYmuOOQ-9o0QpEIZFEeUOHlFmFR87OBU3KFEz4yc";
        Jwt jwt = JwtHelper.decode(token);
        JSONObject job = JSON.parseObject(jwt.getClaims());
        return job.toJSONString();
    }
}
