package is442.portfolioAnalyzer.StockSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataPopulator implements ApplicationRunner {
    private final StockSheetService stockSheetService;

    @Autowired
    public DataPopulator(StockSheetService stockSheetService) {
        this.stockSheetService = stockSheetService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String excelFilePath = "src/main/java/is442/portfolioAnalyzer/StockSheet/AllStocksAvailable.xlsx";
        stockSheetService.populateTableFromExcel(excelFilePath);
    }
    
    
}
