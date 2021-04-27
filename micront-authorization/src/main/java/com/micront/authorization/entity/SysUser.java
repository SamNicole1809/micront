package com.micront.authorization.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigInteger;

@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = -5729547695258514721L;

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    @TableField("user_phone")
    private String userPhone;

    @TableField("user_password")
    private String userPassword;

    @TableField("refresh_token")
    private String refreshToken;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userPhone='" + userPhone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
