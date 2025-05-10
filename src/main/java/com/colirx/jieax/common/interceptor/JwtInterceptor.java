package com.colirx.jieax.common.interceptor;

import com.colirx.jieax.common.pojo.SysUser;
import com.colirx.jieax.common.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        // 无 token 则直接拦截请求
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }
        // 拿到 token，解析 token 中值，判断是否符合，不符合则拦截请求，符合条件则将数据放到当前 ThreadLocal 中且放行
        String token = authHeader.substring(7);
        // TODO: 解析 token，放到 threadLocal 中
        SysUser sysUser = new SysUser();
        sysUser.setId("TODO");
        RequestContextHolder.setUserInfo(sysUser);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContextHolder.clear();
    }
}
