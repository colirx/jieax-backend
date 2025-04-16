package com.colirx.jieax.common.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    RC_200("200", "success"),
    RC_204("204", "授权规则不通过,请稍后再试!"),
    RC_403("403", "无访问权限,请联系管理员授予权限"),
    RC_404("404", "404页面找不到的异常"),
    RC_500("500", "系统异常，请稍后重试");

    // 标记数据库中存储值为 code
    @EnumValue
    private final String code;
    private final String message;
}
