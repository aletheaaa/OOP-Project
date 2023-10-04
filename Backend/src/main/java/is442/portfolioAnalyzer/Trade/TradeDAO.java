package is442.portfolioAnalyzer.Trade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDAO extends JpaRepository<Trade, TradeId> {
    
}
