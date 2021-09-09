package api.doc.docman.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

public class IoCTest {
    @DisplayName("1. IoC Test")
    @Test
    void test1(){
        String url = "www.naver.com/user/it";
        ApplicationContext context = ApplicationContextProvider.getContext();

        Base64Encoder base64Encoder = context.getBean(Base64Encoder.class);
        Encoder encoder = new Encoder(base64Encoder);
        String result = encoder.encode(url);
        System.out.println(result);
    }
}
