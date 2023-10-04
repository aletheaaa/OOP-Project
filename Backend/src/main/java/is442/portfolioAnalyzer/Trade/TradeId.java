package is442.portfolioAnalyzer.Trade;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
