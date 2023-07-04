package com.yovv.usercenter.once;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 导入星球用户进数据库
 *
 * @author yovvis
 */
@Slf4j
public class ImportXingQiuUser {
    public static void main(String[] args) {
        String fileName = "D:\\combat\\user-center-backend\\src\\main\\resources\\testexcel.xlsx";
        List<XingQiuTableUserInfo> tableUserList =
                EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        for (XingQiuTableUserInfo userInfo : tableUserList) {
            log.info(userInfo.getUsername() + userInfo.getPlanetCode());
        }
    }
}
