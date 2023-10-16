 package is442.portfolioAnalyzer.Asset;

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
     AssetDAO assetDAO;

     @Autowired
     ExternalApiService externalApiService;

     public List<Asset> getAllAssets(){
         System.out.println("In service");
         return assetDAO.findAll();
     }

     public List<Asset> getAssetsByPortfolioName(String portfolioName){
         System.out.println("In controller");
         return assetDAO.findByAssetIdPortfolioName(portfolioName);
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
    //   public List<AssetMonthlyPrice> updateMonthlyPrices(Asset asset) {
    //     try {
    //         String symbol = asset.getAssetId().getStockSymbol();
    //         ResponseEntity<?> response = externalApiService.getMonthlyStockPrice(symbol);

    //         if (response.getStatusCode().is2xxSuccessful()) {
    //             TimeSeriesResponse timeSeriesResponse = (TimeSeriesResponse) response.getBody();
    //             List<StockUnit> stockUnits = timeSeriesResponse.getStockUnits();
                
    //             for (StockUnit stockUnit : stockUnits) {
    //                 String date = stockUnit.getDate();
    //                 Double closingPrice = stockUnit.getClose();

    //                 AssetMonthlyPrice assetMonthlyPrice = new AssetMonthlyPrice();
    //                 AssetMonthlyPriceId assetMonthlyPriceId = new AssetMonthlyPriceId();
    //                 assetMonthlyPriceId.setDate(date); 
    //                 assetMonthlyPriceId.setStockSymbol(symbol);
    //                 assetMonthlyPrice.setId(assetMonthlyPriceId);(assetMonthlyPriceId);
    //                 assetMonthlyPrice.setClosingPrice(closingPrice);

    //                 asset.getMonthlyPrices().add(assetMonthlyPrice);
    //             }
                
    //            return asset.getMonthlyPrices();
    //         }
    //     } catch (Exception e) {
    //         // Handle any exceptions or errors
    //         e.printStackTrace();
    //     }
    // }

 }
