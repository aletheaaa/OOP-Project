package is442.portfolioAnalyzer.AssetMonthlyPrice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

 @Repository
 public interface AssetMonthlyPriceDAO extends JpaRepository<AssetMonthlyPrice, AssetMonthlyPriceId> {
    boolean existsByIdStockSymbol(String symbol);    
    
    @Query("SELECT DISTINCT a.id.stockSymbol FROM AssetMonthlyPrice a")
    List<String> findDistinctStockSymbols();

    // Find the latest price of an asset by symbol, current year, and current month
    @Query("SELECT a.closingPrice " +
            "FROM AssetMonthlyPrice a " +
            "WHERE a.id.stockSymbol = :symbol " +
            "AND a.id.year = :year " +
            "AND a.id.month = :month")
    Optional<Double> findLatestPriceBySymbolAndYearAndMonth(String symbol, String year, String month);

 }
