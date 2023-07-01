package com.yovv.usercenter.service;

import com.yovv.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;


@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void addUserTest() {
        User user = new User();
        user.setUsername("yovvis");
        user.setUserAccount("yovv");
        user.setAvatarUrl("https://crulu.oss-cn-shanghai.aliyuncs.com/2023/01/31/adf5a0ba266f4de794962c738ea7ca66.jpg");
        user.setGender(0);
        user.setUserPassword("123123");
        user.setPhone("1381437xxxx");
        user.setEmail("meetxxx@163.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setOperateTime(new Date());
        user.setIsDelete(0);
        boolean result = userService.save(user);
        Assert.assertTrue(result);
    }


    @Test
    void userRegister() {
        String userAccount = "yovvis";
        String userPassword = "123123";
        String checkPassword = "123123";
        String planetCode = "22369";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }
}
