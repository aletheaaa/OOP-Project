package is442.portfolioAnalyzer.Asset;

import jakarta.persistence.Column;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class AssetMonthlyPriceId implements Serializable {
    @Column(name = "year") // Stored as 2020, 2021
    private String year;

    @Column(name = "month") //Stored as January, February
    private String month;

    @Column(name = "stock_symbol") // Updated to stock_symbol
    private String stockSymbol; 

}