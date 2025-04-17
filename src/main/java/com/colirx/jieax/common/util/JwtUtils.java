package com.colirx.jieax.common.util;

import com.colirx.jieax.common.conf.JwtConfig;
import com.colirx.jieax.common.mapper.SysUserMapper;
import com.colirx.jieax.common.mapper.SysUserSecurityMapper;
import com.colirx.jieax.common.pojo.SysUser;
import com.colirx.jieax.common.pojo.SysUserSecurity;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.PrivateKeyBuilder;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

@Component
public class JwtUtils {

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserSecurityMapper sysUserSecurityMapper;

    /**
     * 对称性加密密钥
     */
    public SecretKey generateSecretKey(MacAlgorithm algorithm) {
        return algorithm.key().build();
    }

    /**
     * 非对称性加密密钥，使用 keyPair.getPublic()、keyPair.getPrivate() 获得公钥和私钥
     */
    public KeyPair generateKeyPair(SignatureAlgorithm algorithm) {
        return algorithm.keyPair().build();
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, jwtConfig.getRefreshTokenExpiration());
    }

    public String generateToken(String userId, long expiration) {
        SysUserSecurity sysUserSecurity = sysUserSecurityMapper.selectByUserId(userId);
        SysUser sysUser = sysUserMapper.selectById(userId);
        /*
            设置 header
                - typ: 表明这是一个 JWT（固定为 JWT）
                - alg: 指定用于签署JWT的算法，如 HS256（HMAC SHA-256）或 RS256（RSA SHA-256）
         */
        String algorithm = sysUserSecurity.getAlgorithm();
        JwtBuilder builder = Jwts.builder().header().add(buildHeader(algorithm)).and();
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
        builder = builder
            .issuer(sysUser.getTenantId())
            .subject("token")
            .audience().add(userId).and()
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .id(UUID.randomUUID().toString().replace("-", ""));
        builder.claims(buildPrivateClaims(sysUser));
        /*
            给定签名
         */
        switch (algorithm) {
            case "RS256":
                break;
            case "HS256":
                // TODO:
                builder = builder.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, sysUserSecurity.getSecretKey());
                builder = builder.signWith(Jwts.SIG.HS256.key().build(), );
                break;
            default:
                break;
        }
        return builder.compact();
    }

    private Map<String, String> buildHeader(String alg) {
        Map<String, String> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", alg);
        return header;
    }

    private Map<String, String> buildPrivateClaims(SysUser sysUser) {
        Map<String, String> claims = new HashMap<>();
        claims.put("userId", sysUser.getId());
        claims.put("tenantId", sysUser.getTenantId());
        claims.put("orgId", sysUser.getOrgId());
        return claims;
    }
}
