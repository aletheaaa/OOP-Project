package is442.portfolioAnalyzer.JsonModels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
// @AllArgsConstructor
public class Stock {
    private String Symbol;
    private double Allocation;

    // Getters and setters
}

