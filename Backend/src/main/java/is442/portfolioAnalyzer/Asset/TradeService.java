package is442.portfolioAnalyzer.Asset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TradeService {
    
    @Autowired
    TradeDAO tradeDAO;

    public List<Asset> getAllTrades(){
        System.out.println("In service");
        return tradeDAO.findAll();
    }

    public List<Asset> getTradesByPortfolioId(Integer portfolioId){
        System.out.println("In controller");
        return tradeDAO.findByTradeIdPortfolioId(portfolioId);
    }

}
