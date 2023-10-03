package is442.portfolioAnalyzer.Trade;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trades")
@IdClass(TradeId.class)
public class Trade {

    @Id @Column(name = "purchase_date_time")
    private Timestamp purchaseDateTime;

    @Id @Column(name = "portfolio_id", nullable = false)
    private Integer portfolioId;

    @Id @Column(name = "stock_symbol", nullable = false)
    private String stockSymbol;

    private double purchasePrice;
    private Integer purchaseQuantity;

}
