package is442.portfolioAnalyzer.Trade;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trades")
public class Trade {

    @EmbeddedId
    private TradeId idSymbol;
    private double purchasePrice;
    private Integer purchaseQuantity;

}
