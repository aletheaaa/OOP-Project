 package is442.portfolioAnalyzer.Asset;

 import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
        TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getMonthlyStockPrice(symbol).getBody();
        List<StockUnit> stockUnits = response.getStockUnits();
        Double quantityPurchased = asset.getQuantityPurchased();
        
        if (stockUnits.isEmpty()) {
            // Handle the case where there's no stock data available.
            return 0;
        }

        // Find the earliest year in the stock data.
        String earliestYear = stockUnits.get(stockUnits.size() - 1).getDate().split("-")[0];

        // Check if the requested year is greater than or equal to the earliest year.
        if (Integer.parseInt(year) < Integer.parseInt(earliestYear)) {
            // Handle the case where the requested year is earlier than the available data.
            return 0;
        }

        for (StockUnit stockUnit : stockUnits) {
            String stockUnitDate = stockUnit.getDate();
            String[] dateParts = stockUnitDate.split("-");
            String stockYear = dateParts[0];
            if (stockYear.equals(year)) {
                return stockUnit.getClose() * quantityPurchased;
            }
        }
        return 0; // Year not found in the available data.
    } catch (Exception e) {
        // Handle exceptions appropriately.
        return 0;
    }
}



        //Get value of asset of of specifed year and month
        public double getAssetMonthlyPriceBySymbolAndDate(String symbol, String date) {
            try {
                TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getMonthlyStockPrice(symbol).getBody();
                // System.out.println(response);
                List<StockUnit> stockUnits = response.getStockUnits();
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
                
                for (StockUnit stockUnit : stockUnits) {
                    String stockUnitDate = stockUnit.getDate();
                    Date parsedDate = inputFormat.parse(stockUnitDate);
                    String formattedDate = outputFormat.format(parsedDate);
                    if (formattedDate.equals(date)) {
                        return stockUnit.getClose();
                    }
                }
                return 0;
            } catch (Exception e) {
                // TODO
                // throw exception if symbol not found
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

        // Call the external API service to get monthly stock prices
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
