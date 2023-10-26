package is442.portfolioAnalyzer.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataPopulator implements ApplicationRunner {
    private final StockService stockService;

    @Autowired
    public DataPopulator(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String excelFilePath = "src/main/java/is442/portfolioAnalyzer/Stock/AllStocksAvailable.xlsx";
        stockService.populateTableFromExcel(excelFilePath);
    }
    
    
}
