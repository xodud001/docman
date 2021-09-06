package api.doc.docman;
import api.doc.docman.di.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class DocmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocmanApplication.class, args);

		String url = "www.naver.com/user/it";
		ApplicationContext context = ApplicationContextProvider.getContext();

//		Base64Encoder base64Encoder = context.getBean(Base64Encoder.class);
		Encoder encoder = context.getBean("base64Encode", Encoder.class);
		String result = encoder.encode(url);
		System.out.println(result);

//		UrlEncoder urlEncoder = context.getBean(UrlEncoder.class);
//		encoder.setIEncoder(urlEncoder);
//		result = encoder.encode(url);
//		System.out.println(result);

	}
}

@Configuration
class AppConfig{

	@Bean("base64Encode")
	public Encoder encoder(Base64Encoder base64Encoder){
		return new Encoder(base64Encoder);
	}


	@Bean("urlEncode")
	public Encoder encoder(UrlEncoder urlEncoder){
		return new Encoder(urlEncoder);
	}
}