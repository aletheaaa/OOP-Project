package is442.portfolioAnalyzer.Portfolio;

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
        for (int i = 0; i < assetList.size() ; i++) {
            Asset asset = new Asset();
            AssetCreation assetCreation = assetList.get(i);
              // Create the assetId
            AssetId assetId = new AssetId();
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
        PerformanceSummary performanceSummary = new PerformanceSummary(0, 0, 0, 0, 0, 0);

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
        return null;
    }
}
