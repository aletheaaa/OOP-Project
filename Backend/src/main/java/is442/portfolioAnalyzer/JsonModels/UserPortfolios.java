package is442.portfolioAnalyzer.JsonModels;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class UserPortfolios {
    List<Map<String, String>> userPortfolios;
}
