package is442.portfolioAnalyzer.Portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is442.portfolioAnalyzer.Asset.*;

import java.util.*;


@RestController
@RequestMapping("portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    TradeService tradeService;
    @Autowired
    TradeDAO tradeDAO;

    @GetMapping("hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, Just testing123!");
    }

    @GetMapping("allPortfolios")
    public List<Portfolio> getAllPortfolios() {
        // return ResponseEntity.ok("Connect to Portfolio Service!");
        System.out.println("In controller");
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("user/{userid}")
    public  List<Portfolio> getPortfolioByUser(@PathVariable Integer userid) {
        return portfolioService.getPortfolioByUser(userid);
    }

    @GetMapping("getTradesByPortfolioId/{portfolioId}")
    public List<Asset> getTradesByPortfolioId(@PathVariable Integer portfolioId){
        System.out.println("In controller");
        return tradeDAO.findByTradeIdPortfolioId(portfolioId);
        // directly access DAO

    }
    
    

}

