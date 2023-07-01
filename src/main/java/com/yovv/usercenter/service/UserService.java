package com.yovv.usercenter.service;

import com.yovv.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yovvis
 * 用户服务
 **/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return long
     **/
    long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @return User
     **/
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    Integer userLogOut(HttpServletRequest request);

    User getSafetyUser(User orginUser);


}
