package is442.portfolioAnalyzer.Portfolio;

import is442.portfolioAnalyzer.JsonModels.AssetsAllocation;
import is442.portfolioAnalyzer.JsonModels.GetPortfolioDetails;
import is442.portfolioAnalyzer.JsonModels.PerformanceSummary;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.JsonModels.PortfolioUpdate;
import is442.portfolioAnalyzer.JsonModels.UserPortfolios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import is442.portfolioAnalyzer.Exception.PortfolioNameNotUniqueException;
import is442.portfolioAnalyzer.Asset.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("portfolio")
@RestController
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

    
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "user/{userid}", produces = "application/json")
    public ResponseEntity<UserPortfolios>
    getPortfolioByUser(@PathVariable Integer userid) {
        UserPortfolios userPortfolios = portfolioService.getPortfolioByUser(userid);
        return ResponseEntity.ok(userPortfolios);
    }

    @GetMapping("portfolioName/{portfolioName}")
    public ResponseEntity<Portfolio> getPortfolioByName(@PathVariable String portfolioName) {
        Portfolio portfolio = portfolioService.getPortfolioByName(portfolioName);
        if (portfolio == null) {
            throw new PortfolioNameNotUniqueException("Portfolio name not found");
        }
        return ResponseEntity.ok(portfolio);
        
    }

    // Get portfolio by name and userid
    
    @GetMapping("{portfolioId}/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByIds(
        @PathVariable Integer portfolioId,
        @PathVariable Integer userId) {
    Portfolio portfolio = portfolioService.getPortfolioByIds(portfolioId,userId);
    if (portfolio == null) {
        System.out.println("Cannot find portfolio");
        return ResponseEntity.notFound().build();
    }
    System.out.println("Portfolio found");
    return ResponseEntity.ok(portfolio);
    }


    // Create portfolio ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "createPortfolio/{userId}", consumes = "application/json")
    public ResponseEntity<?> createPortfolio(@RequestBody PortfolioCreation portfolioCreation, @PathVariable Integer userId) {
        portfolioService.createPortfolio(portfolioCreation, userId);
        return ResponseEntity.ok("Portfolio Created!");
    }

    // Update portfolio ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "updatePortfolio/{userId}/{portfolioId}", consumes = "application/json")
    public ResponseEntity<?> updatePortfolio(@RequestBody PortfolioUpdate portfolioUpdate,
                                             @PathVariable Integer userId,
                                             @PathVariable Integer portfolioId) {
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);
        portfolioService.updatePortfolio(portfolioUpdate, portfolioId);
        return ResponseEntity.ok("Portfolio Updated!");
    }

    // Delete portfolio ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.DELETE, allowCredentials = "true")
    @DeleteMapping(value = "deletePortfolio/{userId}/{portfolioId}", produces = "application/json")
    public ResponseEntity<?> deletePortfolio(@PathVariable Integer portfolioId, @PathVariable Integer userId) {
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.ok("Portfolio Deleted!");
    }


    @GetMapping(value = "/getPortfolioDetails/{portfolioId}", produces = "application/json")
    public ResponseEntity<GetPortfolioDetails> getPortfolioDetails(@PathVariable Integer portfolioId) {
        GetPortfolioDetails portfolioDetails = portfolioService.getPortfolioDetails(portfolioId);
        System.out.println("In posting controller");
        if (portfolioDetails != null) {
            return ResponseEntity.ok(portfolioDetails);
        } else {
            // Handle errors and return an appropriate error response.
            System.out.println("Portfolio not found!");
            return ResponseEntity.notFound().build();

        }
        
    }
    
    //Get Portfolio Annual Growth by portfolioId and startYear
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("/getPortfolioAnnualGrowth/{portfolioId}/{startYear}")
    public ResponseEntity<Map<String, Double>> getPortfolioAnnualGrowth(@PathVariable int portfolioId, @PathVariable String startYear) {
        Map<String, Double> annualGrowth = portfolioService.getPortfolioAnnualGrowth(portfolioId, startYear);

        if (annualGrowth == null) {
            // Handle the case where the portfolio or startYear is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(annualGrowth);
    }



    //Get Assets Allocation by  portfolio ID
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "assetsAllocation/{portfolioId}", produces = "application/json")
    public ResponseEntity<AssetsAllocation> getAssetsAllocation(@PathVariable Integer portfolioId) {
        AssetsAllocation assetsAllocation = portfolioService.getAssetsAllocation(portfolioId);
    
        if (assetsAllocation != null) {
            return ResponseEntity.ok(assetsAllocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Get Performance Summary by portfolioID
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "performanceSummary/{portfolioId}", produces = "application/json")
    public ResponseEntity<PerformanceSummary> getPerformanceSummary(@PathVariable Integer portfolioId) {
        PerformanceSummary performanceSummary = portfolioService.getPerformanceSummary(portfolioId);
    
        if (performanceSummary != null) {
            return ResponseEntity.ok(performanceSummary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}

