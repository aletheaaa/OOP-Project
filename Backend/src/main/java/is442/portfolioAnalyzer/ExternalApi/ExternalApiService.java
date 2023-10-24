package is442.portfolioAnalyzer.ExternalApi;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.Fetcher;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    @Autowired
    private AlphaVantageConfig alphaVantageConfig;

    public ResponseEntity<?> getDailyStockPrice(String symbol) {

        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .daily()
                .dataType(DataType.JSON)
                .forSymbol(symbol)
                .fetchSync();

//        if (response.getStockUnits().isEmpty()) {
//            throw new AlphaVantageException("Server is unable to call AlphaVantage API.");
//        }
//        System.out.println(response.getStockUnits().get(0).getClose());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getMonthlyStockPrice(String symbol) {
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .monthly()
                .dataType(DataType.JSON)
                .forSymbol(symbol)
                .adjusted()
                .fetchSync();

//        if (response.getStockUnits().isEmpty()) {
//            throw new AlphaVantageException("Server is unable to call AlphaVantage API.");
//        }
        return ResponseEntity.ok(response);
    }

}
