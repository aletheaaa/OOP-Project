package is442.portfolioAnalyzer.Portfolio;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import is442.portfolioAnalyzer.JsonModels.AssetCreation;
import is442.portfolioAnalyzer.JsonModels.AssetModel;
import is442.portfolioAnalyzer.JsonModels.AssetsAllocation;
import is442.portfolioAnalyzer.JsonModels.Stock;
import is442.portfolioAnalyzer.JsonModels.GetPortfolioDetails;
import is442.portfolioAnalyzer.JsonModels.PerformanceSummary;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import is442.portfolioAnalyzer.Asset.*;

import java.time.Year;
import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    PortfolioDAO portfolioDAO;
    @Autowired
    AssetDAO assetDAO;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    AssetService assetService;

    public List<Portfolio> getAllPortfolios() {
        System.out.println("In service");
        return portfolioDAO.findAll();
    }

    public List<Portfolio> getPortfolioByUser(Integer userid) {
        return portfolioDAO.findByUserId(userid);


    }

    public Portfolio getPortfolioByName(String portfolioName) {
        return portfolioDAO.findByPortfolioName(portfolioName);
    }


    public void createPortfolio(PortfolioCreation portfolioCreation) {

        // PROCESS THE PORTFOLIO CREATION
        Portfolio portfolio = new Portfolio();
        // Get the userId add  into portfolio
        User user = userServiceImpl.getUserById(portfolioCreation.getUserId());
        portfolio.setUser(user);
        // Get the portfolioName, capital, timePeriod, description, startDate and add into portfolio
        portfolio.setPortfolioName(portfolioCreation.getPortfolioName());
        portfolio.setCapital(portfolioCreation.getCapital());
        portfolio.setTimePeriod(portfolioCreation.getTimePeriod());
        portfolio.setDescription(portfolioCreation.getDescription());
        portfolio.setStartDate(portfolioCreation.getStartDate());

        // Save the portfolio without AssetList into DB first
        System.out.println("--------------------------------------");
//        System.out.println(portfolio);
        portfolioDAO.save(portfolio);

        // Creating the assets
        List<Asset> assets = new ArrayList<Asset>();
        // Loop through the asset list and create the assets
        List<AssetCreation> assetList = portfolioCreation.getAssetList();
        for (int i = 0; i < assetList.size(); i++) {
            Asset asset = new Asset();
            AssetCreation assetCreation = assetList.get(i);
            // Create the assetId
            AssetId assetId = new AssetId();
            String symbol = assetCreation.getSymbol();

            assetId.setPortfolioName(portfolioCreation.getPortfolioName());
            assetId.setStockSymbol(assetCreation.getSymbol());
            asset.setAssetId(assetId);

            asset.setSector(assetCreation.getSector());
            asset.setAllocation(assetCreation.getAllocation());

            if (!assetCreation.getSymbol().equals("CASHALLOCATION")) {
                // Call External API to get the latest price
                asset.setUnitPrice(assetService.getAssetLatestPrice(assetCreation.getSymbol()));
                // Add the quantity purchased based on the portfolio capital and asset allocation
                asset.setQuantityPurchased(portfolioCreation.getCapital() * assetCreation.getAllocation() / assetService.getAssetLatestPrice(assetCreation.getSymbol()));
            }

            // TODO - CREATE FUNCTION TO CALCULATE MONTHLY PERFORMANCE
           


            System.out.println(asset);
            assetDAO.save(asset);
            assets.add(asset);
        }

        // Add the assets into the portfolio
        portfolio.setAssets(assets);

        // Update the portfolio with the assetList
        portfolioDAO.save(portfolio);

    }


//   // Get the net profit of the portfolio based on the portfolioName
//   public double getNetProfit(String portfolioName) {
//       Portfolio portfolio = portfolioDAO.findByPortfolioName(portfolioName);
//       List<Asset> assets = portfolio.getAssets();
//       double totalAssetsValue = 0;
//       for (int i = 0; i < assets.size(); i++) {
//           Asset asset = assets.get(i);
//           totalAssetsValue += asset.getTotalValue();
//       }
//       double netProfit = totalAssetsValue - portfolio.getCapital();
//       return netProfit;
//
//   }
//
//   // Get portfolio final balance
//   public double getPortfolioFinalBalance(String portfolioName) {
//       Portfolio portfolio = portfolioDAO.findByPortfolioName(portfolioName);
//       List<Asset> assets = portfolio.getAssets();
//       double totalValue = 0;
//       for (int i = 0; i < assets.size(); i++) {
//           Asset asset = assets.get(i);
//           totalValue += asset.getTotalValue();
//       }
//       return totalValue;
//   }
//
//   // Get the portfolio's CAGR
//   public double getCAGR(String portfolioName) {
//       Portfolio portfolio = portfolioDAO.findByPortfolioName(portfolioName);
//       double finalBalance = getPortfolioFinalBalance(portfolioName);
//       double initialBalance = portfolio.getCapital();
//
//       // Calculate the time period from current
//       int currentYearvalue = Year.now().getValue();
//       int startYearValue = Integer.parseInt(portfolio.getStartDate().substring(0, 4));
//       int timePeriod = currentYearvalue - startYearValue;
//
//       double CAGR = Math.pow(finalBalance/initialBalance, 1/timePeriod) - 1;
//       return CAGR * 100; // Convert to percentage
//   }
//
//   // Get SharpeRatio of the portfolio
//   // The Sharpe Ratio is a measure of the risk-adjusted return of a portfolio.
//   public double calcSharpeRatio(double expectedReturn, double riskFreeRate, double standardDeviation) {
//       // Example: 10% expected return
//       // Example: 3% risk-free rate
//       // Example: 15% standard deviation
//       return (expectedReturn - riskFreeRate) / standardDeviation;
//   }
//
//   // Calculate portfolio's standard deviation
//   public double calcStandardDeviation(double[] stockReturns) {
//       return Math.sqrt(calcVariance(stockReturns));
//   }
//
//   // Calculate portfolio's variance
//   public double calcVariance(double[] stockReturns) {
//       double sum = 0.0;
//       double mean = calcMean(stockReturns);
//       for (double stockReturn : stockReturns) {
//           sum += Math.pow(stockReturn - mean, 2.0);
//       }
//       return sum / (stockReturns.length - 1);
//   }
//
//   // Calculate portfolio's mean
//   public double calcMean(double[] stockReturns) {
//       double sum = 0.0;
//       for (double stockReturn : stockReturns) {
//           sum += stockReturn;
//       }
//       return sum / stockReturns.length;
//   }
//
//   // Calculate portfolio's expected return
//   public double calculateExpectedReturn(double[] assetReturns, double[] weights) {
//   //The expected return of a portfolio is a weighted sum of the expected returns of its individual assets,
//   //where the weights represent the proportion of each asset in the portfolio
//   if (assetReturns.length != weights.length) {
//       // TODO - Handles exceptions
//       throw new IllegalArgumentException("Arrays must have the same length.");
//   }
//   double expectedReturn = 0;
//
//   for (int i = 0; i < assetReturns.length; i++) {
//       expectedReturn += assetReturns[i] * weights[i];
//   }
//   return expectedReturn;
//   }






    public GetPortfolioDetails getPortfolioDetails(String portfolioName) {
        System.out.println("In portfolio posting service");
        
        //create a GetPortfoilioDetails json model
        GetPortfolioDetails portfolioDetails = new GetPortfolioDetails();

   
        // create AssetModel json model
        AssetModel assetModel = new AssetModel(0, null);

        // create AssetAllocation json model
        AssetsAllocation assetsAllocation = new AssetsAllocation();
        Map<String, AssetModel> assetMap = new HashMap<>();

        // create PerformanceSummary json model
        // PerformanceSummary performanceSummary = new PerformanceSummary(0, 0, 0, 0, 0, 0);

        // get existing portfolio by name
        Portfolio portfolio = portfolioDAO.findByPortfolioName(portfolioName);

    
        // Loop through assets of existing portfolio
        for (Asset asset : portfolio.getAssets()) {
            // populate json models less GetPortfolioDetails
            
            // Create a list of stocks 
            List<Stock> stocks = new ArrayList<>(); 

        }
        
        
        // populate GetPortfoilioDetails 
        
        // return GetPortfoilioDetails
        
        // TESTING SAMPLE DATA
        // Sample AssetModel instances for Technology and Healthcare
        AssetModel technologyAsset = new AssetModel(0.5, new ArrayList<>());
        AssetModel healthcareAsset = new AssetModel(0.4, new ArrayList<>());    

        // Sample Stock instances
        Stock teslaStock = new Stock("TSLA", 0.5);
        Stock pfizerStock = new Stock("PFE", 0.4);

        // Add Stock instances to AssetModel
        technologyAsset.getStocks().add(teslaStock);
        healthcareAsset.getStocks().add(pfizerStock);

        // // Populate the assets map
        assetMap.put("Technology", technologyAsset);
        assetMap.put("Healthcare", healthcareAsset);
        assetsAllocation.setAssets(assetMap);

        // Populate the PerformanceSummary
        PerformanceSummary performanceSummary = new PerformanceSummary(
            1000.0, // InitialBalance
            2000.0, // FinalBalance
            1000.0, // NetProfit
            7.77,   // CAGR
            0.59,   // SharpeRatio
            0.42    // SortinoRatio
        );      

                // Create a sample "performance" map
        Map<String, Map<String, Double>> performanceMap = new HashMap<>();

        // Sample performance data for 2013
        Map<String, Double> performance2013 = new HashMap<>();
        performance2013.put("Jan", 1100.0);
        performance2013.put("Feb", 1200.0);
        performance2013.put("Mar", 2100.0);

        // Sample performance data for 2014
        Map<String, Double> performance2014 = new HashMap<>();
        performance2014.put("Jan", 1200.0);
        performance2014.put("Feb", 1300.0);
        performance2014.put("Mar", 1100.0);

        // Add the performance data to the "performance" map
        performanceMap.put("2013", performance2013);
        performanceMap.put("2014", performance2014);

        // Set the "performance" map in GetPortfolioDetails
        portfolioDetails.setPerformance(performanceMap);

        // Set AssetsAllocation and PerformanceSummary in GetPortfolioDetails
        portfolioDetails.setAssetsAllocation(assetsAllocation);
        portfolioDetails.setPerformanceSummary(performanceSummary);
        
        return portfolioDetails;
    }
}

