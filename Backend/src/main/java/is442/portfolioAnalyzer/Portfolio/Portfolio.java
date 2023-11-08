package is442.portfolioAnalyzer.Portfolio;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use GenerationType.IDENTITY for auto-generated primary key
    @Column(name = "portfolio_id") // Name the primary key column
    private Integer portfolioId;

    @Column(name = "portfolio_name")
    private String portfolioName;
    
    private Double capital;
    private String startDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id") // Name of the user_id column in the portfolios table
    private User user;

    @OneToMany
    @JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
    private List<Asset> assets;

}

