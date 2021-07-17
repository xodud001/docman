package api.doc.docman.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    @DisplayName("1. Create Default DelegatingPasswordEncoder")
    @Test
    void test1(){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @DisplayName("2. Create Custom DelegatingPasswordEncoder")
    @Test
    void test2() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoderWithBcrypt = new DelegatingPasswordEncoder(idForEncode, encoders);
        System.out.println(passwordEncoderWithBcrypt.encode("RAW-PASSWORD"));
        // result : {bcrypt}$2a$10$69xh.rHzxPzQpy/B4nY.i.o5vM4oUK7Q3lumLTZ5upIxNn0Y1to.m

        PasswordEncoder passwordEncoderWithNoop = new DelegatingPasswordEncoder("noop", encoders);
        System.out.println(passwordEncoderWithNoop.encode("RAW-PASSWORD"));
        // result : {noop}RAW-PASSWORD

        PasswordEncoder passwordEncoderWithPbkdf2 = new DelegatingPasswordEncoder("pbkdf2", encoders);
        System.out.println(passwordEncoderWithPbkdf2.encode("RAW-PASSWORD"));
        // result : {pbkdf2}90bfbbcbcb8425eb3ebf4059f73ede6bc086a6f4b51f87ff053ba952df43a735fc98607e75034a88

        PasswordEncoder passwordEncoderWithScrypt = new DelegatingPasswordEncoder("scrypt", encoders);
        System.out.println(passwordEncoderWithScrypt.encode("RAW-PASSWORD"));
        // result : {scrypt}$e0801$00D9CR1ttpebisj/nuZsC3TRTtXdGWLHYWw1X9EY9jpZI9GZ1gR2Kc8ITJ/AFtOKIWRjNwWEFch1OHf6OY8jng==$vIoz8W8OBmpx0ZIzREfj2k799i+cjmd/MBgFhKyVOuw=

        PasswordEncoder passwordEncoderWithSha256 = new DelegatingPasswordEncoder("sha256", encoders);
        System.out.println(passwordEncoderWithSha256.encode("RAW-PASSWORD"));
        // result : {sha256}6c9741b719eade0b5d813f4fbd66b839f9f43f748ad82639af15f28f4e6034dbd0b8177eb5cf32ca
    }
    @DisplayName("3. Password Matching")
    @Test
    void test3() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        String encodedPassword = passwordEncoder.encode("RAW-PASSWORD");
        assertTrue(passwordEncoder.matches("RAW-PASSWORD", encodedPassword));
    }

    @DisplayName("4. withDefaultPasswordEncoder Example")
    @Test
    void test4(){
        User user = (User) User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("user")
                .build();
        System.out.println(user.getPassword());
    }

    @DisplayName("5. withDefaultPasswordEncoder Reusing the Builder")
    @Test
    void test5(){
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        User user = (User) users
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        User admin = (User) users
                .username("admin")
                .password("password")
                .roles("USER","ADMIN")
                .build();

        System.out.println(user.getPassword());
        System.out.println(admin.getPassword());
    }

    @DisplayName("6. BCryptPasswordEncoder")
    @Test
    void test6(){
        // Create an encoder with strength 16
        long start = System.currentTimeMillis();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
        String result = encoder.encode("RAW-PASSWORD");
        System.out.println((System.currentTimeMillis() - start)/(double)1000+"s");
        assertTrue(encoder.matches("RAW-PASSWORD", result));
    }

    @DisplayName("7. Argon2PasswordEncoder")
    @Test
    void test7(){
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        String result = encoder.encode("RAW-PASSWORD");
        assertTrue(encoder.matches("RAW-PASSWORD", result));
    }

    @DisplayName("8. Pbkdf2PasswordEncoder")
    @Test
    void test8(){
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        String result = encoder.encode("RAW-PASSWORD");
        assertTrue(encoder.matches("RAW-PASSWORD", result));
    }

    @DisplayName("9. SCryptPasswordEncoder")
    @Test
    void test9(){
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
        String result = encoder.encode("RAW-PASSWORD");
        assertTrue(encoder.matches("RAW-PASSWORD", result));
    }

    @DisplayName("10. WorkFactor")
    @Test
    void test10(){
        // BCryptPasswordEncoder의 strength를 설정하여 인코딩에 걸리는 시간을 1초에 가깝도록 조절
        long start = System.currentTimeMillis();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
        String result = encoder.encode("RAW-PASSWORD");
        System.out.println((System.currentTimeMillis() - start)/(double)1000+"s");
        assertTrue(encoder.matches("RAW-PASSWORD", result));
    }

    @DisplayName("11. Generate salt for encoding")
    @Test
    void test11(){
        String salt = BCrypt.gensalt(
                String.valueOf(BCryptPasswordEncoder.BCryptVersion.$2B).toLowerCase(Locale.ROOT)
                , 15
                , new SecureRandom());
        System.out.println(salt);
    }

    private String getSalt(){
        return BCrypt.gensalt(
                String.valueOf(BCryptPasswordEncoder.BCryptVersion.$2B).toLowerCase(Locale.ROOT)
                , 15
                , new SecureRandom());
    }
    @DisplayName("12. Generate encoded password")
    @Test
    void test12(){
        String salt = getSalt();
        String password = "RAW-PASSWORD";
        byte[] passwordb = password.getBytes(StandardCharsets.UTF_8);
        String encodedPassword = BCrypt.hashpw(passwordb, salt);
        System.out.println(encodedPassword);
    }
}
