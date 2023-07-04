package com.yovv.usercenter.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 星球用户实体
 *
 * @author yovvis
 */
@Data
@EqualsAndHashCode
public class XingQiuTableUserInfo {
    /**
     * 星球编号
     */
    @ExcelProperty("成员编号")
    private String planetCode;

    /**
     * 成员昵称
     */
    @ExcelProperty("成员昵称")
    private String username;
}
