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

    private Integer PortfolioId;
    private double Capital;
    private String TimePeriod;
    private String StartDate;
    @Id
    private String PortfolioName;
    private String Description;
    private Integer UserId;
    private List<AssetCreation> AssetList;

}
