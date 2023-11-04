package is442.portfolioAnalyzer.Portfolio;
import is442.portfolioAnalyzer.JsonModels.*;
import is442.portfolioAnalyzer.Exception.PortfolioNameNotUniqueException;
import is442.portfolioAnalyzer.Exception.UserPortfolioNotMatchException;
import is442.portfolioAnalyzer.User.User;
import is442.portfolioAnalyzer.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is442.portfolioAnalyzer.Stock.*;

import is442.portfolioAnalyzer.Asset.*;

import java.time.Year;
import java.util.*;
import java.math.BigDecimal;

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

    @Autowired
    StockDAO stockDAO;


    // RETRIEVING PORTFOLIO DETAILS
    // ---------------------------------------------------------------------------------------------------


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
        // Get the portfolioName, capital, description, startDate and add
        // into portfolio
        portfolio.setPortfolioName(portfolioCreation.getPortfolioName());
        portfolio.setCapital(portfolioCreation.getCapital());
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

            // Get the industry,sector and country of the asset
            if (symbol.equals("CASHALLOCATION")) {
                asset.setSector("CASHALLOCATION");
                asset.setIndustry("CASHALLOCATION");
                asset.setCountry("CASHALLOCATION");

            } else {
                Stock stock = stockDAO.findBySymbol(symbol);
                String sector = stock.getSector();
                asset.setSector(sector);

                // Get the industry of the asset
                String industry = stock.getIndustry();
                asset.setIndustry(industry);

                // Get the country of the asset
                String country = stock.getCountry();
                asset.setCountry(country);
            }


            // Set the allocation of the asset
            // asset.setAllocation(assetCreation.getAllocation());

            // Set Monthly Prices of asset from API
            // assetService.updateMonthlyPrices(asset, symbol);

            if (!assetCreation.getSymbol().equals("CASHALLOCATION")) {
                // Call External API to get the latest price
                // asset.setUnitPrice(assetService.getAssetLatestPrice(symbol));
                // Add the quantity purchased based on the portfolio capital and asset
                // allocation
                asset.setQuantityPurchased(portfolioCreation.getCapital() * assetCreation.getAllocation()
                        / assetService.getAssetLatestPrice(symbol));

                // Calculate the total value of the asset
                // asset.setTotalValue(assetService.getLatestPrice(symbol) * asset.getQuantityPurchased());
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
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        // Set the portfolio capital, description, startDate, timePeriod
        portfolio.setPortfolioName(portfolioUpdate.getPortfolioName());
        portfolio.setCapital(portfolioUpdate.getCapital());
        portfolio.setDescription(portfolioUpdate.getDescription());
        portfolio.setStartDate(portfolioUpdate.getStartDate());

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
                        // double oldTotalValue = asset.getTotalValue();
                        // Update the asset
                        // asset.setAllocation(assetCreation.getAllocation());
                        // asset.setTotalValue(assetCreation.getAllocation() * portfolioUpdate.getCapital());

                        if (!assetCreation.getSymbol().equals("CASHALLOCATION")) {
                            // Update the quantity purchased based on the portfolio capital and asset
                            // allocation
                            // double oldQuantityPurchased = asset.getQuantityPurchased();
                            // double stockTotalValueDifference = asset.getTotalValue() - oldTotalValue;

                            // Get the additional stock quantity purchased or sold
                            // double stockPurchasedOrSold = stockTotalValueDifference
                            //         / assetService.getAssetLatestPrice(symbol);

                            asset.setQuantityPurchased(assetCreation.getAllocation() * portfolioUpdate.getCapital()
                                    / assetService.getAssetLatestPrice(symbol));

                            // asset.setUnitPrice(asset.getTotalValue() / asset.getQuantityPurchased());
                        }

                        System.out.println(symbol);
                        System.out.println(assetCreation.getAllocation() + " updated");
                        System.out.println(assetCreation.getAllocation() * portfolioUpdate.getCapital() + " updated");
                        System.out.println(asset.getQuantityPurchased() + " updated");
                        System.out.println(assetService.getLatestPrice(symbol) + " updated");
                        // System.out.println(asset.getTotalValue() + " updated");

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


                    // Get the sector of the asset
                    Stock stock = stockDAO.findBySymbol(symbol);
                    String sector = stock.getSector();

                    //Get the industry of the asset
                    String industry = stock.getIndustry();

                    //Get the country of the asset
                    String country = stock.getCountry();

                    // Set the sector, industry, country of the asset
                    newAsset.setSector(sector);
                    newAsset.setIndustry(industry);
                    newAsset.setCountry(country);


                
                    // newAsset.setAllocation(assetCreation.getAllocation());
                    // newAsset.setTotalValue(assetCreation.getAllocation() * portfolioUpdate.getCapital());
                    // newAsset.setUnitPrice(assetService.getLatestPrice(symbol));
                    newAsset.setQuantityPurchased(assetCreation.getAllocation() * portfolioUpdate.getCapital() / assetService.getLatestPrice(symbol));

                    System.out.println(symbol + " added");
                    // System.out.println(newAsset.getAllocation() + " added");
                    // System.out.println(newAsset.getTotalValue() + " added");
                    System.out.println(newAsset.getQuantityPurchased() + " added");
                    System.out.println(assetService.getLatestPrice(symbol) + " added");
                    // System.out.println(newAsset.getTotalValue() + " added");

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

    // PORTFOLIO PERFORMANCE CALCULATIONS
    // ---------------------------------------------------------------------------------------------------

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
    public Map<String, Map<String, Integer>> getPortfolioMonthlyGrowth(int portfolioId, String startYear, String startMonth) {
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
            int startMonthOfYear = (year == startYearInt) ? startMonthInt : 1;
            int endMonth = (year == currentYear) ? currentMonth : 12;

            for (int month = startMonthOfYear; month <= endMonth; month++) {
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
            totalAssetsValue += assetService.getAssetTotalValue(asset);
        }
        double netProfit = totalAssetsValue - portfolio.getCapital();
        return netProfit;

    }


    
    // // Get portfolio final balance at this current point of time
    public double getPortfolioFinalBalance(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        List<Asset> assets = portfolio.getAssets();
        double totalValue = 0;
        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            totalValue += assetService.getAssetTotalValue(asset);
        }
        return totalValue;
    }

    // Get the portfolio's CAGR ----------------------------------------------------------------------------------------
    public double getCAGR(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        double finalBalance = getPortfolioFinalBalance(portfolioId);
        double initialBalance = portfolio.getCapital();
        // Calculate the time period from current
        int currentYearValue = Year.now().getValue();
        int startYearValue = Integer.parseInt(portfolio.getStartDate().substring(0, 4)); // get the year e.g. 2005
        int timePeriod = currentYearValue - startYearValue;
        double CAGR = Math.pow(finalBalance / initialBalance, 1.0 / timePeriod) - 1;
        return CAGR * 100; // Convert to percentage
    }


    // Get the portfolio's SharpeRatio ----------------------------------------------------------------------------------------
    public double getSharpeRatio(int portfolioId, double expectedReturn, double riskFreeRate, double standardDeviation) {
        // Example: 10% expected return
        // Example: 3% risk-free rate
        // Example: 15% standard deviation
        return (expectedReturn - riskFreeRate) / standardDeviation;
    }

    // Get Portfolio's Standard Deviation ---------------------------------------------------------------------------
    // Formula: Square root of the variance.
    // Variance is calculated by sum of (annual returns  - mean returns) ^2
    public double getPortfolioStandardDeviation(int portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        // Calculate the time period from current
        int currentYearValue = Year.now().getValue();

        // Calculate the average returns of the portfolio
        Map<String,Double> annualReturns = getPortfolioAnnualReturns(portfolioId, String.valueOf(currentYearValue));
        int numYear = annualReturns.size();
        double totalAnnualReturns = 0;
        for (Map.Entry<String, Double> entry : annualReturns.entrySet()) {
            totalAnnualReturns += entry.getValue();
        }
        double averageAnnualReturns = totalAnnualReturns / numYear;

        // Calculate the variance
        double marginOfError = 0;
        for (Map.Entry<String, Double> entry : annualReturns.entrySet()) {
            marginOfError += Math.pow(entry.getValue() - averageAnnualReturns, 2);
        }
        double variance = marginOfError / numYear;

        return Math.sqrt(variance) * 100;
    }

    // Get Portfolio's Expected Returns -----------------------------------------------------------------------------------
    // Formula: Total annual returns / number of years
    public double getPortfolioExpectedReturns(int portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        Map <String,Double> annualReturns = getPortfolioAnnualGrowth(portfolioId, portfolio.getStartDate().substring(0, 4));
        int numYear = annualReturns.size();
        double totalAnnualReturns = 0;
        for (Map.Entry<String, Double> entry : annualReturns.entrySet()) {
            totalAnnualReturns += entry.getValue();
        }
        return totalAnnualReturns / numYear;
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

        //Final Balance of the portfolio
        double finalBalance = getPortfolioFinalBalance(portfolioId);


        for (String sector : sectors) {
            double totalAllocation = 0;
            List<StockModel> stocks = new ArrayList<>();

            // code block
            for (Asset asset : portfolio.getAssets()) {
                if (asset.getSector().equals(sector)) {
                    // Get the value of the asset here
                    Double allocation = assetService.getAssetAllocation(asset, finalBalance);
                    totalAllocation += allocation;

                    // Create a list of stocks
                    String symbol = asset.getAssetId().getStockSymbol();
                    stocks.add(new StockModel(symbol, allocation));
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

        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
        double initialBalance = portfolio.getCapital();
        performanceSummary.setInitialBalance(initialBalance);

        double cagr = this.getCAGR(portfolioId);
        performanceSummary.setCAGR(cagr);

        // double stdDev = this.calcStandardDeviation()

        return performanceSummary;
    }

    // Get portfolio details by portfolioId
    //Returns the columns of the portfolio table of that particular portfolio
    public Map<String, Object> getPortfolioDetails(Integer portfolioId) {
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);

        if (portfolio == null) {
            // Handle the case where the portfolio with the given ID is not found.
            return null;
        }

        Map<String, Object> portfolioDetails = new HashMap<>();
        portfolioDetails.put("capital", portfolio.getCapital());
        portfolioDetails.put("description", portfolio.getDescription());
        portfolioDetails.put("portfolio_name", portfolio.getPortfolioName());
        portfolioDetails.put("start_date", portfolio.getStartDate());
        portfolioDetails.put("user_id", portfolio.getUser().getId()); // Assuming you want to include user ID

        return portfolioDetails;
    }

    public Map<String, Double> getIndustryAllocation(Integer portfolioId){
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
       

        List<String> industries = new ArrayList<>();
        for (Asset asset : portfolio.getAssets()) {
            // Check if industry already exists in industries list before adding it
            if (!industries.contains(asset.getIndustry())) {
                industries.add(asset.getIndustry());
            }
        }
        //Final Balance of the portfolio
        double finalBalance = getPortfolioFinalBalance(portfolioId);

         Map<String, Double> industryDetails = new HashMap<>();
         for (String industry : industries) {
            double totalAllocation = 0;
             for (Asset asset : portfolio.getAssets()) {
                if(asset.getIndustry().equals(industry)){
                    Double allocation = assetService.getAssetAllocation(asset, finalBalance);
                    totalAllocation += allocation;
                    industryDetails.put(industry,totalAllocation);
                }
            }
         }
       
        return industryDetails;
    }

    public Map<String, Double> getCountryAllocation(Integer portfolioId){
        Portfolio portfolio = portfolioDAO.findByPortfolioId(portfolioId);
       

        List<String> countries = new ArrayList<>();
        for (Asset asset : portfolio.getAssets()) {
            // Check if country already exists in countries list before adding it
            if (!countries.contains(asset.getCountry())) {
                countries.add(asset.getCountry());
            }
        }

        //Final Balance of the portfolio
        double finalBalance = getPortfolioFinalBalance(portfolioId);

         Map<String, Double> countryDetails = new HashMap<>();
         for (String country : countries) {
            double totalAllocation = 0;
             for (Asset asset : portfolio.getAssets()) {
                if(asset.getCountry().equals(country)){
                    double allocation = assetService.getAssetAllocation(asset, finalBalance);
                    totalAllocation += allocation;
                    countryDetails.put(country,totalAllocation);
                }
            }
         }
       
        return countryDetails;
    }

}