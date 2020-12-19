package cn.edu.xmu.oomall.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * JWT工具
 * @author Ming Qiu
 * @date created in 2019/11/11
 *      modifiedBy Ming Qiu
 *         增加departId 到createToken
 */
public class JwtHelper {

    private Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    // 秘钥
    static final String SECRET = "Role-Privilege-Token";
    // 签名是有谁生成
    static final String ISSUSER = "OOAD";
    // 签名的主题
    static final String SUBJECT = "this is a token";
    // 签名的观众
    static final String AUDIENCE = "MINIAPP";
    // Request中的变量名
    public static final String LOGIN_TOKEN_KEY = "authorization";

    public static class UserAndDepart{
        private Long userId;
        private Long departId;

        public UserAndDepart(long userId, long departId){
            this.userId = userId;
            this.departId = departId;
        }

        public Long getUserId() {
            return userId;
        }

        public Long getDepartId() {
            return departId;
        }
    }

    /**
     * 获得UserId和DepartId
     * @param token
     * @return UserAndDepart
     *    modifiedBy Ming Qiu 2020/11/3 23:09
     */
    public UserAndDepart verifyTokenAndGetClaims(String token) {
        if (token == null || token.isEmpty()) {
            logger.error("token == null || token.isEmpty(): " + token);
            return null;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUSER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();

            logger.debug(claims.toString());

            Claim claimUserId = claims.get("userId");
            Claim claimDepartId = claims.get("departId");
            return new UserAndDepart(claimUserId.asLong(), claimDepartId.asLong());
        } catch (JWTVerificationException exception) {
            logger.error(exception.getMessage());
            return null;
        }
    }
}
