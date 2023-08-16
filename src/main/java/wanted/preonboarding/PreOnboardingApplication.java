package wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PreOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreOnboardingApplication.class, args);
	}

}
