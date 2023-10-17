 package is442.portfolioAnalyzer.Asset;

 import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

     //Set all the monthly prices of the asset from api
      public void updateMonthlyPrices(Asset asset, String symbol) {
        try {
            ResponseEntity<?> response = externalApiService.getMonthlyStockPrice(symbol);

            if (response.getStatusCode().is2xxSuccessful()) {
                TimeSeriesResponse timeSeriesResponse = (TimeSeriesResponse) response.getBody();
                List<StockUnit> stockUnits = timeSeriesResponse.getStockUnits();
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
                
                for (StockUnit stockUnit : stockUnits) {
                    String date = stockUnit.getDate();
                    // Parse the date and format it as "yyyy-MM"
                    Date parsedDate = inputFormat.parse(date);
                    String formattedDate = outputFormat.format(parsedDate);
                    
                    Double closingPrice = stockUnit.getClose();

                    AssetMonthlyPrice assetMonthlyPrice = new AssetMonthlyPrice();
                    AssetMonthlyPriceId assetMonthlyPriceId = new AssetMonthlyPriceId();
                    assetMonthlyPriceId.setDate(formattedDate); 
                    assetMonthlyPriceId.setStockSymbol(symbol);
                    assetMonthlyPrice.setId(assetMonthlyPriceId);
                    assetMonthlyPrice.setClosingPrice(closingPrice);
                    assetMonthlyPriceDAO.save(assetMonthlyPrice);
                    
                    asset.getMonthlyPrices().add(assetMonthlyPrice);

                    
                }
                
               
            }
        } catch (Exception e) {
            // Handle any exceptions or errors
            System.out.println("Error in updateMonthlyPrices");
            e.printStackTrace();
        }
       
    }

 }
