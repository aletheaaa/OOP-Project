package is442.portfolioAnalyzer.Stock;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity 
public class Stock {
    @Id
    private String symbol;
    private String name;
    private String country;
    private String sector;
    private String industry; 
}
