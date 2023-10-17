package is442.portfolioAnalyzer.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PortfolioDAO extends JpaRepository<Portfolio, Integer>{
   List<Portfolio> findByUserId(Integer userId);

   Portfolio findByPortfolioName(String portfolioName);
   
   @Query("SELECT p FROM Portfolio p JOIN p.user u WHERE p.portfolioName = :portfolioName AND u.id = :userId")
   Portfolio findByPortfolioNameAndId(@Param("portfolioName") String portfolioName, @Param("userId") Integer userId);
}

