package is442.portfolioAnalyzer.JsonModels;

import is442.portfolioAnalyzer.Asset.Asset;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
public class PortfolioUpdate {
    @Id
    private Integer PortfolioId;
    private double Capital;
    private String TimePeriod;
    private String StartDate;
    private String PortfolioName;
    private String Description;
    private Integer UserId;
    private List<AssetCreation> AssetList;

}
