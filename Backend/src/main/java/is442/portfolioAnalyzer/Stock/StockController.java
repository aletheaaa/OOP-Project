package is442.portfolioAnalyzer.Stock;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {

    @Autowired
    StockService stockService;
    @Autowired
    ExternalApiService externalApiService;

    @GetMapping("getDailyStockPrice/{symbol}")
    public ResponseEntity<?> getStockDailyPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(externalApiService.getDailyStockPrice(symbol));
    }

    @GetMapping("getMonthlyStockPrice/{symbol}")
    public ResponseEntity<?> getStockMonthlyPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(externalApiService.getMonthlyStockPrice(symbol));
    }


    // This method is used to get the stock price of a particular stock symbol From API
    @GetMapping("updateLatestStockPrice/{symbol}")
    public ResponseEntity<?> updateStockLatestPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(stockService.updateStockLatestPrice(symbol));
    }
    
}
