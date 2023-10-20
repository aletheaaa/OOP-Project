package is442.portfolioAnalyzer.JsonModels;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class AssetModel {
    private double value;
    private List<Stock> stocks;

    // Getters and setters
}

