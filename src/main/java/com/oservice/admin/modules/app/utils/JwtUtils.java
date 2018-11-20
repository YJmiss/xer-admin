package com.oservice.admin.modules.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.modules.app.service.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author LingDu
 * @version 1.0
 */
@ConfigurationProperties(prefix = "os-admin.jwt")
@Component
public class JwtUtils {
    @Resource
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(String userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")//header
                .setSubject(userId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * 生成密钥
     *
     * @return:密钥
     */
    private static Key getKey() {
        //HS256加密
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(ConfigConstant.SECRET_KEY);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**
     * 生成用户令牌
     *
     * @return:密钥
     */
    public String priduceUserToken(String userid, String behave) {
        //生成token
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userid", userid)
                .claim("iss", "JYmiss")
                .claim("sub", "yi_chenyi@aliyun.com")
                .signWith(SignatureAlgorithm.HS256, getKey());
        //设置过期时间
        if (ConfigConstant.EXPSECOND >= 0) {
            Long now = System.currentTimeMillis();
            Long expMills = now + ConfigConstant.EXPSECOND;
            System.out.println("到期时间：" + new Date(expMills));
            Date expDate = new Date(expMills);
            builder.setExpiration(expDate).setNotBefore(new Date());
        }
        String userToken = builder.compact();
        String sign = userToken.split("\\.")[2];
        userService.insertSignByid(userid, sign);
        //在一定时间内刷新令牌
        Long now = System.currentTimeMillis();
        Long expMills = now + ConfigConstant.REFRESHSECOND;
        //将token刷新时间保存在数据库中
        String refreshtime = userService.queryReTokenBySign(sign);
        if (refreshtime == null || refreshtime.equals("")) {
            userService.insertReTokByid(userid, String.valueOf(expMills));
        } else {
            userService.updataReTokByid(userid, String.valueOf(expMills));
        }
        return userToken;
    }

    /**
     * 验证Token
     *
     * @return:Token
     */
    public String Vaild(String token) throws JsonProcessingException {
        String sign = null;
        try {
            System.out.println("token校验：" + token);
            //获得签名
            sign = token.split("\\.")[2];
            //获得载荷
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
            ObjectMapper mapper = new ObjectMapper();
            //将载荷转化成字符串
            String payload = mapper.writeValueAsString(jwtClaims);
            //重新生成签名
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "jwt")
                    .setPayload(payload)
                    .signWith(SignatureAlgorithm.ES256, getKey());
            String newsign = builder.compact().split("\\.")[2];//新签名
            //新签名和旧签名对比
            if (!sign.equals(newsign)) {
                return null;
            }
        } catch (ExpiredJwtException e) { //捕获token 过期
            e.printStackTrace();
            System.out.println("token过期");
            String newtoken = reFreshToken(sign);
            return newtoken;
        } catch (SignatureException | MalformedJwtException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("token校验正确");
        return token;
    }

    /**
     * 刷新Token
     *
     * @return:Token
     */
    public String reFreshToken(String sign) throws JsonProcessingException {
        //获得token的刷新时间
        String refreshtime = userService.queryReTokenBySign(sign);
        //没有过期
        String token = null;
        if (String.valueOf(System.currentTimeMillis()).compareTo(refreshtime) < 0) {
            System.out.println("重新生成token");
            token = priduceUserToken(sign, "refresh");
        } else {
            userService.deleteTokenBySign(sign);
        }
        return token;
    }

}
