package com.yovv.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@MapperScan("com.yovv.usercenter.mapper")
public class YovvUserCenterApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication application = new SpringApplication(YovvUserCenterApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        ConfigurableApplicationContext app = application.run(args);
        log.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ");
        Environment env = app.getEnvironment();
        // ip
        String ip = InetAddress.getLocalHost().getHostAddress();
        // port
        String port = env.getProperty("server.port");
        // path
        String path = env.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)){
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
                "External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
                "----------------------------------------------------------");
    }

}
