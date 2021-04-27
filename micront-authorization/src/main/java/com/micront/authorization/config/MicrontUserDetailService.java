package com.micront.authorization.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.micront.authorization.entity.SysUser;
import com.micront.authorization.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MicrontUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserPhone, userPhone));
        return User.withUsername(user.getUserPhone()).password(user.getUserPassword()).authorities("p1").build();
    }

}
