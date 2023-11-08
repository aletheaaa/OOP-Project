package is442.portfolioAnalyzer.Asset;

import java.time.MonthDay;
import java.time.Year;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import is442.portfolioAnalyzer.AssetMonthlyPrice.*;
import is442.portfolioAnalyzer.Exception.SymbolNotFoundException;
import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import jakarta.annotation.PostConstruct;
import lombok.Data;

@Service
@Data
public class AssetService {

    @Autowired
    AssetMonthlyPriceService assetMonthlyPriceService;

    @Autowired
    AssetDAO assetDAO;

    @Autowired
    ExternalApiService externalApiService;

    /*
     * Update the latest prices of all assets in the AssetMonthlyPrice table
     * This method is called when the application starts up
     */
    @PostConstruct
    public void updateClosingPricesOnStartup() {

        // Check if the AssetMonthlyPrices table is empty or not initialized.
        if (isAssetMonthlyPricesTableEmpty()) {
            // If the table is empty or not initialized, skip the update process.
            return;
        }

        // Get the current year and month
        int currentYear = Year.now().getValue();
        int currentMonth = MonthDay.now().getMonthValue();

        // Loop through your assets or symbols and update closing prices
        List<String> symbols = getUniqueSymbols(); // Implement this method to get unique symbols

        for (String symbol : symbols) {
            double[] priceAndDivident = getAssetLatestPriceAndDivident(symbol);

            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(String.valueOf(currentYear));
            id.setMonth(getMonthName(currentMonth - 1)); // Adjust for 0-based index
            id.setStockSymbol(symbol);

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceService
                    .findAssetMonthlyPriceById(id);
            // System.out.println("TRIGGERED");
            if (assetMonthlyPriceOptional.isPresent()) {
                AssetMonthlyPrice assetMonthlyPrice = assetMonthlyPriceOptional.get();
                assetMonthlyPrice.setClosingPrice(priceAndDivident[0]);
                assetMonthlyPrice.setDividendAmount(priceAndDivident[1]);
                assetMonthlyPriceService.saveAssetMonthlyPrice(assetMonthlyPrice);
            }
        }
    }

    /*
     * This method is called by the scheduler to update the closing prices of all
     * the assets in the AssetMonthlyPrice table
     * The scheduler is configured to run at 4am GMT+8 every day
     */
    @Scheduled(cron = "0 0 20 * * ?") 
    public void updateClosingPricesDaily() {
        System.out.println("Updating closing prices daily...");
        // Get the current year and month
        int currentYear = Year.now().getValue();
        int currentMonth = MonthDay.now().getMonthValue();
        String currentMonthName = getMonthName(currentMonth - 1); // Adjust for 0-based index

        // Loop through your assets or symbols and update closing prices
        List<String> symbols = getUniqueSymbols();

        for (String symbol : symbols) {
            double latestPrice = getAssetLatestPrice(symbol);

            // Update the AssetMonthlyPrice entry for the current year and month
            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(String.valueOf(currentYear));
            id.setMonth(currentMonthName);
            id.setStockSymbol(symbol);

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceService
                    .findAssetMonthlyPriceById(id);

            if (assetMonthlyPriceOptional.isPresent()) {
                AssetMonthlyPrice assetMonthlyPrice = assetMonthlyPriceOptional.get();
                assetMonthlyPrice.setClosingPrice(latestPrice);
                assetMonthlyPriceService.saveAssetMonthlyPrice(assetMonthlyPrice);
            }
        }
    }

    // Saving the asset into the DAO
    public void saveAsset(Asset asset) {
        assetDAO.save(asset);
    }

    // Delete the asset from the DAO
    public void deleteAsset(Asset asset) {
        assetDAO.delete(asset);
    }

    // Get list of assets of portfolio by portfolio id
    public List<Asset> getAssetsByPortfolioId(Integer portfolioId) {
        System.out.println("In controller");
        return assetDAO.findByAssetIdPortfolioId(portfolioId);
    }

    // Get Asset's Total Value by symbol
    public double getAssetTotalValue(Asset asset) {
        // Get the latest price of the asset
        double latestPrice = getLatestPrice(asset.getAssetId().getStockSymbol());

        // Get Quantity purchased of the asset
        double quantityPurchased = asset.getQuantityPurchased();

        if (asset.getAssetId().getStockSymbol().equals("CASHALLOCATION")) {
            return quantityPurchased;
        }

        double totalValue = latestPrice * quantityPurchased;

        return totalValue;
    }

    // Get Asset's Current Allocation (Percentage) e.g. 0.5
    public double getAssetAllocation(Asset asset, double portfolioFinalBalance) {

        // Get the total value of the asset
        double assetValue = getAssetTotalValue(asset);

        // Calculate the allocation
        double allocation = assetValue / portfolioFinalBalance;

        return allocation;
    }

    // Get Asset Latest Price by symbol from Database
    public double getLatestPrice(String symbol) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Adjust for 0-based index
        String currentMonthName = getMonthName(currentMonth - 1); // Adjust for 0-based index

        // Fetch the latest price by symbol, year, and month
        Optional<Double> latestPriceOptional = assetMonthlyPriceService.findLatestPriceBySymbolAndYearAndMonth(
                symbol,
                Integer.toString(currentYear),
                currentMonthName);

        // If a price is found, return it; otherwise, return 0.0
        return latestPriceOptional.orElse(0.0);
    }

    // Get Asset Price by symbol from API
    public double getAssetLatestPrice(String symbol) {
        try {
            TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getDailyStockPrice(symbol).getBody();

            return response.getStockUnits().get(0).getClose();

        } catch (Exception e) {
            throw new SymbolNotFoundException("Error! Symbol " + symbol + "not found!");
        }
    }

    // Get Asset Latest Price and Divident Amount by symbol from API
    public double[] getAssetLatestPriceAndDivident(String symbol) {
        try {
            TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getDailyStockPrice(symbol).getBody();

            double[] priceAndDivident = new double[2];
            priceAndDivident[0] = response.getStockUnits().get(0).getClose();
            priceAndDivident[1] = response.getStockUnits().get(0).getDividendAmount();

            return priceAndDivident;

        } catch (Exception e) {
            throw new SymbolNotFoundException("Error! Symbol " + symbol + "not found!");
        }
    }

    // Get Asset Earliest Price by symbol from API
    public double getEarliestClosingPriceFromApi(String symbol) {
        try {
            TimeSeriesResponse response = (TimeSeriesResponse) externalApiService.getMonthlyStockPrice(symbol)
                    .getBody();

            if (response != null && !response.getStockUnits().isEmpty()) {
                // Sort the stock units by date in ascending order
                response.getStockUnits().sort(Comparator.comparing(StockUnit::getDate));

                // Get the earliest closing price
                StockUnit earliestStockUnit = response.getStockUnits().get(0);
                return earliestStockUnit.getClose();
            }
        } catch (Exception e) {
            throw new SymbolNotFoundException("Error! Symbol " + symbol + "not found!");
        }

        // Return null or handle it as needed if data is not found or an error occurs.
        return 0.0;
    }

    // Get value of asset at the end of the specified year
    public double getAssetValueByYear(String symbol, String year, Asset asset) {

        if (asset.getAssetId().getStockSymbol().equals("CASHALLOCATION")) {
            return asset.getQuantityPurchased();
        }

        try {
            // Use your AssetMonthlyPriceDAO to retrieve the asset price for the specified
            // year.
            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(year);
            id.setStockSymbol(symbol);

            // Check if the input year is the current year.
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year.equals(Integer.toString(currentYear))) {
                // Get the current month.
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                // Set the month to the current month (adjust for 0-based index).
                id.setMonth(getMonthName(currentMonth));
                System.out.println("Current month: " + getMonthName(currentMonth));
            } else {
                // Set the month to "December" for other years.
                id.setMonth("December");
            }

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceService
                    .findAssetMonthlyPriceById(id);

            if (assetMonthlyPriceOptional.isPresent()) {
                // Asset price for the specified year is found in the database.
                double closingPrice = assetMonthlyPriceOptional.get().getClosingPrice();
                double quantityPurchased = asset.getQuantityPurchased();
                return closingPrice * quantityPurchased;
            } else {
                // Handle the case where data for the specified year is not found in the
                // database.
                return 0;
            }
        } catch (Exception e) {
            // Handle exceptions appropriately.
            return 0;
        }
    }

    // Get value of asset at the end of the specified year and month
    public double getAssetValueByYearAndMonth(String symbol, String year, String month, Asset asset) {

        if (asset.getAssetId().getStockSymbol().equals("CASHALLOCATION")) {
            return asset.getQuantityPurchased();
        }

        try {
            AssetMonthlyPriceId id = new AssetMonthlyPriceId();
            id.setYear(year);
            id.setMonth(month);
            id.setStockSymbol(symbol);

            Optional<AssetMonthlyPrice> assetMonthlyPriceOptional = assetMonthlyPriceService
                    .findAssetMonthlyPriceById(id);

            if (assetMonthlyPriceOptional.isPresent()) {
                // Asset price for the specified year and month is found in the database.
                double closingPrice = assetMonthlyPriceOptional.get().getClosingPrice();
                double quantityPurchased = asset.getQuantityPurchased();
                return closingPrice * quantityPurchased;
            } else {
                // Handle the case where data for the specified year and month is not found in
                // the database.
                return 0;
            }
        } catch (Exception e) {
            // Handle exceptions appropriately.
            return 0;
        }
    }

    // HELPER
    // METHODS------------------------------------------------------------------------------------------------

    // Helper method to get month name based on its number (0-based index).
    public String getMonthName(int month) {
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[month];
    }

    // Use JPA to query the database for unique symbols
    public List<String> getUniqueSymbols() {
        List<String> uniqueSymbols = assetMonthlyPriceService.findDistinctStockSymbols();
        return uniqueSymbols;
    }

    // Check if the AssetMonthlyPrices table is empty or not initialized.
    public boolean isAssetMonthlyPricesTableEmpty() {
        long recordCount = assetMonthlyPriceService.getCount();
        return recordCount == 0;
    }

}
