 package is442.portfolioAnalyzer.Asset;

 import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
 import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
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

 }
