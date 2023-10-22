package is442.portfolioAnalyzer.ExternalApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("apii")
@RestController
public class ExternalApiController {

    @Autowired
    ExternalApiService externalApiService;

    @GetMapping("{symbol}")
    public ResponseEntity<?> getDailyStockPrice(@PathVariable String symbol) {
        // return ResponseEntity.ok("Connect to Portfolio Service!");
        System.out.println("In controller");
        return ResponseEntity.ok(externalApiService.getDailyStockPrice(symbol));

    }

}
