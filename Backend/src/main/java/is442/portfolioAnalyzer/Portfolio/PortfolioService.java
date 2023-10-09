package is442.portfolioAnalyzer.Portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is442.portfolioAnalyzer.Asset.*;

import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    PortfolioDAO portfolioDAO;
    @Autowired
    TradeDAO tradeDAO;

    public List<Portfolio> getAllPortfolios(){
        System.out.println("In service");
        return portfolioDAO.findAll();
    }
    
        public List<Portfolio> getPortfolioByUser(Integer userid){
        return portfolioDAO.findByUserId(userid);

      
    }
      public List<Asset> getTradesByPortfolioId(Integer portfolioId){
         System.out.println("In controller");
        return tradeDAO.findByTradeIdPortfolioId(portfolioId);
    }
}
