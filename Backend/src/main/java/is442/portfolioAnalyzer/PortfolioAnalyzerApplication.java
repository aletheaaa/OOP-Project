package is442.portfolioAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortfolioAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioAnalyzerApplication.class, args);
	}

}
