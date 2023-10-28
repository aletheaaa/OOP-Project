package is442.portfolioAnalyzer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true")
    @PostMapping("/forgotPassword")
    public ResponseEntity<AuthenticationResponse> forgotPassword(
            @RequestBody String email) {
        return ResponseEntity.ok(service.forgotPassword(email));
    }
}
