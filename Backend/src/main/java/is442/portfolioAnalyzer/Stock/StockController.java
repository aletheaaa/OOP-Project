package is442.portfolioAnalyzer.Stock;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import is442.portfolioAnalyzer.Portfolio.Portfolio;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {



    @Autowired
    StockService stockService;


    //Get All Stocks and their Industries
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("getAllStocksAndIndustries")
    public ResponseEntity<Map<String, String>> getAllStocksAndIndustries() {
        Map<String, String> industryMap = stockService.getAllStocksAndIndustries();
        return ResponseEntity.ok(industryMap);
    }

    //Get All Stocks and their Countries
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("getAllStocksAndCountries")
    public ResponseEntity<Map<String, String>> getAllStocksAndCountries() {
        Map<String, String> countryMap = stockService.getAllStocksAndCountries();
        return ResponseEntity.ok(countryMap);
    }


    //Get All Stocks and their Countries
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.GET, allowCredentials = "true")
    @GetMapping("getAllStocksAndCurrencies")
    public ResponseEntity<Map<String, String>> getAllStocksAndCurrencies() {
        Map<String, String> currencyMap = stockService.getAllStocksAndCurrencies();
        return ResponseEntity.ok(currencyMap);
    }


    


    
    
}
