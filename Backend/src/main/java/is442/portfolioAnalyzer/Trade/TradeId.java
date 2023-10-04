package is442.portfolioAnalyzer.Trade;

import java.io.Serializable;
import java.security.Timestamp;

import is442.portfolioAnalyzer.Portfolio.Portfolio;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;

import is442.portfolioAnalyzer.Stock.Stock;
@Data
@AllArgsConstructor
@NoArgsConstructor


@Embeddable
public class TradeId implements Serializable {
    

    // @ManyToOne
    // @JoinColumn (name = "portfolio_id")
    // private Portfolio portfolio;
    private Integer portfolioId;
    private String stockSymbol;
    private Timestamp purchaseDateTime;

    


}
