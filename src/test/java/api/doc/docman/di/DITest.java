package api.doc.docman.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DITest {

    @DisplayName("1. IoC Test")
    @Test
    void test1(){
        String url = "www.naver.com/user/it";

        Encoder encoder = new Encoder(new UrlEncoder());
        String result = encoder.encode(url);
        System.out.println(result);
    }
}
