package is442.portfolioAnalyzer.Portfolio;

import is442.portfolioAnalyzer.Exception.PortfolioNotFoundException;
import is442.portfolioAnalyzer.JsonModels.AssetsAllocation;
import is442.portfolioAnalyzer.JsonModels.PerformanceSummary;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.JsonModels.PortfolioUpdate;
import is442.portfolioAnalyzer.JsonModels.UserPortfolios;
import is442.portfolioAnalyzer.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    TokenService tokenService;


    // GET PORTFOLIO 
    // ----------------------------------------------------------------------------------------------

    // Get all portfolios of User by UserId
    @GetMapping("user/{userid}")
    public ResponseEntity<UserPortfolios> getAllPortfoliosByUserId(
            @PathVariable Integer userid,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userid, authHeader.substring(7));

        UserPortfolios userPortfolios = portfolioService.getAllPortfoliosByUserId(userid);
        return ResponseEntity.ok(userPortfolios);
    }
    
    // Get Portfolio by PortfolioId and UserId
    @GetMapping("{portfolioId}/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByIds(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId) {
        Portfolio portfolio = portfolioService.getPortfolioByIds(portfolioId, userId);
        if (portfolio == null) {
            System.out.println("Cannot find portfolio");
            return ResponseEntity.notFound().build();
        }
        System.out.println("Portfolio found");
        return ResponseEntity.ok(portfolio);
    }

    // CREATE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping(value = "createPortfolio/{userId}", consumes = "application/json")
    public ResponseEntity<?> createPortfolio(
            @RequestBody PortfolioCreation portfolioCreation,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.createPortfolio(portfolioCreation, userId);
        return ResponseEntity.ok("Portfolio Created!");
    }

    // UPDATE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PutMapping(value = "updatePortfolio/{userId}/{portfolioId}", consumes = "application/json")
    public ResponseEntity<?> updatePortfolio(@RequestBody PortfolioUpdate portfolioUpdate,
            @PathVariable Integer userId,
            @PathVariable Integer portfolioId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);
        portfolioService.updatePortfolio(portfolioUpdate, portfolioId);
        return ResponseEntity.ok("Portfolio Updated!");
    }

    // DELETE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.DELETE, allowCredentials = "true")
    @DeleteMapping(value = "deletePortfolio/{userId}/{portfolioId}", produces = "application/json")
    public ResponseEntity<?> deletePortfolio(@PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.ok("Portfolio Deleted!");
    }

    // GET PORTFOLIO DETAILS
    // ----------------------------------------------------------------------------------------------
    @GetMapping(value = "/getPortfolioDetails/{userId}/{portfolioId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getPortfolioDetails(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Object> portfolioDetails = portfolioService.getPortfolioDetails(portfolioId);

        if (portfolioDetails == null) {
            // Handle the case where the data is not found or invalid inputs.
            throw new PortfolioNotFoundException("Portfolio not found");
        }

        return ResponseEntity.ok(portfolioDetails);
    }

    // Get Performance Summary by portfolioID
    @GetMapping("performanceSummary/{userId}/{portfolioId}")
    public ResponseEntity<PerformanceSummary> getPerformanceSummary(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        PerformanceSummary performanceSummary = portfolioService.getPerformanceSummary(portfolioId);

        if (performanceSummary != null) {
            return ResponseEntity.ok(performanceSummary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PORTFOLIO PIE CHARTS
    // ---------------------------------------------------------------------------------------------------

    // Get Assets and Sector Allocation by portfolio ID
    @GetMapping("assetsAllocation/{userId}/{portfolioId}")
    public ResponseEntity<AssetsAllocation> getAssetsAllocation(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        AssetsAllocation assetsAllocation = portfolioService.getAssetsAllocation(portfolioId);

        if (assetsAllocation != null) {
            return ResponseEntity.ok(assetsAllocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Industry Allocation by portfolioID
    @GetMapping("getIndustries/{userId}/{portfolioId}")
    public Map<String, Double> getIndustryAllocation(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Double> getIndustryAllocation = portfolioService.getIndustryAllocation(portfolioId);
        return getIndustryAllocation;
    }

    // Get Country Allocation by portfolioID
    @GetMapping("getCountries/{userId}/{portfolioId}")
    public Map<String, Double> getCountryAllocation(
            @PathVariable Integer portfolioId,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Double> getCountryAllocation = portfolioService.getCountryAllocation(portfolioId);
        return getCountryAllocation;
    }

    //PORTFOLIO LINE & BAR CHARTS
    // ---------------------------------------------------------------------------------------------------
    
    // Get Portfolio Annual Growth by portfolioId and startYear
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("/getPortfolioAnnualGrowth/{userId}/{portfolioId}/{startYear}")
    public ResponseEntity<Map<String, Double>> getPortfolioAnnualGrowth(
            @PathVariable int portfolioId,
            @PathVariable String startYear,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        tokenService.checkTokenBelongsToUser(userId, token);
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Double> annualGrowth = portfolioService.getPortfolioAnnualGrowth(portfolioId, startYear);

        if (annualGrowth == null) {
            // Handle the case where the portfolio or startYear is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(annualGrowth);
    }

    // Get Portfolio Monthly Growth by portfolioId, startYear and startMonth
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("/getPortfolioMonthlyGrowth/{userId}/{portfolioId}/{startYear}/{startMonth}")
    public ResponseEntity<Map<String, Map<String, Integer>>> getPortfolioMonthlyGrowth(
            @PathVariable Integer portfolioId,
            @PathVariable String startYear,
            @PathVariable String startMonth,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {
        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Map<String, Integer>> monthlyGrowth = portfolioService.getPortfolioMonthlyGrowth(portfolioId,
                startYear, startMonth);

        if (monthlyGrowth == null) {
            // Handle the case where the data is not found or invalid inputs.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(monthlyGrowth);
    }

    // Get Portfolio Annual Returns (% returns) by portfolioId and startYear
    @GetMapping("/getPortfolioAnnualReturns/{userId}/{portfolioId}/{startYear}")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    public ResponseEntity<Map<String, Double>> getPortfolioAnnualReturns(
            @PathVariable Integer portfolioId,
            @PathVariable String startYear,
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId, userId);

        Map<String, Double> annualReturns = portfolioService.getPortfolioAnnualReturns(portfolioId, startYear);

        if (annualReturns == null) {
            // Handle the case where the data is not found or invalid inputs.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(annualReturns);
    }

    //PORTFOLIO COMPARISON
    // ---------------------------------------------------------------------------------------------------

    // Get difference in portfolio profit by portfolioID
    @GetMapping("getDifferenceInPortfolioProfit/{userId}/{portfolioId1}/{portfolioId2}")
    public double getDifferenceInPortfolioProfit(
            @PathVariable Integer userId,
            @PathVariable Integer portfolioId1,
            @PathVariable Integer portfolioId2,
            @RequestHeader("Authorization") String authHeader) {

        tokenService.checkTokenBelongsToUser(userId, authHeader.substring(7));
        portfolioService.checkPortfolioBelongsToUser(portfolioId1, userId);
        portfolioService.checkPortfolioBelongsToUser(portfolioId2, userId);

        double portfolio1Profit = portfolioService.getNetProfit(portfolioId1);
        double portfolio2Profit = portfolioService.getNetProfit(portfolioId2);

        if (portfolio1Profit > portfolio2Profit) {
            return portfolio1Profit - portfolio2Profit;
        } else {
            return portfolio2Profit - portfolio1Profit;
        }
    }

}
