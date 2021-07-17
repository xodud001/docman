package api.doc.docman;


import api.doc.docman.Service.UserService;
import api.doc.docman.domain.User;
import api.doc.docman.domain.request.LoginRequest;
import api.doc.docman.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JWTRequestTest extends WebIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void before(){
        userRepository.deleteAll();
        userService.save()
    }

    @DisplayName("1. hello 메시지 수신")
    @Test
    void test1(){
        RestTemplate client = new RestTemplate();

        HttpEntity<LoginRequest> body = new HttpEntity<>(
                LoginRequest.builder()
                .username("user")
                .password("1111")
                .build()
        );

        ResponseEntity<User> res = client.exchange(uri("/login"), HttpMethod.POST, body, User.class);


    }
}