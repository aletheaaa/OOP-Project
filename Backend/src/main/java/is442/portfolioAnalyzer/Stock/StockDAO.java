package is442.portfolioAnalyzer.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDAO extends JpaRepository<Stock, String> {
    Stock findBySymbol(String symbol);
}



