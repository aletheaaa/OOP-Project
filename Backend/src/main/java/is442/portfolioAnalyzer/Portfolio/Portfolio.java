package is442.portfolioAnalyzer.Portfolio;

// import is442.portfolioAnalyzer.Stock.Stock;
import jakarta.persistence.*;
import lombok.Data;
import is442.portfolioAnalyzer.Asset.*;
import is442.portfolioAnalyzer.User.User;

import java.util.List;

@Data
@Entity
@Table(name = "portfolios")

public class Portfolio {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "portfolio_name")
    private String portfolioName;
    
    private Double capital;
    private String timePeriod;
    private String startDate;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id") // Name of the user_id column in the portfolios table
    private User user;


    @OneToMany
    @JoinColumn(name = "portfolio_name", referencedColumnName = "portfolio_name")
    private List<Asset> assets;



    

}
