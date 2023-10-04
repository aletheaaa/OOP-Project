package is442.portfolioAnalyzer.Stock;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    StockDAO stockDAO;

    // GET stock price from API
    public ResponseEntity<?> updateStockLatestPrice(String symbol) {

        // Get the response from API
        Config cfg = Config.builder()
                .key("HYFJ1Q80KH14F95D")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .daily()
                .dataType(DataType.JSON)
                .forSymbol(symbol)
                .fetchSync();
        // TODO: CATCH EXCEPTIONS

        // Extract the relevant information from the response
        double lastUpdatedPrice =  response.getStockUnits().get(0).getClose();
        String lastUpdatedTime = response.getStockUnits().get(0).getDate();
        String stockSymbol = response.getMetaData().getSymbol();

        // Save the stock information to the database
        Stock stock = new Stock();
        stock.setStockSymbol(stockSymbol);
        stock.setLastUpdatedPrice(lastUpdatedPrice);
        stock.setLastUpdatedTimeDate(lastUpdatedTime);
        stockDAO.save(stock);

    return ResponseEntity.ok(response);
    }



}
