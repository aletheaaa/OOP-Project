package is442.portfolioAnalyzer.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioDAO extends JpaRepository<Portfolio, Integer>{
   
}

