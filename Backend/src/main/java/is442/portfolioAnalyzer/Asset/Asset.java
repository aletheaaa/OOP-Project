package is442.portfolioAnalyzer.Asset;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "assets")
public class Asset {

    @EmbeddedId
    private AssetId assetId; //symbol and portfolio id
    private String sector;
    private double quantityPurchased;
    private String industry; 
    private String country;

}
