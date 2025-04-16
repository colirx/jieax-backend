package com.colirx.jieax.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sysUser")
public class SysUser extends BaseModel {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String tenantId;
    private String userCode;
    private String userName;
    private String password;
    private String roleId;
    private String orgId;
    private String mobile;
    private String email;
}
