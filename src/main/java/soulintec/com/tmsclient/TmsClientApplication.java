package soulintec.com.tmsclient;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching(proxyTargetClass = true)
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class TmsClientApplication {

	public static void main(String[] args) {
		Application.launch(ApplicationContext.class, args);
	}

}
