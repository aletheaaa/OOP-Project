package is442.portfolioAnalyzer.Portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    PortfolioDAO portfolioDAO;

    public List<Portfolio> getAllPortfolios(){
        System.out.println("In service");
        return portfolioDAO.findAll();
    }
    
}
