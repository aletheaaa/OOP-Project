package is442.portfolioAnalyzer.Asset;

import java.sql.Timestamp;

import is442.portfolioAnalyzer.Portfolio.Portfolio;
// import is442.portfolioAnalyzer.Stock.Stock;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "assets")
public class Asset {

    @EmbeddedId
    private AssetId assetId; //symbol and portfolio name
    private String sector;
    private double allocation;
    private double unitPrice; // The average price of each unit
    private double quantityPurchased;
    private String monthlyPerformance; //2013-Jan-1000:2013-Feb-2000


//    @ManyToOne
//    @MapsId("portfolioName") // Map to portfolioName in the composite primary key
//    @JoinColumn(name = "portfolio_name")
//    private Portfolio portfolio;

}
