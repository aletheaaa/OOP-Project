package is442.portfolioAnalyzer.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PortfolioNameNotUniqueException extends RuntimeException {
    public PortfolioNameNotUniqueException(String message) {
        super(message);
    }
}