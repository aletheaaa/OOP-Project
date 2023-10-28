package is442.portfolioAnalyzer.Asset;

 import java.util.List;

 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

 @Repository
 public interface AssetMonthlyPriceDAO extends JpaRepository<AssetMonthlyPrice, AssetMonthlyPriceId> {
    boolean existsByIdStockSymbol(String symbol);    
    
    @Query("SELECT DISTINCT a.id.stockSymbol FROM AssetMonthlyPrice a")
    List<String> findDistinctStockSymbols();
 }
