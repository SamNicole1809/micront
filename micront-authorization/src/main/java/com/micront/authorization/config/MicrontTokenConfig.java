package com.micront.authorization.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.micront.authorization.entity.SysUser;
import com.micront.authorization.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysUserMapper userMapper;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userPhone = authentication.getUserAuthentication().getName();
                SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserPhone, userPhone));
                Map<String, Object> info = new HashMap<>(16);
                info.put("user_id", user.getId());
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
                return super.enhance(accessToken, authentication);
            }
        };
        converter.setSigningKey("micront");
        return converter;
    }

}
