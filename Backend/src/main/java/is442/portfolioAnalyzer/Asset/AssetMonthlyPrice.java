
package is442.portfolioAnalyzer.Asset;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "asset_monthly_prices")
public class AssetMonthlyPrice {
    
    @EmbeddedId
    private AssetMonthlyPriceId id;

    @Column(name = "closing_price")
    private Double closingPrice;

    @Column(name = "dividend_amount")
    private Double dividendAmount;
   
}

