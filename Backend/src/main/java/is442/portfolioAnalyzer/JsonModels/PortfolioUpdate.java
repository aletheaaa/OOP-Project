package is442.portfolioAnalyzer.JsonModels;

import jakarta.persistence.Id;
import lombok.*;
import java.util.List;


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
