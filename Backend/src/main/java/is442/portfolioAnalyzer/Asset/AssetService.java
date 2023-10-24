 package is442.portfolioAnalyzer.Asset;

 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
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

     public List<Asset> getAllAssets(){
         System.out.println("In service");
         return assetDAO.findAll();
     }

    

     public List<Asset> getAssetsByPortfolioId(Integer portfolioId ){
         System.out.println("In controller");
         return assetDAO.findByAssetIdPortfolioId(portfolioId);
     }

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

    // Helper method to get month name based on its number (0-based index).
    private String getMonthName(int month) {
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
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
    

//    //Get Annual Growth of an Asset by symbol
//    public double getAnnualGrowth(String symbol) {
//        // Get the latest price of the asset
//        double latestPrice = getAssetLatestPrice(symbol);
//
//        // Get the price of the asset 1 year ago
//        String date = "2020-01";
//        double priceOneYearAgo = getAssetPriceBySymbolAndDate(symbol, date);
//
//        // Calculate the annual growth
//        double annualGrowth = (latestPrice - priceOneYearAgo) / priceOneYearAgo * 100;
//
//        return annualGrowth;
//    }


    public Double getTotalValue(Asset asset){
        return asset.getQuantityPurchased() * asset.getUnitPrice();
    }
 }
