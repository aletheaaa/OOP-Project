package is442.portfolioAnalyzer.Portfolio;

// import is442.portfolioAnalyzer.Stock.Stock;
import jakarta.persistence.*;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import lombok.Data;
import is442.portfolioAnalyzer.Asset.*;
import is442.portfolioAnalyzer.User.User;

import java.util.ArrayList;
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


//     @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    @OneToMany
    @JoinColumn(name = "portfolio_name", referencedColumnName = "portfolio_name")
    private List<Asset> assets;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable=false, updatable=false) // Name of the foreign key column in the portfolios table
//    private User user; // Reference to the User entity


    

}
