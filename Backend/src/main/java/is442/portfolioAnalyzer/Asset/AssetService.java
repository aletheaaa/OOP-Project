 package is442.portfolioAnalyzer.Asset;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
 public class AssetService {
    
     @Autowired
     AssetDAO assetDAO;

     public List<Asset> getAllAssets(){
         System.out.println("In service");
         return assetDAO.findAll();
     }

     public List<Asset> getAssetsByPortfolioName(String portfolioName){
         System.out.println("In controller");
         return assetDAO.findByAssetIdPortfolioName(portfolioName);
     }

 }
