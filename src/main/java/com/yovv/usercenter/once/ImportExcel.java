package com.yovv.usercenter.once;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * 导入excel
 *
 * @author yovvis
 */
@Slf4j
public class ImportExcel {
    public static void main(String[] args) {
        String fileName = "D:\\combat\\user-center-backend\\src\\main\\resources\\testexcel.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
//        readByListener(fileName);
        synchronousRead(fileName);

    }

    /**
     * 通过监听器读取excel
     *
     * @param fileName
     */
    public static void readByListener(String fileName) {
//        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new TableListener(dataList -> {
//            for (XingQiuTableUserInfo demoData : dataList) {
//                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
//            }
//        })).sheet().doRead();
        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new TableListener()).sheet().doRead();

    }

    /**
     * 同步进内存全读取excel
     *
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        List<XingQiuTableUserInfo> tableUserList =
                EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        for (XingQiuTableUserInfo userInfo : tableUserList) {
            log.info(userInfo.getUsername() + userInfo.getPlanetCode());
        }
    }
}
