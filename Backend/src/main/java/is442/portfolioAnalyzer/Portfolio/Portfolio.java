package is442.portfolioAnalyzer.Portfolio;

import is442.portfolioAnalyzer.Stock.Stock;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import is442.portfolioAnalyzer.Asset;
import java.util.List;

@Data
@Entity
@Table(name = "portfolios")

public class Portfolio {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String portfolioName;
    private Double capital;
    private String timePeriod;
    private String startDate;
    private String description;	
    private Integer userId;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Asset> assets;

    

}
