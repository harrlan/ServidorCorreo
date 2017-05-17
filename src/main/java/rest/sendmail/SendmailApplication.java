package rest.sendmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SendmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendmailApplication.class, args);
	}
}
