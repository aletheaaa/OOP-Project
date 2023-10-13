package is442.portfolioAnalyzer.JsonModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.Map;
@Data
public class GetPortfolioDetails {
    @JsonProperty("Assets Allocation")
    private AssetsAllocation assetsAllocation;
    @JsonProperty("PerformanceSummary")
    private PerformanceSummary performanceSummary;
    private Map<String, Map<String, Double>> performance;

    // Getters and setters
}