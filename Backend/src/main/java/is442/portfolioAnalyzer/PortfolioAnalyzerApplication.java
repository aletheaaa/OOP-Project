package is442.portfolioAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class PortfolioAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioAnalyzerApplication.class, args);
	}

}
