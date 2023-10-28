package is442.portfolioAnalyzer.Stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is442.portfolioAnalyzer.Portfolio.Portfolio;

@RequestMapping("stock")
@RestController
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("allStockSymbols")
    public List<String> getAllStockSymbols() {
        return stockService.getAllStockSymbols();
    }
}
