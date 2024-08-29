package top.mitrecx.dazhixianxian.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // 生成一个密钥用于签名
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 天的过期时间

    /**
     * 生成 JWT
     *
     * @param claims 包含的声明信息
     * @return 生成的 JWT 字符串
     */
    public static String encode(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static String createToken(int userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", userId);
        return encode(claims);
    }

    public static int parseToken(String token) {
        try {
            Claims claims = decode(token);
            return (int) claims.get("key");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 解码 JWT
     *
     * @param token JWT 字符串
     * @return 解码后的声明信息
     * @throws Exception 如果 JWT 无效或者过期
     */
    public static Claims decode(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        // 准备一些数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "testUser");
        claims.put("role", "admin");

        // 生成 JWT
        String jwt = JwtUtil.encode(claims);
        System.out.println("Generated JWT: " + jwt);

        try {
            // 解码 JWT
            Claims decodedClaims = JwtUtil.decode(jwt);

            System.out.println("Decoded JWT claims: " + decodedClaims);
            System.out.println(decodedClaims.get("username"));
            System.out.println(decodedClaims.get("role"));


            String token = createToken(1);
            System.out.println(token);
            System.out.println("==");
            System.out.println(decode("eyJhbGciOiJIUzI1NiJ9.eyJrZXkiOjEsImlhdCI6MTcyNDk0MDEwOCwiZXhwIjoxNzI1MDI2NTA4fQ.IViw7zpXKpwQdbeEo5Ko6RgiH_9oJEWHf1UsMTqnJjU"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}