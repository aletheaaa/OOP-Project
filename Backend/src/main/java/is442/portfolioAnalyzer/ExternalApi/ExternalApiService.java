package is442.portfolioAnalyzer.ExternalApi;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
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
        // TODO: CATCH EXCEPTIONS

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
        // TODO: CATCH EXCEPTIONS

        return ResponseEntity.ok(response);
    }

}
