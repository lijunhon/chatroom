package org.springboot.chatroom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springboot.chatroom.po.UserPo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component
public class TokenUtil implements Serializable {
    private static final long serialVersionUID = -3L;
    /**
     * Token 有效时长
     */
    private static final Long EXPIRATION = 604800L;
    @Value("${rsa.key.private}")
    private String PRIVATE_KEY;
    @Value("${rsa.key.public}")
    private String PUBLIC_KEY;

    /** 生成 Token 字符串 必须 setAudience 接收者 setExpiration 过期时间 role 用户角色
     * @param userPo 用户信息
     * @return 生成的Token字符串 or null
     */
    public String createToken(UserPo userPo) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Token 的过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        // 生成 Token
        String token = Jwts.builder()
                // 设置 Token 签发者 可选
                .setIssuer("springboot-chatroom")
                // 根据用户名设置 Token 的接受者
                .setAudience(userPo.getAccountNumber())
                // 设置过期时间
                .setExpiration(expirationDate)
                // 设置 Token 生成时间 可选
                .setIssuedAt(new Date())
                // 通过 claim 方法设置一个 key = role，value = userRole 的值
                .claim("role", userPo.getRole())
                // 设置加密密钥和加密算法，注意要用私钥加密且保证私钥不泄露
                .signWith(RsaUtil.getPrivateKey(PRIVATE_KEY), SignatureAlgorithm.RS256)
                //.signWith(SignatureAlgorithm.HS512,"aaaaaa")
                .compact();
        return String.format("Bearer %s", token);
    }

    /** 验证 Token ，并获取到用户名和用户权限信息
     * @param token Token 字符串
     * @return sysUser 用户信息
     */
    public UserPo validationToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 解密 Token，获取 Claims 主体
        Claims claims = Jwts.parserBuilder()
                // 设置公钥解密，以为私钥是保密的，因此 Token 只能是自己生成的，如此来验证 Token
                .setSigningKey(RsaUtil.getPublicKey(PUBLIC_KEY))
                .build().parseClaimsJws(token).getBody();
        assert claims != null;
        // 验证 Token 有没有过期 过期时间
        Date expiration = claims.getExpiration();
        // 判断是否过期 过期时间要在当前日期之后
        if (!expiration.after(new Date())) {
            return null;
        }
        UserPo userPo = new UserPo();
        userPo.setAccountNumber(claims.getAudience());
        userPo.setRole(claims.get("role").toString());
        return userPo;
    }
}
