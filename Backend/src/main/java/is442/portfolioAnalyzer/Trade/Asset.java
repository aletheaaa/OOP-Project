package is442.portfolioAnalyzer.Trade;

import java.sql.Timestamp;

import is442.portfolioAnalyzer.Portfolio.Portfolio;
import is442.portfolioAnalyzer.Stock.Stock;
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
    private double purchasedPrice; //price at time of purchase

    private List <String> monthlyPerformance; //2013-Jan-1000



}
