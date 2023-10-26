package is442.portfolioAnalyzer.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AuthenticationException extends RuntimeException {
    public AuthenticationException (String message) {
        super(message);
    }
}