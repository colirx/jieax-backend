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
@TableName("sysOrganization")
public class SysOrganization extends BaseModel {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
}
