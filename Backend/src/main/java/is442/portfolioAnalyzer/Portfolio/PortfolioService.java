package is442.portfolioAnalyzer.Portfolio;

import is442.portfolioAnalyzer.Exception.PortfolioNameNotUniqueException;
import is442.portfolioAnalyzer.Exception.UserPortfolioNotMatchException;
import is442.portfolioAnalyzer.JsonModels.AssetCreation;
import is442.portfolioAnalyzer.JsonModels.AssetModel;
import is442.portfolioAnalyzer.JsonModels.AssetsAllocation;
import is442.portfolioAnalyzer.JsonModels.Stock;
import is442.portfolioAnalyzer.JsonModels.UserPortfolios;
import is442.portfolioAnalyzer.JsonModels.GetPortfolioDetails;
import is442.portfolioAnalyzer.JsonModels.PerformanceSummary;
import is442.portfolioAnalyzer.JsonModels.PortfolioCreation;
import is442.portfolioAnalyzer.JsonModels.PortfolioUpdate;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is442.portfolioAnalyzer.Asset.*;

import java.time.Year;
import java.util.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

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

    // Get all the portfolios for a user by specifying the userId
    public UserPortfolios getAllPortfoliosByUserId(Integer userId) {
        List<Portfolio> portfolios = portfolioDAO.findByUserId(userId);
        List<Map<String, String>> userPortfoliosList = new ArrayList<>();
        UserPortfolios userPortfolios = new UserPortfolios(null);
        for (Portfolio portfolio : portfolios) {
            Map<String, String> portfolioMap = new HashMap<>();
            portfolioMap.put("portfolioId", String.valueOf(portfolio.getPortfolioId()));
            portfolioMap.put("portfolioName", portfolio.getPortfolioName());
            userPortfoliosList.add(portfolioMap);
            userPortfolios.setUserPortfolios(userPortfoliosList);
        }

        return userPortfolios;
    }

    public Portfolio getPortfolioByName(String portfolioName) {
        return portfolioDAO.findByPortfolioName(portfolioName);
    }

    public Portfolio getPortfolioByIds(Integer portfolioId, Integer userId) {
        System.out.println("Get portfolio by name and id  - in the service");
        return portfolioDAO.findByPortfolioIds(portfolioId, userId);
    }

    public Portfolio findByPortfolioId(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        if (portfolio == null) {
            throw new UserPortfolioNotMatchException("User does not have the portfolio!");
        } else {
            return portfolio;
        }
    }

    // Will throw Exception if Portfolio does not belong to the user
    public void checkPortfolioBelongsToUser(Integer portfolioId, Integer userId) {
        Portfolio portfolio = findByPortfolioId(portfolioId);
        if (portfolio.getUser().getId() != userId) {
            throw new UserPortfolioNotMatchException("User does not have the portfolio!");
        }
    }

    // CREATE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    public void createPortfolio(PortfolioCreation portfolioCreation, Integer userId)
            throws PortfolioNameNotUniqueException {

        // PROCESS THE PORTFOLIO CREATION
        Portfolio portfolio = new Portfolio();
        // Get the userId add into portfolio
        User user = userServiceImpl.getUserById(userId);
        portfolio.setUser(user);
        // Get the portfolioName, capital, timePeriod, description, startDate and add
        // into portfolio
        portfolio.setPortfolioName(portfolioCreation.getPortfolioName());
        portfolio.setCapital(portfolioCreation.getCapital());
        portfolio.setTimePeriod(portfolioCreation.getTimePeriod());
        portfolio.setDescription(portfolioCreation.getDescription());
        portfolio.setStartDate(portfolioCreation.getStartDate());

        // Exception handling to check if portfolio name already exists for the user
        List<Portfolio> existingPortfolios = portfolioDAO.findByUserId(userId);

        for (Portfolio existingPortfolio : existingPortfolios) {
            if (existingPortfolio.getPortfolioName().equals(portfolio.getPortfolioName())) {
                throw new PortfolioNameNotUniqueException("Portfolio name is not unique");
            }
        }

        // Save the portfolio without AssetList into DB first
        System.out.println("--------------------------------------");
        // System.out.println(portfolio);
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

            // Set all monthly prices and divident amount of asset by symbol and save into
            // DB
            assetService.populateAssetMonthlyPrices(symbol);

            assetId.setPortfolioId(portfolio.getPortfolioId());
            assetId.setStockSymbol(assetCreation.getSymbol());
            asset.setAssetId(assetId);

            asset.setSector(assetCreation.getSector());
            asset.setAllocation(assetCreation.getAllocation());

            // Set Monthly Prices of asset from API
            // assetService.updateMonthlyPrices(asset, symbol);

            if (!assetCreation.getSymbol().equals("CASHALLOCATION")) {
                // Call External API to get the latest price
                asset.setUnitPrice(assetService.getAssetLatestPrice(symbol));
                // Add the quantity purchased based on the portfolio capital and asset
                // allocation
                asset.setQuantityPurchased(portfolioCreation.getCapital() * assetCreation.getAllocation()
                        / assetService.getAssetLatestPrice(symbol));

                // Calculate the total value of the asset
                asset.setTotalValue(asset.getUnitPrice() * asset.getQuantityPurchased());
            }

            // System.out.println(asset);
            assetDAO.save(asset);
            assets.add(asset);
        }

        // Add the assets into the portfolio
        portfolio.setAssets(assets);

        // Update the portfolio with the assetList
        portfolioDAO.save(portfolio);

    }

    // UPDATE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    public void updatePortfolio(PortfolioUpdate portfolioUpdate, Integer portfolioId) {
        Portfolio portfolio = findByPortfolioId(portfolioUpdate.getPortfolioId());

        // Set the portfolio capital, description, startDate, timePeriod
        portfolio.setPortfolioName(portfolioUpdate.getPortfolioName());
        portfolio.setCapital(portfolioUpdate.getCapital());
        portfolio.setDescription(portfolioUpdate.getDescription());
        portfolio.setStartDate(portfolioUpdate.getStartDate());
        portfolio.setTimePeriod(portfolioUpdate.getTimePeriod());

        // Get the assetList from the portfolioCreation
        List<AssetCreation> assetList = portfolioUpdate.getAssetList();
        List<String> existedAssets = getAssetSymbols(portfolioId);

        List<Asset> assets = assetService.getAssetsByPortfolioId(portfolioId);
        // Loop through the assetList and update each asset in the portfolio
        for (int i = 0; i < assetList.size(); i++) {
            AssetCreation assetCreation = assetList.get(i);
            // Get the stockSymbol
            String symbol = assetCreation.getSymbol();

            for (Asset asset : assets) {
                // Check if the asset is in the portfolio
                if (existedAssets.contains(symbol)) {
                    if (asset.getAssetId().getStockSymbol().equals(symbol)) {
                        double oldTotalValue = asset.getTotalValue();
                        // Update the asset
                        asset.setAllocation(assetCreation.getAllocation());
                        asset.setTotalValue(assetCreation.getAllocation() * portfolioUpdate.getCapital());

                        if (!assetCreation.getSymbol().equals("CASHALLOCATION")) {
                            // Update the quantity purchased based on the portfolio capital and asset
                            // allocation
                            double oldQuantityPurchased = asset.getQuantityPurchased();
                            double stockTotalValueDifference = asset.getTotalValue() - oldTotalValue;
                            // Get the additional stock quantity purchased or sold
                            double stockPurchasedOrSold = stockTotalValueDifference
                                    / assetService.getAssetLatestPrice(symbol);
                            asset.setQuantityPurchased(oldQuantityPurchased + stockPurchasedOrSold);
                            asset.setUnitPrice(asset.getTotalValue() / asset.getQuantityPurchased());
                        }

                        System.out.println(symbol);
                        System.out.println(assetCreation.getAllocation() + " updated");
                        System.out.println(assetCreation.getAllocation() * portfolioUpdate.getCapital() + " updated");
                        System.out.println(asset.getQuantityPurchased() + " updated");
                        System.out.println(asset.getUnitPrice() + " updated");
                        System.out.println(asset.getTotalValue() + " updated");

                        System.out.println("--------------------------------------");

                        // Update the asset in the DB
                        assetDAO.save(asset);
                    }

                } else { // If new asset added ...
                    existedAssets.add(symbol);

                    // Set all monthly prices and divident amount of asset by symbol and save into
                    // DB
                    assetService.populateAssetMonthlyPrices(symbol);

                    // Create the asset
                    Asset newAsset = new Asset();
                    // Create the assetId
                    AssetId assetId = new AssetId();
                    assetId.setPortfolioId(portfolioId);
                    assetId.setStockSymbol(symbol);
                    newAsset.setAssetId(assetId);
                    newAsset.setSector(assetCreation.getSector());
                    newAsset.setAllocation(assetCreation.getAllocation());
                    newAsset.setTotalValue(assetCreation.getAllocation() * portfolioUpdate.getCapital());
                    newAsset.setUnitPrice(assetService.getAssetLatestPrice(symbol));
                    newAsset.setQuantityPurchased(newAsset.getTotalValue() / newAsset.getUnitPrice());

                    System.out.println(symbol + " added");
                    System.out.println(newAsset.getAllocation() + " added");
                    System.out.println(newAsset.getTotalValue() + " added");
                    System.out.println(newAsset.getQuantityPurchased() + " added");
                    System.out.println(newAsset.getUnitPrice() + " added");
                    System.out.println(newAsset.getTotalValue() + " added");

                    // Save the new asset into the DB
                    assetDAO.save(newAsset);
                }
            }
        }
        // Remove existing assets that are not in the updated lit of assets
        List<String> symbolsToUpdate = getAssetSymbolsToUpdate(assetList);
        for (Asset asset : assets) {
            if (!symbolsToUpdate.contains(asset.getAssetId().getStockSymbol())) {
                assetDAO.delete(asset);
            }
        }

        portfolioDAO.save(portfolio);
    }

    // DELETE PORTFOLIO
    // ---------------------------------------------------------------------------------------------------
    public void deletePortfolio(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        List<Asset> assets = portfolio.getAssets();
        for (Asset asset : assets) {
            assetDAO.delete(asset);
        }
        portfolioDAO.delete(portfolio);
    }

    // Get a list of asset symbols in the portfolio
    public List<String> getAssetSymbols(Integer portfolioId) {
        List<Asset> assets = assetService.getAssetsByPortfolioId(portfolioId);
        List<String> symbols = new ArrayList<>();
        for (Asset asset : assets) {
            symbols.add(asset.getAssetId().getStockSymbol());
        }
        return symbols;
    }

    // Retrieve the list of asset symbols that is to be updated
    public List<String> getAssetSymbolsToUpdate(List<AssetCreation> assetList) {
        List<String> symbolsToUpdate = new ArrayList<>();
        for (AssetCreation assetCreation : assetList) {
            symbolsToUpdate.add(assetCreation.getSymbol());
        }
        return symbolsToUpdate;
    }

    // Get list of portfolio values by end of every year from the startYear to
    // current year
    public Map<String, Double> getPortfolioAnnualGrowth(int portfolioId, String startYear) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return null;
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYearInt = Integer.parseInt(startYear);

        if (startYearInt > currentYear) {
            // Handle the case where the start year is in the future.
            return null;
        }

        Map<String, Double> portfolioValuesByYear = new HashMap<>();

        for (int year = startYearInt; year <= currentYear; year++) {
            String yearString = Integer.toString(year);
            double portfolioValue = getPortfolioValueByYear(portfolioId, yearString);
            portfolioValuesByYear.put(yearString, portfolioValue);
        }

        return portfolioValuesByYear;
    }

    // Calculate the value of the portfolio by the end of the year
    public int getPortfolioValueByYear(Integer portfolioId, String year) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return 0; // Return 0 as an integer.
        }

        List<Asset> assets = portfolio.getAssets();
        BigDecimal portfolioValue = BigDecimal.ZERO; // Initialize as zero.

        for (Asset asset : assets) {
            double assetValue = assetService.getAssetValueByYear(asset.getAssetId().getStockSymbol(), year, asset);
            BigDecimal assetValueBigDecimal = new BigDecimal(assetValue);
            portfolioValue = portfolioValue.add(assetValueBigDecimal);
        }

        // Convert the portfolio value to an integer.
        int portfolioValueInt = portfolioValue.intValue();

        return portfolioValueInt;
    }

    // Get list of portfolio values by year and month from the starting year and
    // month until the current year and month
    public Map<String, Map<String, Integer>> getPortfolioMonthlyGrowth(int portfolioId, String startYear,
            String startMonth) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return null;
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Adjust for 0-based index

        int startYearInt = Integer.parseInt(startYear);
        int startMonthInt = Integer.parseInt(startMonth);

        if (startYearInt > currentYear || (startYearInt == currentYear && startMonthInt > currentMonth)) {
            // Handle the case where the start year and month are in the future.
            return null;
        }

        Map<String, Map<String, Integer>> portfolioMonthlyValues = new HashMap<>();

        for (int year = startYearInt; year <= currentYear; year++) {
            Map<String, Integer> monthlyValues = new LinkedHashMap<>();
            int endMonth = (year == currentYear) ? currentMonth : 12;

            for (int month = startMonthInt; month <= endMonth; month++) {
                String monthName = getMonthName(month - 1); // Adjust for 0-based index
                String yearString = Integer.toString(year);
                int portfolioValue = getPortfolioValueByYearAndMonth(portfolioId, yearString, monthName);
                monthlyValues.put(monthName, portfolioValue);
            }

            portfolioMonthlyValues.put(Integer.toString(year), monthlyValues);
        }

        return portfolioMonthlyValues;
    }

    // Get the portfolio value at the end of the specified year and month
    public int getPortfolioValueByYearAndMonth(Integer portfolioId, String year, String month) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return 0; // Return 0 as an integer.
        }

        List<Asset> assets = portfolio.getAssets();
        BigDecimal portfolioValue = BigDecimal.ZERO; // Initialize as zero.

        for (Asset asset : assets) {
            double assetValue = assetService.getAssetValueByYearAndMonth(asset.getAssetId().getStockSymbol(), year,
                    month, asset);
            BigDecimal assetValueBigDecimal = new BigDecimal(assetValue);
            portfolioValue = portfolioValue.add(assetValueBigDecimal);
        }

        // Convert the portfolio value to an integer, effectively rounding down.
        int portfolioValueInt = portfolioValue.intValue();

        return portfolioValueInt;
    }

    // Helper method to get month name based on its number (0-based index).
    private String getMonthName(int month) {
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
    }

    public Map<String, Double> getPortfolioAnnualReturns(int portfolioId, String startYear) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return null;
        }

        // Calculate the portfolio values by year for all years after the start year.
        Map<String, Double> portfolioValuesByYear = new TreeMap<>(); // Use a TreeMap to keep years sorted.

        int startYearInt = Integer.parseInt(startYear);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        double previousYearValue = getPortfolioValueByYear(portfolioId, String.valueOf(startYearInt - 1));

        for (int year = startYearInt; year <= currentYear; year++) {
            String yearString = String.valueOf(year);
            double portfolioValue = getPortfolioValueByYear(portfolioId, yearString);
            System.out.println("Portfolio value for " + yearString + ": " + String.valueOf(portfolioValue));

            // Calculate the annual return as a percentage increase with two decimal places.
            double annualReturn = 0.0;
            if (previousYearValue != 0.0) {
                annualReturn = ((portfolioValue - previousYearValue) / previousYearValue) * 100;
                annualReturn = Math.round(annualReturn * 100.0) / 100.0; // Round to two decimal places.
            }

            portfolioValuesByYear.put(yearString, annualReturn);

            // Update the previous year's value.
            previousYearValue = portfolioValue;
        }

        return portfolioValuesByYear;
    }

    // // Get the net profit of the portfolio based on the portfolioName
    public double getNetProfit(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        List<Asset> assets = portfolio.getAssets();
        double totalAssetsValue = 0;
        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            totalAssetsValue += asset.getTotalValue();
        }
        double netProfit = totalAssetsValue - portfolio.getCapital();
        return netProfit;

    }

    //
    // // Get portfolio final balance
    public double getPortfolioFinalBalance(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        List<Asset> assets = portfolio.getAssets();
        double totalValue = 0;
        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            totalValue += asset.getTotalValue();
        }
        return totalValue;
    }

    // // Get the portfolio's CAGR
    public double getCAGR(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        double finalBalance = getPortfolioFinalBalance(portfolioId);
        double initialBalance = portfolio.getCapital();

        // Calculate the time period from current
        int currentYearvalue = Year.now().getValue();
        int startYearValue = Integer.parseInt(portfolio.getStartDate().substring(0, 4));
        int timePeriod = currentYearvalue - startYearValue;

        double CAGR = Math.pow(finalBalance / initialBalance, 1 / timePeriod) - 1;
        return CAGR * 100; // Convert to percentage
    }

    //
    // // Get SharpeRatio of the portfolio
    // // The Sharpe Ratio is a measure of the risk-adjusted return of a portfolio.
    public double calcSharpeRatio(double expectedReturn, double riskFreeRate, double standardDeviation) {
        // Example: 10% expected return
        // Example: 3% risk-free rate
        // Example: 15% standard deviation
        return (expectedReturn - riskFreeRate) / standardDeviation;
    }

    //
    // // Calculate portfolio's standard deviation
    public double calcStandardDeviation(double[] stockReturns) {
        return Math.sqrt(calcVariance(stockReturns));
    }

    //
    // // Calculate portfolio's variance
    public double calcVariance(double[] stockReturns) {
        double sum = 0.0;
        double mean = calcMean(stockReturns);
        for (double stockReturn : stockReturns) {
            sum += Math.pow(stockReturn - mean, 2.0);
        }
        return sum / (stockReturns.length - 1);
    }

    //
    // // Calculate portfolio's mean
    public double calcMean(double[] stockReturns) {
        double sum = 0.0;
        for (double stockReturn : stockReturns) {
            sum += stockReturn;
        }
        return sum / stockReturns.length;
    }

    //
    // // Calculate portfolio's expected return
    public double calculateExpectedReturn(double[] assetReturns, double[] weights) {
        // //The expected return of a portfolio is a weighted sum of the expected
        // returns of its individual assets,
        // //where the weights represent the proportion of each asset in the portfolio
        // if (assetReturns.length != weights.length) {
        // // TODO - Handles exceptions
        // throw new IllegalArgumentException("Arrays must have the same length.");
        // }
        double expectedReturn = 0;

        for (int i = 0; i < assetReturns.length; i++) {
            expectedReturn += assetReturns[i] * weights[i];
        }
        return expectedReturn;
    }

    public GetPortfolioDetails getPortfolioDetails(Integer porfolioId) {
        System.out.println("In portfolio posting service");

        // create a GetPortfoilioDetails json model
        GetPortfolioDetails portfolioDetails = new GetPortfolioDetails();

        // create AssetModel json model
        AssetModel assetModel = new AssetModel(0, null);

        // create AssetAllocation json model
        AssetsAllocation assetsAllocation = new AssetsAllocation();
        Map<String, AssetModel> assetMap = new HashMap<>();

        // create PerformanceSummary json model
        // PerformanceSummary performanceSummary = new PerformanceSummary(0, 0, 0, 0, 0,
        // 0);

        // get existing portfolio by portfoloioId
        Portfolio portfolio = portfolioDAO.findByPortfolioId(porfolioId);

        // Loop through assets of existing portfolio
        List<String> sectors = new ArrayList<>();
        for (Asset asset : portfolio.getAssets()) {
            // Check if sector already exists in sectors list before adding it
            if (!sectors.contains(asset.getSector())) {
                sectors.add(asset.getSector());
            }
        }
        System.out.println(sectors);

        for (String sector : sectors) {
            double totalAllocation = 0;
            List<Stock> stocks = new ArrayList<>();

            // code block
            for (Asset asset : portfolio.getAssets()) {
                if (asset.getSector().equals(sector)) {
                    // Get the value of the asset here
                    Double allocation = asset.getAllocation();
                    totalAllocation += allocation;

                    // Create a list of stocks
                    String symbol = asset.getAssetId().getStockSymbol();
                    stocks.add(new Stock(symbol, allocation));
                }
            }
            // Create an AssetModel instance
            AssetModel am = new AssetModel(totalAllocation, stocks);
            System.out.println(am);
        }

        for (Asset asset : portfolio.getAssets()) {
            // populate json models less GetPortfolioDetails

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
                7.77, // CAGR
                0.59, // SharpeRatio
                0.42 // SortinoRatio
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

    public AssetsAllocation getAssetsAllocation(Integer portfolioId) {

        // create AssetAllocation json model
        AssetsAllocation assetsAllocation = new AssetsAllocation();
        Map<String, AssetModel> assetMap = new HashMap<>();

        // get existing portfolio by portfoloioId
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        // Loop through assets of existing portfolio
        List<String> sectors = new ArrayList<>();
        for (Asset asset : portfolio.getAssets()) {
            // Check if sector already exists in sectors list before adding it
            if (!sectors.contains(asset.getSector())) {
                sectors.add(asset.getSector());
            }
        }
        System.out.println(sectors);

        for (String sector : sectors) {
            double totalAllocation = 0;
            List<Stock> stocks = new ArrayList<>();

            // code block
            for (Asset asset : portfolio.getAssets()) {
                if (asset.getSector().equals(sector)) {
                    // Get the value of the asset here
                    Double allocation = asset.getAllocation();
                    totalAllocation += allocation;

                    // Create a list of stocks
                    String symbol = asset.getAssetId().getStockSymbol();
                    stocks.add(new Stock(symbol, allocation));
                }
            }
            // Create an AssetModel instance
            AssetModel am = new AssetModel(totalAllocation, stocks);
            // System.out.println(am);
            assetMap.put(sector, am);
            System.out.println(assetMap);
        }
        assetsAllocation.setAssets(assetMap);
        System.out.println(assetsAllocation);
        return assetsAllocation;
    }

    public PerformanceSummary getPerformanceSummary(Integer portfolioId) {
        PerformanceSummary performanceSummary = new PerformanceSummary(0, 0, 0, 0, 0, 0);

        double netProfit = this.getNetProfit(portfolioId);
        performanceSummary.setNetProfit(netProfit);

        double finalBalance = this.getPortfolioFinalBalance(portfolioId);
        performanceSummary.setFinalBalance(finalBalance);

        double cagr = this.getCAGR(portfolioId);
        performanceSummary.setCAGR(cagr);

        // double stdDev = this.calcStandardDeviation()

        return performanceSummary;
    }

}
