package is442.portfolioAnalyzer.Stock;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Stock {

    @Id
    private String stockSymbol;
    private String stockName;
    private String lastUpdatedTimeDate;
    private double lastUpdatedPrice;


}
