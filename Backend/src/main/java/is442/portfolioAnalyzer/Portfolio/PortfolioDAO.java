package is442.portfolioAnalyzer.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PortfolioDAO extends JpaRepository<Portfolio, Integer>{
   List<Portfolio> findByUserId(Integer userId);
}

