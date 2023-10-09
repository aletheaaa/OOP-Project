package is442.portfolioAnalyzer.ExternalApi;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AlphaVantageConfig {
    private final Config config;

    public AlphaVantageConfig() {
        this.config = Config.builder()
                .key("HYFJ1Q80KH14F95D")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(config);
    }
    public Config getConfig() {
        return config;
    }

}