package com.yovv.usercenter.model.domain.register;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yovvis
 * @Description 用户登录请求体
 * @date 2023/5/21 16:05
 */
@Data
public class UserLoginRequest implements Serializable {

   private static final long serialVersionUID = 298636590679889535L;

   private String userAccount;

   private String userPassword;

}
