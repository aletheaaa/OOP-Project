package is442.portfolioAnalyzer.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class SymbolNotFoundException extends RuntimeException {
  public SymbolNotFoundException(String message) {
    super(message);
  }
}
