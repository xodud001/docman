package api.doc.docman;

import api.doc.docman.di.ApplicationContextProvider;
import api.doc.docman.di.Base64Encoder;
import api.doc.docman.di.Encoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DocmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocmanApplication.class, args);

		String url = "www.naver.com/user/it";
		ApplicationContext context = ApplicationContextProvider.getContext();

		Base64Encoder base64Encoder = context.getBean(Base64Encoder.class);
		Encoder encoder = new Encoder(base64Encoder);
		String result = encoder.encode(url);
		System.out.println(result);
	}

}
