package com.colirx.jieax.common.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.colirx.jieax.common.interceptor.RequestContextHolder;
import com.colirx.jieax.common.mapper.SysUserMapper;
import com.colirx.jieax.common.pojo.SysUser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MybatisPlus 对于多租户场景的拦截器
 */
@Component
public class PojoTenantHandler implements TenantLineHandler {

    @Override
    public Expression getTenantId() {
        SysUser userInfo = RequestContextHolder.getUserInfo();
        return new StringValue(userInfo.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return "tenantId";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 根据需求返回是否需要忽略此表
        return false;
    }
}
