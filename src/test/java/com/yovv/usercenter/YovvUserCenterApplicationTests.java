package com.yovv.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class YovvUserCenterApplicationTests {

    @Test
    void testDigest(){
        String SALT = "yovv";
        String userPassword = "123123123";
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        System.out.println(newPassword);
    }


    @Test
    void contextLoads() {
    }

}
