package is442.portfolioAnalyzer.Stock;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.io.ObjectInputFilter;

@Service
public class StockService {

    // GET stock price from API
    public ResponseEntity<?> getStockPrice(String symbol) {

        // Get the response from API
        Config cfg = Config.builder()
                .key("HYFJ1Q80KH14F95D")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .fetchSync();
        // TODO: CATCH EXCEPTIONS
    return ResponseEntity.ok(response);
    }


    // Update the latest price in database
    public ResponseEntity<?> updateStockPrice(String symbol) {
        ResponseEntity<?> response = getStockPrice(symbol);
        try {
            // Parsing the response to the specific stock format
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject body = (JSONObject) jsonObject.get("body");
            JSONObject metaData = (JSONObject) body.get("metaData");
            String symb = (String) metaData.get("symbol");
            String lastUpdatedDate = (String) metaData.get("lastRefreshed");
            JSONArray stockUnits = (JSONArray) body.get("stockUnits");

            JSONObject latest = (JSONObject) stockUnits.get(0);
            double lastUpdatedPrice = (double) latest.get("close");
            Stock stock = new Stock();
            stock.setStockSymbol(symb);
            stock.setLastUpdatedTimeDate(lastUpdatedDate);
            stock.setLastUpdatedPrice(lastUpdatedPrice);
            return ResponseEntity.ok(stock);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Stock price updated!");
    }

}
