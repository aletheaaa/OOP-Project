package is442.portfolioAnalyzer.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface StockDAO extends JpaRepository<Stock, String> {
    Stock findBySymbol(String symbol);
   

    @Query("SELECT s.symbol FROM Stock s")
    List<String> getAllStockSymbols();
}



