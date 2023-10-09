package is442.portfolioAnalyzer.Stock;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    StockDAO stockDAO;
    @Autowired
    ExternalApiService externalApiService;

    // GET stock price from API
    public ResponseEntity<?> updateStockLatestPrice(String symbol) {
        // Get the stock price from the API
        TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getDailyStockPrice(symbol).getBody();

        // Extract the relevant information from the response
        try {
            double lastUpdatedPrice =  response.getStockUnits().get(0).getClose();
            String lastUpdatedTime = response.getStockUnits().get(0).getDate();
            String stockSymbol = response.getMetaData().getSymbol();
            System.out.println("Stock symbol: " + stockSymbol);
            System.out.println("Last updated price: " + lastUpdatedPrice);
            System.out.println("Last updated time: " + lastUpdatedTime);

            // Save the stock information to the database
            Stock stock = new Stock();
            stock.setStockSymbol(stockSymbol);
            stock.setLastUpdatedPrice(lastUpdatedPrice);
            stock.setLastUpdatedTime(lastUpdatedTime);
            stockDAO.save(stock);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid stock symbol");
        }

    return ResponseEntity.ok(response);
    }



}
