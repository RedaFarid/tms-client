package soulintec.com.tmsclient;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableCaching(proxyTargetClass = true)
@EnableAsync(proxyTargetClass = true)
@SpringBootApplication
public class TmsClientApplication {

	public static void main(String[] args) {
		Application.launch(ApplicationContext.class, args);
	}

}
