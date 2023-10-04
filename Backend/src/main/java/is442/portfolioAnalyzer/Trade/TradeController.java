package is442.portfolioAnalyzer.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @GetMapping("allTrades")
    public List<Trade> getAllTrades(){
        System.out.println("In controller");
        return tradeService.getAllTrades();
    }
}
