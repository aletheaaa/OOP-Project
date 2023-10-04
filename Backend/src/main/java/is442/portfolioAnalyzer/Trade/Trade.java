package is442.portfolioAnalyzer.Trade;

import java.security.Timestamp;

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

@Data
@Entity
@Table(name = "trades")
public class Trade {

    @EmbeddedId
    private TradeId tradeId;

    private double purchasePrice;
    private Integer purchaseQuantity;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("portfolioId") // Use the name of the property in TradeId
    // @JoinColumn(name = "portfolio_id")
    // private Portfolio portfolio;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("stockSymbol") // Use the name of the property in TradeId
    // @JoinColumn(name = "stock_symbol")
    // private Stock stock;
}
