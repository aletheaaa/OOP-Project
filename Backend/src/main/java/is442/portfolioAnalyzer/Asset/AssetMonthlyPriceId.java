package is442.portfolioAnalyzer.Asset;

import jakarta.persistence.Column;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class AssetMonthlyPriceId implements Serializable {
    @Column(name = "date")
    private String date;

    @Column(name = "stock_symbol") // Updated to stock_symbol
    private String stockSymbol;

}