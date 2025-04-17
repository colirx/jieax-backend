package com.colirx.jieax.common.interceptor;

import com.colirx.jieax.common.pojo.SysUser;

public class RequestContextHolder {
    private static final ThreadLocal<SysUser> userInfo = new ThreadLocal<>();

    public static void setUserInfo(SysUser sysUser) {
        userInfo.set(sysUser);
    }

    public static SysUser getUserInfo() {
        return userInfo.get();
    }

    public static void clear() {
        userInfo.remove();
    }
}
