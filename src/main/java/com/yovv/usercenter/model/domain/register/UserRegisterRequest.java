package com.yovv.usercenter.model.domain.register;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yovvis
 * @Description 用户注册请求体
 * @date 2023/5/21 16:05
 */
@Data
public class UserRegisterRequest implements Serializable {

   private static final long serialVersionUID = -6254091480027466589L;

   private String userAccount;

   private String userPassword;

   private String checkPassword;

   private String planetCode;
}
