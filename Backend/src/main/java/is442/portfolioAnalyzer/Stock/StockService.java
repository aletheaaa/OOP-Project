package is442.portfolioAnalyzer.Stock;

import is442.portfolioAnalyzer.ExternalApi.ExternalApiService;
import java.util.HashMap;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    StockDAO stockDAO;
    @Autowired
    ExternalApiService externalApiService;

   
    //Get all stocks and their Industries
    public Map<String, String> getAllStocksAndIndustries() {
        List<Stock> stocks = stockDAO.findAll();
        Map<String, String> industryMap = new HashMap<>();

        for (Stock stock : stocks) {
            industryMap.put(stock.getStockSymbol(), stock.getIndustry());
        }

        return industryMap;
    }

    //Get all stocks and their countries
    public Map<String, String> getAllStocksAndCountries() {
        List<Stock> stocks = stockDAO.findAll();
        Map<String, String> countryMap = new HashMap<>();

        for (Stock stock : stocks) {
            countryMap.put(stock.getStockSymbol(), stock.getCountry());
        }

        return countryMap;
    }

    //Get all stocks and their currencies
    public Map<String, String> getAllStocksAndCurrencies() {
        List<Stock> stocks = stockDAO.findAll();
        Map<String, String> countryMap = new HashMap<>();

        for (Stock stock : stocks) {
            countryMap.put(stock.getStockSymbol(), stock.getCurrency());
        }

        return countryMap;
    }



}
