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


     

     @GetMapping("getAssetsByPortfolioId/{portfolioId}")
     public List<Asset> getAssetsByPortfolioId(@PathVariable Integer portfolioId){
         System.out.println("In controller");
         return assetService.getAssetsByPortfolioId(portfolioId);
     }

     @GetMapping("getAssetPriceBySymbolAndDate/{symbol}/{date}")
        public double getAssetPriceBySymbolAndDate(@PathVariable String symbol, @PathVariable String date){
            System.out.println("In controller");
            return assetService.getAssetPriceBySymbolAndDate(symbol, date);
        }
 }
