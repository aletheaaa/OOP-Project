 package is442.portfolioAnalyzer.Asset;

 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import jakarta.annotation.PostConstruct;
import lombok.Data;

@Service
@Data
public class AssetService {

    @Autowired
    AssetMonthlyPriceDAO assetMonthlyPriceDAO;

    @Autowired
    AssetDAO assetDAO;

    @Autowired
    ExternalApiService externalApiService;



    @PostConstruct
    public void updateClosingPricesOnStartup() {

        // Check if the AssetMonthlyPrices table is empty or not initialized.
        if (isAssetMonthlyPricesTableEmpty()) {
            // If the table is empty or not initialized, skip the update process.
            return;
        }

        
        // Get the current year and month
        int currentYear = Year.now().getValue();
        int currentMonth = MonthDay.now().getMonthValue();

        // Loop through your assets or symbols and update closing prices
        List<String> symbols = getUniqueSymbols(); // Implement this method to get unique symbols

        for (String symbol : symbols) {
            double[] priceAndDivident = getAssetLatestPriceAndDivident(symbol);

            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(String.valueOf(currentYear));
            id.setMonth(getMonthName(currentMonth-1)); // Adjust for 0-based index
            id.setStockSymbol(symbol);

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceDAO.findById(id);
            // System.out.println("TRIGGERED");
            if (assetMonthlyPriceOptional.isPresent()) {
                AssetMonthlyPrice assetMonthlyPrice = assetMonthlyPriceOptional.get();
                assetMonthlyPrice.setClosingPrice(priceAndDivident[0]);
                assetMonthlyPrice.setDividendAmount(priceAndDivident[1]);
                assetMonthlyPriceDAO.save(assetMonthlyPrice);
            }
        }
    }

    public List<Asset> getAllAssets(){
        System.out.println("In service");
        return assetDAO.findAll();
    }



    public List<Asset> getAssetsByPortfolioId(Integer portfolioId ){
        System.out.println("In controller");
        return assetDAO.findByAssetIdPortfolioId(portfolioId);
    }

    //Get Asset's Total Value by symbol
    public double getAssetTotalValue(Asset asset){
        // Get the latest price of the asset
        double latestPrice = getLatestPrice(asset.getAssetId().getStockSymbol());

        //Get Quantity purchased of the asset
        double quantityPurchased = asset.getQuantityPurchased();

        double totalValue = latestPrice * quantityPurchased;

        return totalValue;
    }

    //Get Asset's Current Allocation (Percentage) e.g. 0.5 
    public double getAssetAllocation(Asset asset, double portfolioFinalBalance) {


        // Get the total value of the asset
        double assetValue = getAssetTotalValue(asset);

        // Calculate the allocation
        double allocation = assetValue / portfolioFinalBalance;

        return allocation;
    }

    //Get Asset Latest Price by symbol from Database
     public double getLatestPrice(String symbol) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Adjust for 0-based index
        String currentMonthName = getMonthName(currentMonth - 1); // Adjust for 0-based index

        // Use the AssetMonthlyPriceDAO to fetch the latest price by symbol, year, and month
        Optional<Double> latestPriceOptional = assetMonthlyPriceDAO.findLatestPriceBySymbolAndYearAndMonth(
            symbol,
            Integer.toString(currentYear),
            currentMonthName
        );

        // If a price is found, return it; otherwise, return 0.0
        return latestPriceOptional.orElse(0.0);
    }



    //Get Asset Price by symbol from API
    public double getAssetLatestPrice(String symbol) {
        try {
            TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getDailyStockPrice(symbol).getBody();

            return response.getStockUnits().get(0).getClose();
        } catch (Exception e) {
            // TODO
            // throw exception if symbol not found

            return 0;
        }
    }

    //Get Asset Latest Price and Divident Amount by symbol from API
    public double[] getAssetLatestPriceAndDivident(String symbol) {
        try {
            TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getDailyStockPrice(symbol).getBody();

            
            double[] priceAndDivident = new double[2];
            priceAndDivident[0] = response.getStockUnits().get(0).getClose();
            priceAndDivident[1] = response.getStockUnits().get(0).getDividendAmount();

            return priceAndDivident;
        } catch (Exception e) {
            // TODO
            // throw exception if symbol not found

            return null;
        }
    }



    // Get value of asset at the end of the specified year
    public double getAssetValueByYear(String symbol, String year, Asset asset) {
        try {
            // Use your AssetMonthlyPriceDAO to retrieve the asset price for the specified year.
            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(year);
            id.setStockSymbol(symbol);

            // Check if the input year is the current year.
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year.equals(Integer.toString(currentYear))) {
                // Get the current month.
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                // Set the month to the current month (adjust for 0-based index).
                id.setMonth(getMonthName(currentMonth));
                System.out.println("Current month: " + getMonthName(currentMonth));
            } else {
                // Set the month to "December" for other years.
                id.setMonth("December");
            }

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceDAO.findById(id);

            if (assetMonthlyPriceOptional.isPresent()) {
                // Asset price for the specified year is found in the database.
                double closingPrice = assetMonthlyPriceOptional.get().getClosingPrice();
                double quantityPurchased = asset.getQuantityPurchased();
                return closingPrice * quantityPurchased;
            } else {
                // Handle the case where data for the specified year is not found in the database.
                return 0;
            }
        } catch (Exception e) {
            // Handle exceptions appropriately.
            return 0;
        }
    }



    // Get value of asset at the end of the specified year and month
    public double getAssetValueByYearAndMonth(String symbol, String year, String month, Asset asset) {
        try {
            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(year);
            id.setMonth(month);
            id.setStockSymbol(symbol);

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceDAO.findById(id);

            if (assetMonthlyPriceOptional.isPresent()) {
                // Asset price for the specified year and month is found in the database.
                double closingPrice = assetMonthlyPriceOptional.get().getClosingPrice();
                double quantityPurchased = asset.getQuantityPurchased();
                return closingPrice * quantityPurchased;
            } else {
                // Handle the case where data for the specified year and month is not found in the database.
                return 0;
            }
        } catch (Exception e) {
            // Handle exceptions appropriately.
            return 0;
        }
    }



    //Populate AssetMonthlyPrice Table of the specified stock with all the monthly prices from the api service
    public void populateAssetMonthlyPrices(String symbol) {

        // Check if the symbol already exists in the AssetMonthlyPrice table
        boolean symbolExists = assetMonthlyPriceDAO.existsByIdStockSymbol(symbol);
        if (symbolExists) {
            return; // Symbol already exists, no need to update
        }

        // Call the external API servic e to get monthly stock prices
        TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getMonthlyStockPrice(symbol).getBody();
        

        if (response != null && response.getStockUnits() != null) {
            List<AssetMonthlyPrice> monthlyPrices = new ArrayList<>();
            for (StockUnit stockUnit : response.getStockUnits()) {
                AssetMonthlyPrice monthlyPrice = new AssetMonthlyPrice();

                // Extract year and month from the date
                String date = stockUnit.getDate();
                String[] dateParts = date.split("-");
                if (dateParts.length >= 2) {
                    String year = dateParts[0];
                    String monthNumber = dateParts[1];
                    String monthName = convertMonthNumberToName(monthNumber);

                    monthlyPrice.setId(new AssetMonthlyPriceId(year, monthName, symbol));
                }

                monthlyPrice.setClosingPrice(stockUnit.getClose());
                monthlyPrice.setDividendAmount(stockUnit.getDividendAmount());

                monthlyPrices.add(monthlyPrice);
            }
            System.out.println(response.getStockUnits());
            System.out.println("IT WORKS TILL HERE");

            assetMonthlyPriceDAO.saveAll(monthlyPrices);
        }
    }

    // Get the Total price of the asset by symbol and date
    //  public Double getTotalValue(Asset asset){
    //     return asset.getQuantityPurchased() * asset.getUnitPrice();
    // }

    // HELPER METHODS------------------------------------------------------------------------------------------------
    // Helper method to get month name based on its number (0-based index).
    private String getMonthName(int month) {
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
    }


    public List<String> getUniqueSymbols() {
        // Use JPA to query the database for unique symbols
        List<String> uniqueSymbols = assetMonthlyPriceDAO.findDistinctStockSymbols();
        return uniqueSymbols;
    }



    public String convertMonthNumberToName(String monthNumber) {
        // Create an array of month names
        String[] monthNames = new String[] {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        // Convert the month number to an integer and use it as an index
        int index = Integer.parseInt(monthNumber) - 1;
        if (index >= 0 && index < monthNames.length) {
            return monthNames[index];
        }

        return monthNumber; // If the conversion fails, return the original number
    }


    public boolean isAssetMonthlyPricesTableEmpty() {
        long recordCount = assetMonthlyPriceDAO.count();
        return recordCount == 0;
    }

}
