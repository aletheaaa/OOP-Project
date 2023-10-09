 package is442.portfolioAnalyzer.Asset;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;

 @Service
 public class AssetService {
    
     @Autowired
     AssetDAO assetDAO;

     public List<Asset> getAllTrades(){
         System.out.println("In service");
         return assetDAO.findAll();
     }

     public List<Asset> getTradesByPortfolioId(Integer portfolioId){
         System.out.println("In controller");
         return assetDAO.findByTradeIdPortfolioId(portfolioId);
     }

 }
