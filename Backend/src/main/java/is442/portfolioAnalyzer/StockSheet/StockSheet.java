package is442.portfolioAnalyzer.StockSheet;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity 
public class StockSheet {
    @Id
    private String symbol;
    private String name;
    private String country;
    private String sector;
    private String industry; 
}
