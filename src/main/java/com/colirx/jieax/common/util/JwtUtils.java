package com.colirx.jieax.common.util;

import com.colirx.jieax.common.conf.JwtConfig;
import com.colirx.jieax.common.mapper.SysUserMapper;
import com.colirx.jieax.common.pojo.MacAlgorithmType;
import com.colirx.jieax.common.pojo.SysUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, String> buildSecretKey(MacAlgorithmType type) {
        Map<String, String> keys = new HashMap<>();
        switch (type) {
            case RS256 -> {
                KeyPair keyPair = Jwts.SIG.RS256.keyPair().build();
                keys.put("publicKey", Encoders.BASE64.encode(keyPair.getPrivate().getEncoded()));
                keys.put("privateKey", Encoders.BASE64.encode(keyPair.getPublic().getEncoded()));
            }
            case HS256, default -> {
                SecretKey secretKey = Jwts.SIG.HS256.key().build();
                keys.put("secretKey", Encoders.BASE64.encode(secretKey.getEncoded()));
            }
        }
        return keys;
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, jwtConfig.getRefreshTokenExpiration());
    }

    public String generateToken(String userId, long expiration) {
        return Jwts.builder()
            /*
                设置 header
                    - typ: 表明这是一个 JWT（固定为 JWT）
                    - alg: 指定用于签署JWT的算法，如 HS256（HMAC SHA-256）或 RS256（RSA SHA-256）
                typ 固定为 JWT、alg 会自动根据 KEY 填充
             */
            .header().type("JWT")
            .and()
            /*
                设置 payload
                - Registered Claims：注册声明
                    - iss（issuer）：签发JWT的实体。
                    - sub（subject）：JWT所面向的用户或主题。
                    - aud（audience）：预期接收JWT的受众。
                    - exp（expiration time）：JWT过期时间，在此时间之后JWT应被视为无效。
                    - nbf（not before）：JWT生效时间之前，不应被接受处理的时间点。
                    - iat（issued at）：JWT的创建时间。
                    - jti（JWT ID）：JWT的唯一标识符，可用于防止重放攻击。
                - Public Claims：公共声明
                - Private Claims：私有声明
             */
            .issuer("jieax-server")
            .subject("jieax")
            .audience().add("personal").and()
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .issuedAt(new Date())
            .id(UUID.randomUUID().toString().replace("-", ""))
            .claims(buildClaims(userId))
            // 给定签名
            .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    private Map<String, String> buildClaims(String userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        HashMap<String, String> claims = new HashMap<>();
        claims.put("tenantId", sysUser.getTenantId());
        claims.put("orgId", sysUser.getOrgId());
        claims.put("userId", userId);
        claims.put("role", sysUser.getRoleId());
        return claims;
    }
}
