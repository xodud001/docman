package api.doc.docman;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.Jwts;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeFactory;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSimpleTest {

    private void printToken(String token){
        String[] tokens = token.split("\\.");
        System.out.println("header" + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body" + new String(Base64.getDecoder().decode(tokens[1])));

    }

    @DisplayName("1. jjwt를 이용한 토큰 테스트")
    @Test
    void test1(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String okta = Jwts.builder().addClaims(
                Map.of("name", "taeyeong", "birth", 1997)
        ).signWith(key, SignatureAlgorithm.HS256).compact();

        System.out.println(okta);
        printToken(okta);
        System.out.println(JWT.decode(okta).getSignature());
    }

    @DisplayName("2. java-jwt를 이용한 토큰 테스트")
    @Test
    void test2(){
        String oauth0Token = JWT.create().withClaim("name", "taeyeong").withClaim("birth", 1997)
                .sign(Algorithm.HMAC256("SECRET_KEY"));

        System.out.println(oauth0Token);
        printToken(oauth0Token);
    }

    @DisplayName("3. 만료 시간 테스트")
    @Test
    void test3() throws InterruptedException {
        final Algorithm  AL =  Algorithm.HMAC256("SECRET_KEY");
        String token = JWT.create()
                .withSubject("a1234")
                .withNotBefore(new Date(System.currentTimeMillis()+1000))
                .withExpiresAt(new Date(System.currentTimeMillis()+3000))
                .sign(AL);
//        Thread.sleep(2000);
        try {
            DecodedJWT verify = JWT.require(AL).build().verify(token);
            System.out.println(verify.getClaims());
        }catch (Exception ex){
            System.out.println("유효하지 않은 토큰입니다.");
            DecodedJWT decodedJWT = JWT.decode(token);
            System.out.println(decodedJWT.getClaims());
        }
    }
}
