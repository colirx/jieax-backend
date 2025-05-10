package com.colirx.jieax;

import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class JieaxBackendApplicationTests {



    @Test
    void compactJws() {
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        System.out.println(Encoders.BASE64.encode(secretKey.getEncoded()));

        String jws = Jwts.builder()
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
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
            .issuedAt(new Date())
            .id(UUID.randomUUID().toString().replace("-", ""))
            .claim("tenantId", "tenantId")
            .claim("orgId", "orgId")
            .claim("userId", "userId")
            // 给定签名
            // .signWith(secretKey)
            .signWith(secretKey)
            .compact();
        System.out.println(jws);

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Jws<Claims> claims = jwtParser.parseSignedClaims(jws);
        System.out.println(claims.getHeader());
        System.out.println(claims.getPayload());
    }

    @Test
    void compactJws2() {
        KeyPair keyPair = Jwts.SIG.RS256.keyPair().build();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String jws = Jwts.builder()
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
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
            .issuedAt(new Date())
            .id(UUID.randomUUID().toString().replace("-", ""))
            .claim("tenantId", "tenantId")
            .claim("orgId", "orgId")
            .claim("userId", "userId")
            // 给定签名
            // .signWith(secretKey)
            .compact();
        System.out.println(jws);

        // JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        // Jws<Claims> claims = jwtParser.parseSignedClaims(jws);
        // System.out.println(claims.getHeader());
        // System.out.println(claims.getPayload());
    }
}
