package is442.portfolioAnalyzer.Stock;

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


    // This method is used to get the stock price of a particular stock symbol From API
    @GetMapping("{symbol}")
    public ResponseEntity<?> updateStockLatestPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(stockService.updateStockLatestPrice(symbol));
    }

//    @GetMapping("{symbol}")
//    public ResponseEntity<?> updateStockPrice(@PathVariable String symbol) {
//        return ResponseEntity.ok(stockService.updateStockPrice(symbol));
//    }
    
}
