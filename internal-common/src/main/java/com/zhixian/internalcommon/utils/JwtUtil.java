package com.zhixian.internalcommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Slf4j

public final class JwtUtil {

    /**
     * 服务器上存的私钥
     */
    private static String SECRET ="sdsdasd_d_sd+WDsdasd";

    /**
     * jwt生成token
     * @param subject
     * @param date
     * @return
     */
    public static String createToken(String subject, Date date){
        return Jwts.builder()
                .setSubject(subject)
                .setId("token")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 5 * 60 * 10000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    public static JwtInfo parseToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if (!Objects.isNull(claims)){
                return JwtInfo.builder()
                        .subject(claims.getSubject())
                        .issueDate(claims.getIssuedAt().getTime())
                        .build();
            }

        }catch (Exception e){
            log.error("jwt Token过期了" + e);
        }
        return null;

    }

    public static void main(String[] args) {
        String expireToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VybmFtZVwiOlwia2V2aW5cIn0iLCJqdGkiOiJ0b2tlbiIsImlhdCI6MTUxNjIzOSwiZXhwIjoxNTE5MjM5fQ.GQxi-1RZBZmnm0GOd1d7fcG9V6w_aE6wQgnW3S124_hNlNIIqJ8ZUBxaWc1DJjRvesG-Q2Z7uhTz7BNS1ByNow";
        String token = createToken("{\"username\":\"kevin\"}", new Date());
        System.out.println("generate token: " + token);
        System.out.println("parse token: " + parseToken(token));


    }
}
