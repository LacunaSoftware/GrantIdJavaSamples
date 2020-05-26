package lacuna.sample.webappgrantid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
public class WebappGrantidApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappGrantidApplication.class, args);
	}

	@Bean
	LayoutDialect thymeleafDialect() {
		return new LayoutDialect();
	}

	@Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
