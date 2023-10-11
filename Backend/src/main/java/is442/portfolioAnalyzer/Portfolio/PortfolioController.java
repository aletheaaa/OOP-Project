package is442.portfolioAnalyzer.Portfolio;

import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import is442.portfolioAnalyzer.Asset.*;

import java.util.*;


@RestController
@RequestMapping("portfolio")
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    AssetService assetService;
    @Autowired
    AssetDAO AssetDAO;

    @GetMapping("hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, Just testing123!");
    }

    @GetMapping("allPortfolios")
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        // return ResponseEntity.ok("Connect to Portfolio Service!");
        System.out.println("In controller");
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    @GetMapping("user/{userid}")
    public ResponseEntity <List<Portfolio>> getPortfolioByUser(@PathVariable Integer userid) {
        return ResponseEntity.ok(portfolioService.getPortfolioByUser(userid));
    }

    @GetMapping("portfolioName/{portfolioName}")
    public ResponseEntity<Portfolio> getPortfolioByName(@PathVariable String portfolioName) {
        Portfolio portfolio = portfolioService.getPortfolioByName(portfolioName);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping(value = "createPortfolio",consumes = "application/json")
    public ResponseEntity<?> createPortfolio(@RequestBody PortfolioCreation portfolioCreation) {
        portfolioService.createPortfolio(portfolioCreation);
//        Map<String, AssetCreation> assetList = portfolioCreation.getAssetList();

        String name = portfolioCreation.getPortfolioName();

        return ResponseEntity.ok("Portfolio Created!");
    }

    
    

}

