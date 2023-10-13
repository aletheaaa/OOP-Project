package is442.portfolioAnalyzer.JsonModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GetPortfolioDetails {
    @JsonProperty("Assets Allocation")
    private AssetsAllocation assetsAllocation;
    @JsonProperty("PerformanceSummary")
    private PerformanceSummary performanceSummary;
    private Map<String, Map<String, Double>> performance;

    // Getters and setters
}