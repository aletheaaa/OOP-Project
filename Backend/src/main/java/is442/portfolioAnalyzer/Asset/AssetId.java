package is442.portfolioAnalyzer.Asset;


import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor


@Embeddable
public class AssetId implements Serializable {
    @Column(name = "portfolio_id")
    private Integer portfolioId;
    private String stockSymbol;
}
