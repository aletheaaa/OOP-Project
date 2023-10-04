package is442.portfolioAnalyzer.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    
    @Autowired
    TradeDAO tradeDAO;

    public List<Trade> getAllTrades(){
        return tradeDAO.findAll();
    }
}
