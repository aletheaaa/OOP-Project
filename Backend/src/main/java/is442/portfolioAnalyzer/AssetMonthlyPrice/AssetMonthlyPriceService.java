package is442.portfolioAnalyzer.AssetMonthlyPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;

@Service
public class AssetMonthlyPriceService {

    @Autowired
    AssetMonthlyPriceDAO assetMonthlyPriceDAO;

    @Autowired
    ExternalApiService externalApiService;

    //Populate AssetMonthlyPrice Table of the specified stock with all the monthly prices from the api service
    public void populateAssetMonthlyPrices(String symbol) {

        // Check if the symbol already exists in the AssetMonthlyPrice table
        boolean symbolExists = assetMonthlyPriceDAO.existsByIdStockSymbol(symbol);
        if (symbolExists) {
            return; // Symbol already exists, no need to update
        }

        // Call the external API servic e to get monthly stock prices
        TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getMonthlyStockPrice(symbol).getBody();
        
        if (response != null && response.getStockUnits() != null) {
            List<AssetMonthlyPrice> monthlyPrices = new ArrayList<>();
            for (StockUnit stockUnit : response.getStockUnits()) {
                AssetMonthlyPrice monthlyPrice = new AssetMonthlyPrice();

                // Extract year and month from the date
                String date = stockUnit.getDate();
                String[] dateParts = date.split("-");
                if (dateParts.length >= 2) {
                    String year = dateParts[0];
                    String monthNumber = dateParts[1];
                    String monthName = convertMonthNumberToName(monthNumber);

                    monthlyPrice.setId(new AssetMonthlyPriceId(year, monthName, symbol));
                }

                monthlyPrice.setClosingPrice(stockUnit.getClose());
                monthlyPrice.setDividendAmount(stockUnit.getDividendAmount());
                monthlyPrices.add(monthlyPrice);
            }

            assetMonthlyPriceDAO.saveAll(monthlyPrices);
        }
    }

    public Optional<AssetMonthlyPrice> findAssetMonthlyPriceById(AssetMonthlyPriceId id) {
        return assetMonthlyPriceDAO.findById(id);
     }

    public Optional<Double> findLatestPriceBySymbolAndYearAndMonth(String symbol, String year, String month){
        return assetMonthlyPriceDAO.findLatestPriceBySymbolAndYearAndMonth(symbol, year, month);
    }

    public void saveAssetMonthlyPrice(AssetMonthlyPrice assetMonthlyPrice) {
        assetMonthlyPriceDAO.save(assetMonthlyPrice);
    }

    // Helper Method
    public String convertMonthNumberToName(String monthNumber) {
        // Create an array of month names
        String[] monthNames = new String[] {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        // Convert the month number to an integer and use it as an index
        int index = Integer.parseInt(monthNumber) - 1;
        if (index >= 0 && index < monthNames.length) {
            return monthNames[index];
        }

        return monthNumber; // If the conversion fails, return the original number
    }

    public List<String> findDistinctStockSymbols() {
        return assetMonthlyPriceDAO.findDistinctStockSymbols();
    }

    //Get count of records in Asset Monthly Price Table
    public long getCount(){
        return assetMonthlyPriceDAO.count();
    }
}
