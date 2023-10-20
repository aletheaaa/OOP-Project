package is442.portfolioAnalyzer.JsonModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class AssetCreation {

    private String Symbol;
    private double Allocation;
    private String Sector;
}
