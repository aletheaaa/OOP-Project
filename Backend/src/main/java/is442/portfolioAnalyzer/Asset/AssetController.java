 package is442.portfolioAnalyzer.Asset;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

 @RestController
 @RequestMapping("trade")
 public class AssetController {

     @Autowired
     AssetService assetService;

     @GetMapping("allTrades")
     public List<Asset> getAllTrades(){
         System.out.println("In controller");
         return assetService.getAllTrades();
     }


     @GetMapping("getTradesByPortfolioId/{portfolioId}")
     public List<Asset> getTradesByPortfolioId(@PathVariable Integer portfolioId){
         System.out.println("In controller");
         return assetService.getTradesByPortfolioId(portfolioId);
     }
 }
