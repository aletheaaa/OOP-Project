 package is442.portfolioAnalyzer.Asset;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 import java.util.List;

 @RestController
 @RequestMapping("asset")
 @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
 public class AssetController {

     @Autowired
     AssetService assetService;

     @GetMapping("allAssets")
     public List<Asset> getAllAssets(){
         System.out.println("In controller");
         return assetService.getAllAssets();
     }


     
     @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
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
