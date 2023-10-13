package is442.portfolioAnalyzer.JsonModels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
@Getter
@Data
@AllArgsConstructor
public class PerformanceSummary {
    @JsonProperty("InitialBalance")
    private double InitialBalance;
    @JsonProperty("FinalBalance")
    private double FinalBalance;
    @JsonProperty("NetProfit")
    private double NetProfit;
    @JsonProperty("CAGR")
    private double CAGR;
    @JsonProperty("SharpeRatio")
    private double SharpeRatio;
    @JsonProperty("SortinoRatio")
    private double SortinoRatio;
    
    // Getters and setters
}

