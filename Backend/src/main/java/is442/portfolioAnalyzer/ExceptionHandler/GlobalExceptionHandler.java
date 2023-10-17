package is442.portfolioAnalyzer.ExceptionHandler;

import is442.portfolioAnalyzer.Exception.PortfolioNameNotUniqueException;
import is442.portfolioAnalyzer.Exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// This class will handle all exceptions thrown by the application that are listed below.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(PortfolioNameNotUniqueException.class)
    public ResponseEntity<?> handlePortfolioNameNotUniqueException(PortfolioNameNotUniqueException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
