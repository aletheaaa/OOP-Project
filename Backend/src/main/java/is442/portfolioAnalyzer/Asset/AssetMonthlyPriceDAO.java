package is442.portfolioAnalyzer.Asset;

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
    
    // Find the earliest price of an asset by symbol, current year, and current month in the table
    @Query("SELECT a.closingPrice " +
        "FROM AssetMonthlyPrice a " +
        "WHERE a.id.stockSymbol = :symbol " +
        "AND (a.id.year < :year OR (a.id.year = :year AND a.id.month < :month)) " +
        "ORDER BY a.id.year, a.id.month ASC")
    Optional<Double> findEarliestPriceBySymbolAndYearAndMonth(String symbol, String year, String month);
    
 }
