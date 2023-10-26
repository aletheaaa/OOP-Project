package is442.portfolioAnalyzer.StockSheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSheetDAO extends JpaRepository<StockSheet, String> {
}



