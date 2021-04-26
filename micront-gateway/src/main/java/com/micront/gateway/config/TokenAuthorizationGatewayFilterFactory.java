package com.micront.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenAuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenAuthorizationGatewayFilterFactory.Config> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public TokenAuthorizationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("auth");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.getAuth() == 0) {
                return chain.filter(exchange);
            }
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null) {
                return chain.filter(exchange);
            }
            Jwt jwt = JwtHelper.decode(token.substring(7));
            String claims = jwt.getClaims();
            JSONObject job = JSON.parseObject(claims);
            String jti = job.getString("jti");
            Boolean isQuited = redisTemplate.hasKey(jti);
            if (isQuited != null && isQuited) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.getHeaders().set("Access-Control-Allow-Origin", "*");
                response.getHeaders().set("Cache-Control", "no-cache");
                Map<String, Object> result = new HashMap<>(16);
                result.put("code", 403);
                result.put("message", "Invalid Token");
                DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private Integer auth;

        public Integer getAuth() {
            return auth;
        }

        public void setAuth(Integer auth) {
            this.auth = auth;
        }
    }

}
