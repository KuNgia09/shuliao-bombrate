package com.example.shuliao_bombrate;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JWTUtils {
    private String secret = "f4e2e52034348f8dd67cde581c0f9eb5f4e2e52034341f86b67cde581c0f9eb5";
    private int expire = 365;

    public String generateToken(String userId, String sid) {
        Date nowDate = new Date();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("sid", sid);
        Date expireDate = new Date(nowDate.getTime() + this.expire * 1000L * 60L * 60L * 24L);


        return Jwts.builder().setHeaderParam("type", "JWT").setHeaderParam("salt", UUID.randomUUID().toString()).setSubject(jsonObject.toJSONString()).setIssuedAt(nowDate).setExpiration(expireDate).signWith(generalKey(), SignatureAlgorithm.HS512).compact();
    }


    private Key generalKey() {
        Key key = Keys.hmacShaKeyFor(this.secret.getBytes());
        return key;
        /*     */
    }
}
