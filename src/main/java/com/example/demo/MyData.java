package com.example.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * @author wanba
 * @Description
 * @create 2021-08-03 5:41 PM
 */
@Data
public class MyData extends BaseRowModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "昵称", index = 1)
    private String nickName;

    @ExcelProperty(value = "密码", index = 2)
    private String password;

    //不支持LocalDate
    @ExcelProperty(value = "生日", index = 3, format = "yyyy/MM/dd")
    private Date birthday;
}
