package is442.portfolioAnalyzer.JsonModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
@Data
// @AllArgsConstructor
public class AssetsAllocation {
    private Map<String, AssetModel> assets;

    // Getters and setters
}