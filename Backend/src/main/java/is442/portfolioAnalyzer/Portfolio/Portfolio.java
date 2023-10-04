package is442.portfolioAnalyzer.Portfolio;

import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "portfolios")

public class Portfolio {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer portfolioId;
    private Double capital;
    private String description;
    private String portfolioName;	
    private Integer userId;

    

}
