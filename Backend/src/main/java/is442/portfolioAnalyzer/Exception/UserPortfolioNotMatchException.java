package is442.portfolioAnalyzer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserPortfolioNotMatchException extends RuntimeException{
    public UserPortfolioNotMatchException(String message) {
        super(message);
    }
}
