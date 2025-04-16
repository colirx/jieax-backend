package com.colirx.jieax.common.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.colirx.jieax.common.pojo.SysUserSecurity;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserSecurityMapper extends BaseMapper<SysUserSecurity> {

    default SysUserSecurity selectByUserId(String userId) {
        QueryWrapper<SysUserSecurity> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        return selectOne(wrapper);
    };
}
