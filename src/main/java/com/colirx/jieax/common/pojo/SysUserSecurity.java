package com.colirx.jieax.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sysUserSecurity")
public class SysUserSecurity extends BaseModel {

    private String userId;
    // 默认给定 HS256
    private String algorithm = Jwts.SIG.HS256.getId();
    // 若为对称性加密，则存在此值
    private String secretKey;
    // 若为非对称性加密，则存在 publicKey、privateKey
    private String publicKey;
    private String privateKey;
}
