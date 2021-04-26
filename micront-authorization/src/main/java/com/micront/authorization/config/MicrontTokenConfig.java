package com.micront.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MicrontTokenConfig {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String username = authentication.getUserAuthentication().getName();
                System.out.println(username);
                Map<String, Object> info = new HashMap<>(16);
                info.put("user_id", "11112222");
                info.put("team_id", "22123123123");
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
                return super.enhance(accessToken, authentication);
            }
        };
        converter.setSigningKey("micront");
        return converter;
    }

}
