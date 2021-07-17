package api.doc.docman.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;

public class JWTUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("secret");
    private static final long AUTH_TIME = 20*60;
    private static final long REFRESH_TIME = 7*24*60*60;

    public static String makeAuthToken(String user){
        return JWT.create()
                .withSubject(user)
                .withClaim("exp", Instant.now().getEpochSecond()+AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(String user){
        return JWT.create()
                .withSubject(user)
                .withClaim("exp", Instant.now().getEpochSecond()+REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token){
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder()
                    .success(true)
                    .username(verify.getSubject())
                    .build();
        }catch (Exception ex){
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder()
                    .success(false)
                    .username(decode.getSubject())
                    .build();
        }
    }
}
