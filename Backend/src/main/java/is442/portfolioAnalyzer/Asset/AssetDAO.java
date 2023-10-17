 package is442.portfolioAnalyzer.Asset;

 import java.util.List;

 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface AssetDAO extends JpaRepository<Asset, AssetId> {
     List<Asset> findByAssetIdPortfolioId(Integer portfolioId);
 }
