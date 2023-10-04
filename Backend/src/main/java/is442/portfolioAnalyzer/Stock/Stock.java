package is442.portfolioAnalyzer.Stock;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    private String stockSymbol;
    private String stockName;
    private String lastUpdatedTime;
    private double lastUpdatedPrice;


}
