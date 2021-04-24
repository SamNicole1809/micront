package com.micront.authorization.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MicrontUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return User.withUsername("sam").password("$2a$10$8gbZsZELhTp/kmMAgsAmmupUS.VPtKtYuuZSl.KU1O2hCT/EZWulW").authorities("p1").build();
    }
}
