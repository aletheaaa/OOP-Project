 package is442.portfolioAnalyzer.Asset;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

 @RestController
 @RequestMapping("asset")
 public class AssetController {

     @Autowired
     AssetService assetService;

     @GetMapping("allAssets")
     public List<Asset> getAllAssets(){
         System.out.println("In controller");
         return assetService.getAllAssets();
     }


     @GetMapping("getAssetsByPortfolioName/{portfolioName}")
     public List<Asset> getAssetsByPortfolioName(@PathVariable String portfolioName){
         System.out.println("In controller");
         return assetService.getAssetsByPortfolioName(portfolioName);
     }
 }
