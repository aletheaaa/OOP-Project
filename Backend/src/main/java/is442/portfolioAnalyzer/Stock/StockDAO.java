package is442.portfolioAnalyzer.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDAO extends JpaRepository<Stock, String> {
    
}
