package is442.portfolioAnalyzer.Asset;

import is442.portfolioAnalyzer.Portfolio.Portfolio;
import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Data
@AllArgsConstructor
@NoArgsConstructor


@Embeddable
public class AssetId implements Serializable {
    @Column(name = "portfolio_id")
    private Integer portfolioId;
    private String stockSymbol;
}
