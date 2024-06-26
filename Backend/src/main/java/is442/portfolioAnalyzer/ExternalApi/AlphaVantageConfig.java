package is442.portfolioAnalyzer.ExternalApi;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import lombok.Getter;
import org.springframework.stereotype.Component;

//ZKMMTO1ATDBLXH2K
@Getter
@Component
public class AlphaVantageConfig {
    private final Config config;

    public AlphaVantageConfig() {
        this.config = Config.builder()
                .key("HI5ADT0RWWANGUID")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(config);
    }
    public Config getConfig() {
        return config;
    }

}
